package com.dbottillo.replacename.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.dbottillo.replacename.domain.R

object AppIcons {
    val Home = R.drawable.ic_home_24
    val About = R.drawable.ic_info_24
    val Settings = Icons.Rounded.Settings
}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
