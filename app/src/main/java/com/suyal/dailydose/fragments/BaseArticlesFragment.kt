package com.suyal.dailydose.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.suyal.dailydose.EmptyRecyclerView
import com.suyal.dailydose.News
import com.suyal.dailydose.NewsLoader
import com.suyal.dailydose.NewsPreferences
import com.suyal.dailydose.R
import com.suyal.dailydose.adapter.NewsAdapter
import com.suyal.dailydose.utils.Constants


open class BaseArticlesFragment : Fragment(), LoaderManager.LoaderCallbacks<MutableList<News>> {

    private val LOG_TAG: String = BaseArticlesFragment::class.java.name

    /** Constant value for the news loader ID.  */
    private val NEWS_LOADER_ID = 1

    /** Adapter for the list of news  */
    lateinit var mAdapter: NewsAdapter

    /** TextView that is displayed when the recycler view is empty  */
    lateinit var mEmptyStateTextView: TextView

    /** Loading indicator that is displayed before the first load is completed  */
    lateinit var mLoadingIndicator: View

    /** The [SwipeRefreshLayout] that detects swipe gestures and
     * triggers callbacks in the app.
     */
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_home, container, false)

        // Find a reference to the {@link RecyclerView} in the layout
        // Replaced RecyclerView with EmptyRecyclerView
        val mRecyclerView: EmptyRecyclerView = rootView.findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(activity)
        mRecyclerView.setHasFixedSize(true)

        // Set the layoutManager on the {@link RecyclerView}
        mRecyclerView.setLayoutManager(layoutManager)

        // Find the SwipeRefreshLayout
        mSwipeRefreshLayout = rootView.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        // Set the color scheme of the SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeColors(
            resources.getColor(R.color.swipe_color_1),
            resources.getColor(R.color.swipe_color_2),
            resources.getColor(R.color.swipe_color_3),
            resources.getColor(R.color.swipe_color_4)
        )

        // Set up OnRefreshListener that is invoked when the user performs a swipe-to-refresh gesture.
        mSwipeRefreshLayout.setOnRefreshListener(OnRefreshListener {
            Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout")
            // restart the loader
            initiateRefresh()
            Toast.makeText(
                activity, getString(R.string.updated_just_now),
                Toast.LENGTH_SHORT
            ).show()
        })

        // Find the loading indicator from the layout
        mLoadingIndicator = rootView.findViewById<View>(R.id.loading_indicator)

        // Find the empty view from the layout and set it on the new recycler view
        mEmptyStateTextView = rootView.findViewById<TextView>(R.id.empty_view)
        mRecyclerView.setEmptyView(mEmptyStateTextView)

        // Create a new adapter that takes an empty list of news as input
        mAdapter = NewsAdapter(requireContext(), ArrayList<News>())

        // Set the adapter on the {@link recyclerView}
        mRecyclerView.setAdapter(mAdapter)

        // Check for network connectivity and initialize the loader
        initializeLoader(isConnected())

        return rootView
    }

   /* override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val uriBuilder: Uri.Builder = NewsPreferences.getPreferredUri(requireContext())

        Log.e(LOG_TAG, uriBuilder.toString())

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), uriBuilder.toString())
    }*/

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val uriBuilder: Uri.Builder = NewsPreferences.getPreferredUri(requireContext())

        Log.e(LOG_TAG, uriBuilder.toString())

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), uriBuilder.toString()) as Loader<MutableList<News>>
    }


    override fun onLoaderReset(loader: Loader<MutableList<News>>) {
        mAdapter.clearAll()
    }

    override fun onLoadFinished(loader: Loader<MutableList<News>>, data: MutableList<News>?) {

        // Hide loading indicator because the data has been loaded
        mLoadingIndicator!!.visibility = View.GONE

        // Set empty state text to display "No news found."
        mEmptyStateTextView.setText(R.string.no_news)

        // Clear the adapter of previous news data
        mAdapter.clearAll()

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the recyclerView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data)
        }

        // Hide the swipe icon animation when the loader is done refreshing the data
        mSwipeRefreshLayout!!.isRefreshing = false
    }


    /**
     * When the user returns to the previous screen by pressing the up button in the SettingsActivity,
     * restart the Loader to reflect the current value of the preference.
     */
    override fun onResume() {
        super.onResume()
        restartLoader(isConnected())
    }

    /**
     * Check for network connectivity.
     */
    private fun isConnected(): Boolean {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        val connectivityManager =
            activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Get details on the currently active default data network
        val networkInfo = connectivityManager.activeNetworkInfo

        return (networkInfo != null && networkInfo.isConnected)
    }

    /**
     * If there is internet connectivity, initialize the loader as
     * usual. Otherwise, hide loading indicator and set empty state TextView to display
     * "No internet connection."
     *
     * @param isConnected internet connection is available or not
     */
    private fun initializeLoader(isConnected: Boolean) {
        if (isConnected) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            val loaderManager = loaderManager
            // Initialize the loader with the NEWS_LOADER_ID
            loaderManager.initLoader<MutableList<News>>(NEWS_LOADER_ID, null, this)
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            mLoadingIndicator!!.visibility = View.GONE
            // Update empty state with no connection error message and image
            mEmptyStateTextView?.setText(R.string.no_internet_connection)
            mEmptyStateTextView?.setCompoundDrawablesWithIntrinsicBounds(
                Constants.DEFAULT_NUMBER,
                R.drawable.ic_network_check, Constants.DEFAULT_NUMBER, Constants.DEFAULT_NUMBER
            )
        }
    }

    /**
     * Restart the loader if there is internet connectivity.
     * @param isConnected internet connection is available or not
     */
    private fun restartLoader(isConnected: Boolean) {
        if (isConnected) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            val loaderManager = loaderManager
            // Restart the loader with the NEWS_LOADER_ID
            loaderManager.restartLoader<MutableList<News>>(NEWS_LOADER_ID, null, this)
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            mLoadingIndicator!!.visibility = View.GONE
            // Update empty state with no connection error message and image
            mEmptyStateTextView?.setText(R.string.no_internet_connection)
            mEmptyStateTextView?.setCompoundDrawablesWithIntrinsicBounds(
                Constants.DEFAULT_NUMBER,
                R.drawable.ic_network_check, Constants.DEFAULT_NUMBER, Constants.DEFAULT_NUMBER
            )

            // Hide SwipeRefreshLayout
            mSwipeRefreshLayout!!.visibility = View.GONE
        }
    }

    /**
     * When the user performs a swipe-to-refresh gesture, restart the loader.
     */
    private fun initiateRefresh() {
        restartLoader(isConnected())
    }


}