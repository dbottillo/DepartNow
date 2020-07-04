package com.dbottillo.replacename.feature.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dbottillo.replacename.di.AboutComponentProvider
import kotlinx.android.synthetic.main.activity_about.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ((applicationContext as AboutComponentProvider).provideAboutComponent()).inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)
    }
}
