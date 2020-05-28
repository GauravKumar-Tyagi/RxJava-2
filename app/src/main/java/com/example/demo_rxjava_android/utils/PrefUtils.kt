package com.example.demo_rxjava_android.utils

import android.content.Context
import android.content.SharedPreferences


/*
This class stores and retrieves the API Key that needs to be sent in every HTTP call as Authorization header field.
 */
object PrefUtils {

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
    }

    fun storeApiKey(context: Context, apiKey: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString("API_KEY", apiKey)
        editor.commit()
    }

    fun getApiKey(context: Context): String? {
        return getSharedPreferences(context).getString("API_KEY", null)
    }
}