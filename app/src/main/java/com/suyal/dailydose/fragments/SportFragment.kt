package com.suyal.dailydose.fragments

import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import com.suyal.dailydose.News
import com.suyal.dailydose.NewsLoader
import com.suyal.dailydose.NewsPreferences
import com.suyal.dailydose.R


class SportFragment : BaseArticlesFragment() {

    private val LOG_TAG: String = SportFragment::class.java.name

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<MutableList<News>> {
        val sportUrl: String = NewsPreferences.getPreferredUrl(requireContext(), getString(R.string.sport))
        Log.e(LOG_TAG, sportUrl)

        // Create a new loader for the given URL
        return NewsLoader(requireContext(), sportUrl.toString()) as Loader<MutableList<News>>
    }
}