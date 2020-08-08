package com.masuwes.githubuserfinal.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.masuwes.githubuserfinal.R
import com.masuwes.githubuserfinal.ui.fragment.FollowersFragment
import com.masuwes.githubuserfinal.ui.fragment.FollowingFragment

class SectionPagerAdapter(private val context: Context, fragmentManager: FragmentManager):
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var username: String? = "username"

    @StringRes
    private val TAB_TITLES = intArrayOf (
        R.string.tab_text1,
        R.string.tab_text2
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment()
                val bundle = Bundle()
                bundle.putString(FollowersFragment.EXTRA_FOLLOWERS, getData())
                fragment.arguments = bundle
            }

            1 -> {
                fragment = FollowingFragment()
                val bundle = Bundle()
                bundle.putString(FollowingFragment.EXTRA_FOLLOWING, getData())
                fragment.arguments = bundle
            }
        }
        return fragment as Fragment
    }

    fun setData(loginName: String){
        username = loginName
    }

    private fun getData(): String? {
        return username
    }

    override fun getCount(): Int = 2

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }


}












