package com.masuwes.githubuserfinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.masuwes.githubuserfinal.R
import com.masuwes.githubuserfinal.adapter.FollowingAdapter
import com.masuwes.githubuserfinal.viewmodel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*


class FollowingFragment : Fragment() {

    companion object {
        const val EXTRA_FOLLOWING = "extra_following"
    }

    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowingAdapter()

        showRecyclerView()

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)

        if (arguments != null) {
            val username = arguments?.getString(EXTRA_FOLLOWING)
            followingViewModel.setFollowing(username)
        }

        followingViewModel.getFollowing().observe(this, Observer { userModel ->
            if (userModel != null) {
                adapter.setData(userModel)
            }
        })

    }

    private fun showRecyclerView() {
        following_list.layoutManager = LinearLayoutManager(context)
        following_list.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}





























