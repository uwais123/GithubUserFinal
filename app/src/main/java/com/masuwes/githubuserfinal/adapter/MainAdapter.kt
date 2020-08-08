package com.masuwes.githubuserfinal.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masuwes.githubuserfinal.R
import com.masuwes.githubuserfinal.model.UserModel
import com.masuwes.githubuserfinal.ui.RecyclerViewClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val dataUser = ArrayList<UserModel>()

    var listener: RecyclerViewClickListener? = null

    fun setData(items: ArrayList<UserModel>) {
        dataUser.clear()
        dataUser.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(userModel: UserModel) {
            with(itemView) {
                Picasso.get().load(Uri.parse(userModel.avatar)).into(image_avatar)
                title.text = userModel.login
                subtitle.text = userModel.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataUser[position])
        holder.itemView.setOnClickListener {
            listener?.onItemClicked(it, dataUser[position])
        }

    }
}














