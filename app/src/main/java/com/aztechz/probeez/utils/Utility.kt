package com.aztechz.probeez.utils

import android.graphics.Typeface
import com.aztechz.probeez.MyApplication

object Utility {

    internal var fontRegular: Typeface? = null
    internal var fontBold : Typeface? = null
    internal  var fontMedium : Typeface? = null
    internal var fontSemiBold : Typeface? = null
    internal  var fontThin : Typeface? = null
    internal  var fontButton : Typeface? = null

     fun setFont(myApplication: MyApplication)
     {
         fontBold = Typeface.createFromAsset(myApplication.assets, "fonts/work_sans_bold.ttf")
         fontRegular = Typeface.createFromAsset(myApplication.assets, "fonts/work_sans_regular.ttf")
         fontMedium = Typeface.createFromAsset(myApplication.assets, "fonts/work_sans_medium.ttf")
         fontSemiBold = Typeface.createFromAsset(myApplication.assets, "fonts/work_sans_semibold.ttf")
         fontThin = Typeface.createFromAsset(myApplication.assets, "fonts/work_sans_thin.ttf")
         fontButton = Typeface.createFromAsset(myApplication.assets, "fonts/work_sans_medium.ttf")
     }

}