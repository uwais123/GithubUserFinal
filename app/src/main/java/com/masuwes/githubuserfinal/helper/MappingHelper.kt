package com.masuwes.githubuserfinal.helper

import android.database.Cursor
import android.util.Log
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.AVATAR
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.LOGIN_NAME
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion._ID
import com.masuwes.githubuserfinal.model.UserModel

object MappingHelper {

    val TAG = MappingHelper::class.java.simpleName

    fun mapCursorToArrayList(gitCursor: Cursor?): ArrayList<UserModel> {

        val gitList = ArrayList<UserModel>()

        gitCursor?.apply {
            while (moveToNext()) {
                val userModel = UserModel()
                userModel.id = getInt(getColumnIndexOrThrow(_ID))
                userModel.login = getString(getColumnIndexOrThrow(LOGIN_NAME))
                userModel.avatar = getString(getColumnIndexOrThrow(AVATAR))
                gitList.add(userModel)
                Log.d(TAG, userModel.toString())
            }
        }
        return gitList
    }
}
















