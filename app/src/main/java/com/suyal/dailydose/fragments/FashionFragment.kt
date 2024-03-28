package com.suyal.dailydose.fragments

import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import com.suyal.dailydose.News
import com.suyal.dailydose.NewsLoader
import com.suyal.dailydose.NewsPreferences
import com.suyal.dailydose.R


class FashionFragment : BaseArticlesFragment() {

    private val LOG_TAG: String = FashionFragment::class.java.name

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val fashionUrl: String =
            NewsPreferences.getPreferredUrl(requireContext(), getString(R.string.fashion))
        Log.e(LOG_TAG, fashionUrl)

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), fashionUrl.toString()) as Loader<MutableList<News>>
    }
}