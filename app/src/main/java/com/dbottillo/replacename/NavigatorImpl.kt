package com.dbottillo.replacename

import android.app.Activity
import android.content.Intent
import com.dbottillo.replacename.feature.about.AboutActivity

class NavigatorImpl : Navigator {

    override fun openAboutScreen(origin: Activity) {
        origin.startActivity(Intent(origin, AboutActivity::class.java))
    }
}
