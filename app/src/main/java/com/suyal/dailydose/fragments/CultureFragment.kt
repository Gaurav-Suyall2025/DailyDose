package com.suyal.dailydose.fragments

import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import com.suyal.dailydose.News
import com.suyal.dailydose.NewsLoader
import com.suyal.dailydose.NewsPreferences
import com.suyal.dailydose.R


class CultureFragment : BaseArticlesFragment() {

    private val LOG_TAG: String = CultureFragment::class.java.name

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val cultureUrl: String =
            NewsPreferences.getPreferredUrl(requireContext(), getString(R.string.culture))
        Log.e(LOG_TAG, cultureUrl)

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), cultureUrl.toString()) as Loader<MutableList<News>>
    }

}