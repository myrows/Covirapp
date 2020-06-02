package com.example.covirapp.common

import android.content.Context
import android.content.SharedPreferences
import com.example.covirapp.di.MyApplication


class SharedPreferencesManager {

    object SharedPreferencesManager {
        private const val APP_SETTINGS_FILE = "APP_SETTINGS"

        private val sharedPreferences: SharedPreferences

            private get() = MyApplication.instance.getSharedPreferences(
                APP_SETTINGS_FILE,
                Context.MODE_PRIVATE
            )

        fun setSomeIntValue(dataLabel: String?, dataValue: Int) {
            val editor =
                sharedPreferences.edit()
            editor.putInt(dataLabel, dataValue)
            editor.commit()
        }

        fun setSomeStringValue(dataLabel: String?, dataValue: String?) {
            val editor =
                sharedPreferences.edit()
            editor.putString(dataLabel, dataValue)
            editor.commit()
        }

        fun setSomeBooleanValue(dataLabel: String?, dataValue: Boolean) {
            val editor =
                sharedPreferences.edit()
            editor.putBoolean(dataLabel, dataValue)
            editor.commit()
        }

        fun getSomeIntValue(dataLabel: String?): Int {
            return sharedPreferences.getInt(dataLabel, 0)
        }

        fun getSomeStringValue(dataLabel: String?): String? {
            return sharedPreferences.getString(dataLabel, null)
        }

        fun getSomeBooleanValue(dataLabel: String?): Boolean {
            return sharedPreferences.getBoolean(dataLabel, false)
        }
    }
}