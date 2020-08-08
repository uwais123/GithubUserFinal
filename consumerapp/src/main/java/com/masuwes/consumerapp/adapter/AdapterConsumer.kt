package com.masuwes.consumerapp.adapter

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.masuwes.consumerapp.R
import com.masuwes.consumerapp.model.UserModel
import com.masuwes.consumerapp.ui.CustomOnItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class AdapterConsumer(private val activity: Activity): RecyclerView.Adapter<AdapterConsumer.AdapterViewHolder>() {

    var listFavorite = ArrayList<UserModel>()
        set(listFavorite){
            if (listFavorite.size > 0){
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return AdapterViewHolder (mView)
    }

    override fun getItemCount(): Int = this.listFavorite.size

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    inner class AdapterViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        fun bind(userModel: UserModel) {
            with(itemView){
                Picasso.get().load(Uri.parse(userModel.avatar)).into(image_avatar)
                title.text = userModel.login

                listItem.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        Toast.makeText(context, listFavorite[position].login, Toast.LENGTH_SHORT).show()
                    }
                }))
            }
        }
    }
}