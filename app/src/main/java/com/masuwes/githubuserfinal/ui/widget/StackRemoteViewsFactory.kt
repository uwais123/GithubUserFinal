package com.masuwes.githubuserfinal.ui.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.masuwes.githubuserfinal.R
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.CONTENT_URI
import com.masuwes.githubuserfinal.helper.FavouriteItem
import java.util.concurrent.ExecutionException

class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var listFavoriteUsers: Cursor? = null

    override fun onDataSetChanged() {
        if (listFavoriteUsers != null) {
            listFavoriteUsers?.close()
        }

        val identityToken = Binder.clearCallingIdentity()
        listFavoriteUsers = context.contentResolver.query(CONTENT_URI, null, null, null, null)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onCreate() {
        listFavoriteUsers = context.contentResolver.query(CONTENT_URI, null, null, null ,null)
    }

    override fun getCount(): Int = listFavoriteUsers?.count ?: 0

    override fun getViewAt(position: Int): RemoteViews {
        val item: FavouriteItem = getItem(position)
        val rv = RemoteViews(context.packageName, R.layout.list_widget)

        var bitmap: Bitmap? = null
        try {
            bitmap = Glide.with(context)
                .asBitmap()
                .load(item.avatar)
                .submit()
                .get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap)
        val extras = Bundle()

        //ambil username
        extras.putString(FavoriteUserWidget.EXTRA_ITEM, item.username)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    private fun getItem(position: Int): FavouriteItem {
        listFavoriteUsers?.moveToPosition(position)?.let { check(it) { "Invalid Position!" } }
        return FavouriteItem(listFavoriteUsers)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    override fun onDestroy() { }

}

























