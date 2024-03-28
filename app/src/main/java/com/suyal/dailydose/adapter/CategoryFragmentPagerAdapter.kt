package com.suyal.dailydose.adapter

import com.suyal.dailydose.R
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.suyal.dailydose.fragments.BusinessFragment
import com.suyal.dailydose.fragments.CultureFragment
import com.suyal.dailydose.fragments.EnvironmentFragment
import com.suyal.dailydose.fragments.FashionFragment
import com.suyal.dailydose.fragments.HomeFragment
import com.suyal.dailydose.fragments.ScienceFragment
import com.suyal.dailydose.fragments.SocietyFragment
import com.suyal.dailydose.fragments.SportFragment
import com.suyal.dailydose.fragments.WorldFragment
import com.suyal.dailydose.utils.Constants

/**
 * Created by Gaurav Suyal on 28-03-2024.
 */
class CategoryFragmentPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    /**
     * Return the Fragment that should be displayed for the given page number.
     */
    override fun getItem(position: Int): Fragment {
        return when (position) {
            Constants.HOME -> HomeFragment()
            Constants.WORLD -> WorldFragment()
            Constants.SCIENCE -> ScienceFragment()
            Constants.SPORT -> SportFragment()
            Constants.ENVIRONMENT -> EnvironmentFragment()
            Constants.SOCIETY -> SocietyFragment()
            Constants.FASHION -> FashionFragment()
            Constants.BUSINESS -> BusinessFragment()
            Constants.CULTURE -> CultureFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    /**
     * Return the total number of pages.
     */
    override fun getCount(): Int {
        return 9
    }

    /**
     * Return page title of the tab.
     */
    override fun getPageTitle(position: Int): CharSequence? {
        val titleResId = when (position) {
            Constants.HOME -> R.string.ic_title_home
            Constants.WORLD -> R.string.ic_title_world
            Constants.SCIENCE -> R.string.ic_title_science
            Constants.SPORT -> R.string.ic_title_sport
            Constants.ENVIRONMENT -> R.string.ic_title_environment
            Constants.SOCIETY -> R.string.ic_title_society
            Constants.FASHION -> R.string.ic_title_fashion
            Constants.BUSINESS -> R.string.ic_title_business
            else -> R.string.ic_title_culture
        }
        return context.getString(titleResId)
    }
}