package com.masuwes.consumerapp.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.masuwes.consumerapp.R
import com.masuwes.consumerapp.adapter.AdapterConsumer
import com.masuwes.consumerapp.db.DatabaseContract.CONTENT_URI
import com.masuwes.consumerapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: AdapterConsumer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = resources.getString(R.string.favorite)

        adapter = AdapterConsumer(this)

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
                showSnackBarMessage(resources.getString(R.string.empty))
            }
        }

        showRecyclerView()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.localization) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerView() {
        user_list.adapter = adapter
        user_list.layoutManager = LinearLayoutManager(this)
        user_list.setHasFixedSize(true)
    }

    private fun showSnackBarMessage(message: String) {
         Snackbar.make(user_list, message, Snackbar.LENGTH_SHORT).show()
    }
}