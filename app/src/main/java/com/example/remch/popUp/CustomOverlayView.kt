package com.example.remch.popUp

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavDeepLinkRequest
import com.example.domain.usecase.GetThreeLastTranslation
import com.example.remch.MyViewModel
import com.example.remch.R
import com.example.remch.Window
import javax.inject.Inject
import kotlin.random.Random


class CustomOverlayView(
    context: Context,
    val window: WindowManager,
    val layoutParams: WindowManager.LayoutParams,
    text: Array<String>
) : ViewGroup(context) {

    lateinit var layoutInflater: LayoutInflater
    lateinit var mview: View


    init {
        var list = arrayListOf<Array<String>>()
        val a = text.size / 4
        var index = 0
        for (i in 0 until a){
            list.add(text.sliceArray(index..(index+3)))
            index += 4
        }

        val randomFromList = list.random()



        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mview = layoutInflater.inflate(R.layout.popup, null)
        mview.findViewById<Button>(R.id.window_close).setOnClickListener {


            Window(window, layoutParams).close(mview)

        }

        text?.let {
            mview.findViewById<TextView>(R.id.fromText).setText(randomFromList[0])
            mview.findViewById<TextView>(R.id.toText).setText(randomFromList[1])
            mview.findViewById<TextView>(R.id.from).setText(randomFromList[2])
            mview.findViewById<TextView>(R.id.to).setText(randomFromList[3])
        }

    }


    fun createView(context: Context): View {

        return mview

    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        window.removeView(this)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    //
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // Add any necessary setup or listeners here
    }

    //
    override fun onDraw(canvas: android.graphics.Canvas) {
        // Perform any custom drawing operations here


    }

    override fun isAttachedToWindow(): Boolean {
        return super.isAttachedToWindow()

    }


}