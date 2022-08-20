package com.smartestmedia.task.utils

import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.smartestmedia.task.R

val getNavOptions: NavOptions
    get() = navOptions {
        anim {
            enter = R.anim.enter
            exit = R.anim.exit
        }
    }