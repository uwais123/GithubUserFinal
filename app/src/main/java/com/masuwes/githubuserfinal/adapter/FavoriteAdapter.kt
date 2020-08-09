package com.masuwes.githubuserfinal.adapter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masuwes.githubuserfinal.R
import com.masuwes.githubuserfinal.model.UserModel
import com.masuwes.githubuserfinal.ui.CustomOnClick
import com.masuwes.githubuserfinal.ui.activity.DetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class FavoriteAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    val TAG = FavoriteAdapter::class.java.simpleName
    var listFavorite = ArrayList<UserModel>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(mView)
    }

    override fun getItemCount(): Int = this.listFavorite.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(userModel: UserModel){
            with(itemView){
                Picasso.get().load(Uri.parse(userModel.avatar)).into(image_avatar)
                title.text = userModel.login
                Log.d(TAG, "${userModel.avatar}")

                listItem.setOnClickListener(CustomOnClick(adapterPosition, object : CustomOnClick.OnitemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_STATE, userModel)
                        intent.putExtra(DetailActivity.EXTRA_FAV, "favorite")
                        activity.startActivity(intent)
                    }
                }))
            }
        }
    }

}



















