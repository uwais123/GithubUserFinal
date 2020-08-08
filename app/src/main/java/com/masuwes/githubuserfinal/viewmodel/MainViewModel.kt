package com.masuwes.githubuserfinal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.masuwes.githubuserfinal.model.UserModel
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel: ViewModel() {

    companion object {
        private var TAG = MainViewModel::class.java.simpleName
    }

    val listUser = MutableLiveData<ArrayList<UserModel>>()

    fun setUser(username: String?) {

        val client = AsyncHttpClient()

        val listItem = ArrayList<UserModel>()
        val userUrl: MutableList<String> = ArrayList()

        val searchUrl = "https://api.github.com/search/users?q=$username"

        client.addHeader("Authorization", "token 0c8b312623b4c7a626a7840635915e8364f0b936")
        client.addHeader("User-Agent", "request")

        client.get(searchUrl, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG , "result")

                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        userUrl.add(item.getString("url"))
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

                for (i in 0 until userUrl.lastIndex + 1) {
                    client.get(userUrl[i], object : AsyncHttpResponseHandler() {
                        override fun onSuccess(
                            statusCode: Int,
                            headers: Array<out Header>,
                            responseBody: ByteArray
                        ) {
                            val resultDetail = String(responseBody)
                            Log.d(TAG , "resultDetail")

                            try {
                                val responseObject = JSONObject(resultDetail)

                                val user =
                                    UserModel()
                                user.login = responseObject.getString("login")
                                user.name = responseObject.getString("name")
                                user.avatar = responseObject.getString("avatar_url")
                                user.company = responseObject.getString("company")
                                user.location = responseObject.getString("location")
                                user.repository = responseObject.getInt("public_repos").toString()
                                user.follower = responseObject.getInt("followers")
                                user.following = responseObject.getInt("following")
                                listItem.add(user)

                            } catch (e: Exception) {
                                Log.d("Exception", e.message.toString())
                            }

                            listUser.postValue(listItem)
                        }

                        override fun onFailure(
                            statusCode: Int,
                            headers: Array<out Header>,
                            responseBody: ByteArray,
                            error: Throwable
                        ) {
                            Log.d("onFailure", error.message.toString())
                        }

                    })
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun getUser(): LiveData<ArrayList<UserModel>> = listUser
}










































