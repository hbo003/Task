package com.smartestmedia.task.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.smartestmedia.task.R
import com.smartestmedia.task.utils.getNavOptions

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val splashTimeOut: Long = 3000 // 3 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToHome()
    }

    private fun goToHome() {
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().popBackStack()
            findNavController().navigate(R.id.home, null, getNavOptions)
        }, splashTimeOut)
    }
}