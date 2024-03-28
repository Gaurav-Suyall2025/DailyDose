package com.suyal.dailydose.fragments

import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import com.suyal.dailydose.News
import com.suyal.dailydose.NewsLoader
import com.suyal.dailydose.NewsPreferences
import com.suyal.dailydose.R


class SocietyFragment : BaseArticlesFragment() {

    private val LOG_TAG: String = SocietyFragment::class.java.name

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val societyUrl: String =
            NewsPreferences.getPreferredUrl(requireContext(), getString(R.string.society))
        Log.e(LOG_TAG, societyUrl)

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), societyUrl.toString()) as Loader<MutableList<News>>
    }
}