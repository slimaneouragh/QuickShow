package com.example.remch

import android.content.Context
import android.view.View
import android.view.WindowManager
import javax.inject.Inject


class Window (window: WindowManager, layoutParams: WindowManager.LayoutParams) {

    private val window = window
    private val layoutParams = layoutParams

    fun open(view: View) {
        window.addView(view, layoutParams)
    }

    fun close(view: View) {
        window.removeView(view)
    }
}





