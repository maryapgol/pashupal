package com.aztechz.probeez.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataProcessor(private var context: Context) {

    private var preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    fun sharedPreferenceExist(key: String?): Boolean {

        return preferences.contains(key)
    }

    fun clearSharedPreferences() {

        preferences?.edit()?.clear()?.apply()
    }

    fun setInt(key: String?, value: Int) {

        val editor = preferences?.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

    fun getInt(key: String?): Int? {

        return preferences?.getInt(key, 0)
    }

    fun setStr(key: String?, value: String?) {
       Log.i("DataProcessor","Value: "+value)
        val editor = preferences.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getStr(key: String?): String? {

        return preferences.getString(key, "DNF")
    }

    fun setBool(key: String?, value: Boolean) {

        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBool(key: String?): Boolean {

        return preferences.getBoolean(key, false)
    }

    fun setObject(serializedObjectKey: String?, `object`: Any?) {

        val sharedPreferencesEditor = preferences.edit()
        val gson = Gson()
        val serializedObject = gson.toJson(`object`)
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject)
        sharedPreferencesEditor.apply()
    }

    fun <GenericClass> getObject(
        key: String?,
        classType: Class<GenericClass>?
    ): GenericClass? {

        if (preferences.contains(key)) {
            val gson = Gson()
            return gson.fromJson(preferences.getString(key, ""), classType)
        }
        return null
    }


    fun setArrayList(key: String?,
                     list: ArrayList<*>) {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun <GenericClass> getArrayList(key: String?,classType: Class<GenericClass>?): GenericClass {

        val gson = Gson()
        val json: String? = preferences.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<GenericClass>?>() {}.type
        return gson.fromJson(json, type)

    }

    companion object {
        const val PREFS_NAME = "om.aztechz.manamatrimony"
    }


}