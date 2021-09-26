package com.example.dataframe.utils

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

object Utility {
    fun getDefaultProgressIndicator(context: Context): KProgressHUD {
        return KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait ...")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }
}