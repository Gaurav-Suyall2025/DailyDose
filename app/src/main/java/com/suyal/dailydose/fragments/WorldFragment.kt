package com.suyal.dailydose.fragments

import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import com.suyal.dailydose.News
import com.suyal.dailydose.NewsLoader
import com.suyal.dailydose.NewsPreferences
import com.suyal.dailydose.R


class WorldFragment : BaseArticlesFragment() {

    private val LOG_TAG: String = WorldFragment::class.java.name

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val worldUrl: String = NewsPreferences.getPreferredUrl(requireContext(), getString(R.string.world))
        Log.e(LOG_TAG, worldUrl)

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), worldUrl.toString()) as Loader<MutableList<News>>
    }
}