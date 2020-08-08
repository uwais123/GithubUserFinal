package com.masuwes.githubuserfinal.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.masuwes.githubuserfinal.R
import android.provider.Settings
import android.util.Log
import com.masuwes.githubuserfinal.alarm.AlarmReceiver
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        const val SHARED_PREFERENCE = "sharedpreference"
        const val BOOLEAN_KEY = "booleankey"
        internal val TAG = SettingsActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = resources.getString(R.string.settings)
        }

        alarmReceiver = AlarmReceiver()

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)

        val getBoolean = sharedPreferences.getBoolean(BOOLEAN_KEY, false)
        checkbox_alarm.isChecked = getBoolean

        checkbox_alarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val editor = sharedPreferences.edit()
                editor.apply {
                    putBoolean(BOOLEAN_KEY, true)
                }.apply()

                alarmReceiver.setRepeatAlarm(this, AlarmReceiver.EXTRA_TYPE, "09:00")
                Log.d(TAG, "Alarm On")
            } else {
                val editor = sharedPreferences.edit()
                editor.apply {
                    putBoolean(BOOLEAN_KEY, false)
                }.apply()

                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_ALARM_REPEATING)
                Log.d(TAG, "Alarm Off")
            }
        }

        // change language
        constsatu.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}












