package com.masuwes.githubuserfinal.ui

import android.view.View
import com.masuwes.githubuserfinal.model.UserModel

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, user: UserModel)
}