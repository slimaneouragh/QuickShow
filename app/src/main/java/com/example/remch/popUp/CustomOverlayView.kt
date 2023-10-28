package com.example.remch.popUp

import android.annotation.SuppressLint
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
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavDeepLinkRequest
import com.example.domain.entity.Translations
import com.example.domain.usecase.GetThreeLastTranslation
import com.example.remch.MyViewModel
import com.example.remch.R
import com.example.remch.Window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration


@SuppressLint("ViewConstructor", "InflateParams")
class CustomOverlayView(
    context: Context,
    val window: WindowManager,
    val layoutParams: WindowManager.LayoutParams,
    text: List<Translations>
) : ViewGroup(context) {

    var layoutInflater: LayoutInflater
    var mview: View


    init {


        val randomFromList = text.random()



        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mview = layoutInflater.inflate(R.layout.popup, null)
        mview.findViewById<Button>(R.id.window_close).setOnClickListener {


            Window(window, layoutParams).close(mview)

        }


        text.let {
            mview.findViewById<TextView>(R.id.fromText).text = randomFromList.textFrom
            mview.findViewById<TextView>(R.id.toText).text = randomFromList.textTo
            mview.findViewById<TextView>(R.id.from).text = randomFromList.from
            mview.findViewById<TextView>(R.id.to).text = randomFromList.to
        }

        onAttachedToWindow()


    }


    fun createView(context: Context): View {
//        Window(window, layoutParams).close(mview)
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