package com.masuwes.consumerapp.helper

import android.database.Cursor
import android.util.Log
import com.masuwes.consumerapp.db.DatabaseContract.GitColumns.Companion.AVATAR
import com.masuwes.consumerapp.db.DatabaseContract.GitColumns.Companion.LOGIN_NAME
import com.masuwes.consumerapp.db.DatabaseContract.GitColumns.Companion._ID
import com.masuwes.consumerapp.model.UserModel

object MappingHelper {

    private val TAG = MappingHelper::class.java.simpleName

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