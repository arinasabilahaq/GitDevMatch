package com.arina.githubuser.ui.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.arina.githubuser.R
import com.arina.githubuser.ui.detail.FollowersFragment
import com.arina.githubuser.ui.detail.FollowingFragment

class SectionPagerAdapter(private val context: Context, fragmentManager: FragmentManager, dataBundle: Bundle) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle = dataBundle

    @StringRes
    private val tabTitlesResIds = intArrayOf(R.string.followers, R.string.following)

    override fun getCount(): Int = tabTitlesResIds.size

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = FollowersFragment()
                fragment.arguments = fragmentBundle
                fragment
            }
            1 -> {
                val fragment = FollowingFragment()
                fragment.arguments = fragmentBundle
                fragment
            }
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = tabTitlesResIds.getOrNull(position)?.let { context.resources.getString(it) }
}
