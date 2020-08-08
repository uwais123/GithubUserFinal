package com.masuwes.githubuserfinal.ui

import android.view.View

class CustomOnClick(private val position: Int, private val onItemClickCallback: OnitemClickCallback): View.OnClickListener {
    override fun onClick(view: View) {
        onItemClickCallback.onItemClicked(view, position)
    }

    interface OnitemClickCallback{
        fun onItemClicked(view: View, position: Int)
    }
}