package com.suyal.dailydose.fragments

import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import com.suyal.dailydose.News
import com.suyal.dailydose.NewsLoader
import com.suyal.dailydose.NewsPreferences
import com.suyal.dailydose.R


class ScienceFragment : BaseArticlesFragment() {

    private val LOG_TAG: String = ScienceFragment::class.java.name

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val scienceUrl: String =
            NewsPreferences.getPreferredUrl(requireContext(), getString(R.string.science))
        Log.e(LOG_TAG, scienceUrl)

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), scienceUrl.toString()) as Loader<MutableList<News>>
    }
}