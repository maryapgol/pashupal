package com.aztechz.probeez

import android.app.Application
import android.content.Context
import androidx.viewbinding.BuildConfig
import com.aztechz.probeez.util.ReleaseTree
import com.aztechz.probeez.utils.Utility
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Utility.setFont(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                        "Class:%s: Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        } else {
            Timber.plant(ReleaseTree())
        }
    }
/*

    override fun attachBaseContext(newBase: Context) {// get chosen language from sharad preference
        val country = "IN"
        val lang: String = if (DataProcessor(newBase).sharedPreferenceExist(Constants().language))
            DataProcessor(newBase).getStr(Constants().language).toString()
        else "en"

        try {
            LocaleHelper.setLocale(newBase, lang)
            val localeToSwitchTo = Locale(lang, country)
            val localeUpdatedContext: ContextWrapper =
                ContextUtils.updateLocale(newBase, localeToSwitchTo)
            super.attachBaseContext(LocaleHelper.onAttach(newBase, lang))
        } catch (e: Exception) {
            val f = FirebaseCrashlytics.getInstance()
            f.recordException(e)
            Toast.makeText(newBase, "Language change failed", Toast.LENGTH_SHORT).show()
        }
    }
*/

    companion object {
        var appContext: Context? = null
            private set
    }
}