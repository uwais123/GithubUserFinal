package com.masuwes.githubuserfinal.helper

import android.database.Cursor
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.AVATAR
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.LOGIN_NAME
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion._ID
import com.masuwes.githubuserfinal.db.DatabaseContract.getColumnInt
import com.masuwes.githubuserfinal.db.DatabaseContract.getColumnString

class FavoriteItem (cursor: Cursor?) {
    var id : Int? = 0
    var username: String? = null
    var avatar: String? = null

    init {
        id = getColumnInt(cursor, _ID)
        username = getColumnString(cursor, LOGIN_NAME)
        avatar = getColumnString(cursor, AVATAR)
    }

    override fun toString(): String {
        return """FavouritesItem{id = '$id',username = '$username',avatar = '$avatar'}"""
    }
}