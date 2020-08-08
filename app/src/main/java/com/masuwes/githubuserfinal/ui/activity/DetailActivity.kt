package com.masuwes.githubuserfinal.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.masuwes.githubuserfinal.R
import com.masuwes.githubuserfinal.adapter.SectionPagerAdapter
import com.masuwes.githubuserfinal.db.DatabaseContract.CONTENT_URI
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.AVATAR
import com.masuwes.githubuserfinal.db.DatabaseContract.GitColumns.Companion.LOGIN_NAME
import com.masuwes.githubuserfinal.helper.MappingHelper
import com.masuwes.githubuserfinal.model.UserModel
import com.masuwes.githubuserfinal.ui.fragment.FollowersFragment
import com.masuwes.githubuserfinal.ui.fragment.FollowingFragment
import com.masuwes.githubuserfinal.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private var userModel: UserModel? = null
    private var fromFavorite: String? = null
    private var fromMainActivity: String? = null
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var uriWithId: Uri

    companion object {
        internal val TAG = DetailActivity::class.java.simpleName
        const val EXTRA_STATE = "extra_state"
        const val EXTRA_FAV = "extra_fav"
        const val EXTRA_MAIN = "extra_main"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.apply {
            title = "Profile"
            elevation = 0f
        }

        fromFavorite = intent.getStringExtra(EXTRA_FAV)
        fromMainActivity = intent.getStringExtra(EXTRA_MAIN)

        // Intent to Detail
        userModel = intent.getParcelableExtra<UserModel>(EXTRA_STATE) as UserModel
        detail_username.text = userModel?.login
        Picasso.get().load(Uri.parse(userModel?.avatar)).into(detail_avatar)

        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + userModel?.id)

        val dataUserFav = contentResolver?.query(uriWithId, null, null, null, null)
        val dataGitObject = MappingHelper.mapCursorToArrayList(dataUserFav)
        for (data in dataGitObject) {
            if (this.userModel?.login == data.login) {
                isFavorite = true
                Log.d(TAG, "user favorite")
            }
        }

        setFollowerFollowing(userModel!!)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(userModel?.login)
        detailViewModel.getDetailData().observe(this, Observer { userModel ->
            if (userModel != null) {
                detail_name.text = userModel.name
                detail_company.text = userModel.company
                detail_location.text = userModel.location
                Log.d(TAG, "GetDetail Success")
            }
        })

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            // check where listItem come from
            if (fromMainActivity != null) {
                val intentMain = Intent(this, MainActivity::class.java)
                startActivity(intentMain)
            }else if (fromFavorite != null) {
                val intentFav = Intent(this, FavoriteActivity::class.java)
                startActivity(intentFav)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_favorite_menu, menu)
        setLove(menu)
        this.menuItem = menu
        return super.onCreateOptionsMenu(menu)
    }

    private fun setLove(menu: Menu) {
        if (isFavorite) {
            menu.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.favorite_pink)
        } else {
            menu.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.favorite_border)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId) {
             R.id.add_favorite -> setFavorite()
         }
        return true
    }

    private fun setFavorite() {
        if (isFavorite) {
            userModel?.let {
                //gitHelper.deleteById("${it.id}")
                contentResolver.delete(uriWithId, null, null)
                Log.d(TAG, "Favorite Deleted")
                showToast("${it.login} Unfavorite")
                menuItem?.let { notLove ->
                    isFavorite = false
                    setLove(notLove)
                }
            }
        } else {
            val values = ContentValues()
            values.put(LOGIN_NAME, userModel?.login)
            values.put(AVATAR, userModel?.avatar)
            contentResolver.insert(CONTENT_URI, values)
            userModel?.login
            Log.d(TAG, "Favorite Added")
            showToast("Added ${userModel?.login} to Favorite")
            menuItem?.let { love ->
                isFavorite = true
                setLove(love)
            }
        }
    }


    private fun setFollowerFollowing(data: UserModel) {
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        sectionPagerAdapter.setData(data.login.toString())
        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)

        val txtFollower = resources.getString(R.string.tab_text1)
        tabs.getTabAt(0)?.text = "$txtFollower: ${userModel?.follower}"
        tabs.getTabAt(0)?.setIcon(R.drawable.followers)

        val txtFollowing = resources.getString(R.string.tab_text2)
        tabs.getTabAt(1)?.text = "$txtFollowing: ${userModel?.following}"
        tabs.getTabAt(1)?.setIcon(R.drawable.following)

        val bundle = Bundle()

        val followersFragment = FollowersFragment()
        bundle.putString(FollowersFragment.EXTRA_FOLLOWERS, data.login)
        followersFragment.arguments = bundle

        val followingFragment = FollowingFragment()
        bundle.putString(FollowingFragment.EXTRA_FOLLOWING, data.login)
        followingFragment.arguments = bundle
    }

    // toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}






































