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

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    private val following = ArrayList<UserModel>()

    fun setData(items: ArrayList<UserModel>) {
        following.clear()
        following.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = following.size

    override fun onBindViewHolder(holder: FollowingAdapter.ViewHolder, position: Int) {
        holder.bind(following[position])
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