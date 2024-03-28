package com.suyal.dailydose.fragments

import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import com.suyal.dailydose.News
import com.suyal.dailydose.NewsLoader
import com.suyal.dailydose.NewsPreferences
import com.suyal.dailydose.R


class BusinessFragment : BaseArticlesFragment() {

    private val LOG_TAG: String = BusinessFragment::class.java.name

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val businessUrl: String =
            NewsPreferences.getPreferredUrl(requireContext(), getString(R.string.business))
        Log.e(LOG_TAG, businessUrl)

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), businessUrl.toString()) as Loader<MutableList<News>>
    }

}