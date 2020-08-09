package com.masuwes.githubuserfinal.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.masuwes.githubuserfinal.R
import com.masuwes.githubuserfinal.adapter.MainAdapter
import com.masuwes.githubuserfinal.model.UserModel
import com.masuwes.githubuserfinal.ui.RecyclerViewClickListener
import com.masuwes.githubuserfinal.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewClickListener {

    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel
    private var backPress: Long = 0
    private var backToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
        }

        adapter = MainAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        
        // TODO: Add TextInputSearch
        text_edit_text.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                val username = text_input_layout.editText?.text.toString()

                if (username.isEmpty()) return@OnEditorActionListener true

                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(text_edit_text.windowToken, 0)

                viewModel.setUser(username)
                no_result_layout.visibility = View.GONE
                progress_circular.visibility = View.VISIBLE
            }
            false
        })
        
        viewModel.getUser().observe(this, Observer { user ->
            if (user != null) {
                adapter.setData(user)
                progress_circular.visibility = View.GONE
                showListUser()
            }
        })
        adapter.listener = this
    }

    private fun showListUser() {
        user_list.layoutManager = LinearLayoutManager(this)
        user_list.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId) {
             R.id.settings -> {
                 startActivity(Intent(this, SettingsActivity::class.java))
                 return true
             }
             R.id.favorite -> {
                 startActivity(Intent(this, FavoriteActivity::class.java))
                 return true
             }
         }
        return false
    }

    override fun onItemClicked(view: View, user: UserModel) {
        val intentMain = Intent(this@MainActivity, DetailActivity::class.java)
        intentMain.putExtra(DetailActivity.EXTRA_STATE, user)
        intentMain.putExtra(DetailActivity.EXTRA_MAIN, "mainactivity")
        startActivity(intentMain)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        backToast = Toast.makeText(baseContext, resources.getString(R.string.twiceback), Toast.LENGTH_SHORT)

        if (backPress + 2000 > System.currentTimeMillis()) {
            val exit = Intent(Intent.ACTION_MAIN)
            exit.addCategory(Intent.CATEGORY_HOME)
            exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(exit)
            backToast?.cancel()
            return super.onKeyDown(keyCode, event)
        } else {
            backToast?.show()
        }
        backPress = System.currentTimeMillis()
        return true
    }

}












































