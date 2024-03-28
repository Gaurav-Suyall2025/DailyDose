package com.suyal.dailydose

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.suyal.dailydose.utils.QueryUtils

/**
 * Created by Gaurav Suyal on 28-03-2024.
 */


class NewsLoader(context: Context, private val mUrl: String) : AsyncTaskLoader<List<News>>(context) {

    /** Tag for log messages */
    private val LOG_TAG = NewsLoader::class.java.name

    override fun onStartLoading() {
        // Trigger the loadInBackground() method to execute.
        forceLoad()
    }

    /**
     * This is on a background thread.
     */
    override fun loadInBackground(): List<News>? {
        if (mUrl.isNullOrEmpty()) {
            return null
        }

        // Perform the network request, parse the response, and extract a list of news.
        return QueryUtils.fetchNewsData(mUrl)
    }
}
