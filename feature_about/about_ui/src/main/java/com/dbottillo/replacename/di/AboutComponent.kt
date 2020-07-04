package com.dbottillo.replacename.di

import com.dbottillo.replacename.feature.about.AboutActivity
import dagger.Subcomponent

@Subcomponent
interface AboutComponent {
    fun inject(activity: AboutActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): AboutComponent
    }
}
