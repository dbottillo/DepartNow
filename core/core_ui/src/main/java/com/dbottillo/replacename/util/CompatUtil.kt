package com.dbottillo.replacename.util

import android.os.Build

object CompatUtil {
    @JvmField
    val minSdk24 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    @JvmField
    val minSdk26 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    @JvmField
    val minSdk27 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1

    @JvmField
    val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}
