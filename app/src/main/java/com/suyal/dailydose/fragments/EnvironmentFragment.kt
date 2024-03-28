package com.suyal.dailydose.fragments

import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import com.suyal.dailydose.News
import com.suyal.dailydose.NewsLoader
import com.suyal.dailydose.NewsPreferences
import com.suyal.dailydose.R


class EnvironmentFragment : BaseArticlesFragment() {

    private val LOG_TAG: String = EnvironmentFragment::class.java.name

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val environmentUrl: String =
            NewsPreferences.getPreferredUrl(requireContext(), getString(R.string.environment))
        Log.e(LOG_TAG, environmentUrl)

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), environmentUrl.toString()) as Loader<MutableList<News>>
    }
}