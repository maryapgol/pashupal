package com.aztechz.probeez.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataProcessor(private var context: Context) {

    fun sharedPreferenceExist(key: String?): Boolean {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        return prefs.contains(key)
    }

    fun clearSharedPreferences() {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        prefs?.edit()?.clear()?.apply()
    }

    fun setInt(key: String?, value: Int) {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        val editor = prefs?.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

    fun getInt(key: String?): Int? {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        return prefs?.getInt(key, 0)
    }

    fun setStr(key: String?, value: String?) {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        val editor = prefs?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getStr(key: String?): String? {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        return prefs.getString(key, "DNF")
    }

    fun setBool(key: String?, value: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBool(key: String?): Boolean {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        return prefs.getBoolean(key, false)
    }

    fun setObject(serializedObjectKey: String?, `object`: Any?) {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        val sharedPreferencesEditor = prefs.edit()
        val gson = Gson()
        val serializedObject = gson.toJson(`object`)
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject)
        sharedPreferencesEditor.apply()
    }

    fun <GenericClass> getObject(
        preferenceKey: String?,
        classType: Class<GenericClass>?
    ): GenericClass? {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        if (prefs.contains(preferenceKey)) {
            val gson = Gson()
            return gson.fromJson(prefs.getString(preferenceKey, ""), classType)
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
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            0
        )
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<GenericClass>?>() {}.type
        return gson.fromJson(json, type)

    }

    companion object {
        const val PREFS_NAME = "om.aztechz.manamatrimony"
    }


}