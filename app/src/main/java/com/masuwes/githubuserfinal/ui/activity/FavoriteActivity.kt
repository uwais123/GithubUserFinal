package com.masuwes.githubuserfinal.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.masuwes.githubuserfinal.R
import com.masuwes.githubuserfinal.adapter.FavoriteAdapter
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.CONTENT_URI
import com.masuwes.githubuserfinal.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter
    internal val TAG = FavoriteActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.apply{
            setDisplayHomeAsUpEnabled(true)
            title = resources.getString(R.string.favorite)
        }

        adapter = FavoriteAdapter(this)

        GlobalScope.launch(Dispatchers.Main) {
            val deferredGit = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val fav = deferredGit.await()
            if (fav.size > 0) {
                adapter.listFavorite = fav
            } else {
                adapter.listFavorite = ArrayList()
                showSnackbar(resources.getString(R.string.empty))
            }
        }

        showRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showRecyclerView() {
        favorite_list.adapter = adapter
        favorite_list.layoutManager = LinearLayoutManager(this)
        favorite_list.setHasFixedSize(true)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(favorite_list, message, Snackbar.LENGTH_LONG).show()
    }
}










































