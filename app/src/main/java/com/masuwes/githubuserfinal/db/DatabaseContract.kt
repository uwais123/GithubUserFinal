package com.masuwes.githubuserfinal.db

import android.net.Uri
import android.provider.BaseColumns
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.TABLE_NAME

object DatabaseContract {
    const val AUTHORITY = "com.masuwes.githubuserfinal"
    private const val SCHEME = "content"

    class GitColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "git_user"
            const val _ID = "_id"
            const val LOGIN_NAME = "login_name"
            const val AVATAR = "avatar"
        }
    }

    // content: com.masuwes.githubuserfinal/git_user
    val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()
}



























