package com.masuwes.githubuserfinal.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masuwes.githubuserfinal.R
import com.masuwes.githubuserfinal.model.UserModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class FollowersAdapter: RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    private val followers = ArrayList<UserModel>()

    fun setData(items: ArrayList<UserModel>) {
        followers.clear()
        followers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = followers.size

    override fun onBindViewHolder(holder: FollowersAdapter.ViewHolder, position: Int) {
        holder.bind(followers[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(userModel: UserModel) {
            with(itemView) {
                Picasso.get().load(Uri.parse(userModel.avatar)).into(image_avatar)
                title.text = userModel.login
            }
        }
    }

}