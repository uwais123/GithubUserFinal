package com.masuwes.githubuserfinal.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.masuwes.githubuserfinal.db.DatabaseContract.AUTHORITY
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.CONTENT_URI
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.TABLE_NAME
import com.masuwes.githubuserfinal.helper.GitHelper

class GitContentProvider : ContentProvider() {

    companion object {
        private const val GIT = 1
        private const val GIT_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var gitHelper: GitHelper

        init {
            //com.masuwes.githubuserfinal/git_user
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, GIT)
            //com.masuwes.githubuserfinal/git_user/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", GIT_ID)
        }
    }

    override fun onCreate(): Boolean {
        gitHelper = GitHelper.getInstance(context as Context)
        gitHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            GIT -> gitHelper.queryAll()
            GIT_ID -> gitHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
     }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (GIT) {
            sUriMatcher.match(uri) -> gitHelper.insert(contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val updated: Int = when (GIT_ID) {
            sUriMatcher.match(uri) -> gitHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (GIT_ID) {
            sUriMatcher.match(uri) -> gitHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

}


























