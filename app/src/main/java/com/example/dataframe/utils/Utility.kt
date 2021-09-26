package com.example.dataframe.utils

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD
import java.lang.StringBuilder

object Utility {
    fun getDefaultProgressIndicator(context: Context): KProgressHUD {
        return KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait ...")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }

    // function to generate a random string of length n
    fun getAlphaNumericRandomString(n: Int): String? {

        // chose a Character random from this String
        val AlphaNumericString =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"

        // create StringBuffer size of AlphaNumericString
        val sb = StringBuilder(n)
        for (i in 0 until n) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            val index = (AlphaNumericString.length
                    * Math.random()).toInt()

            // add Character one by one in end of sb
            sb.append(AlphaNumericString[index])
        }
        return sb.toString()
    }

    fun getSchoolAvatarColorsMap(){
        val colors = mutableMapOf<Int, String>()
        colors[1] = "#00FFFF"
        colors[2] = "#f88885"
        colors[3] = "#6495ED"
        colors[4] = "#cd0df6"
        colors[5] = "#add8e6"
        colors[6] = "#B22222"
        colors[7] = "#581845"
        colors[8] = "#5218FA"
        colors[9] = "#00416A"
        colors[10] = "#00A86B"
        colors[11] = "#C3B091"
        colors[12] = "#B57EDC"
        colors[13] = "#2fc6fe"
        colors[14] = "#e0d191"
        colors[15] = "#FF7F00"
        colors[16] = "#003153"
        colors[17] = "#436b95"
        colors[18] = "#734A12"
        colors[19] = "#5ccf70"
        colors[20] = "#008080"
        colors[21] = "#8B00FF"
        colors[22] = "#339966"
        colors[23] = "#C9A0DC"
        colors[24] = "#eeed09"
        colors[25] = "#a97164"
        colors[26] = "#506022"
    }
}