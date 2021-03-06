package com.masuwes.githubuserfinal.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.TABLE_NAME
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion._ID
import com.masuwes.githubuserfinal.db.DatabaseHelper
import java.sql.SQLException

class GitHelper(context: Context) {

    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: GitHelper? = null
        fun getInstance(context: Context): GitHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: GitHelper(context)
        }
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE, null, null, null, null, null, "$_ID DESC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE, null, "$_ID = ?", arrayOf(id), null, null, null, null
        )
    }

    fun update(id: String, values: ContentValues?): Int {
        open()
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}


































