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
import com.masuwes.githubuserfinal.adapter.FollowersAdapter
import com.masuwes.githubuserfinal.viewmodel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*


class FollowersFragment : Fragment() {

    companion object{
        const val EXTRA_FOLLOWERS = "followers_name"
    }
    private lateinit var adapter: FollowersAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter()
        showRecyclerView()

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)


        if (arguments != null) {
            val username = arguments?.getString(EXTRA_FOLLOWERS)
            followersViewModel.setFollowers(username.toString())
        }

        followersViewModel.getFollowers().observe(viewLifecycleOwner, Observer { usermodel ->
            if (usermodel != null) {
                adapter.setData(usermodel)
            }
        })

    }


    private fun showRecyclerView() {
        followers_list.layoutManager = LinearLayoutManager(context)
        followers_list.adapter = adapter

        adapter.notifyDataSetChanged()
    }

}





















