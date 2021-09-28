package com.victorliu.pictureviewer.view

import android.content.Context
import android.widget.FrameLayout
import android.view.ViewGroup
import android.view.Gravity
import com.victorliu.pictureviewer.R
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.ImageView
import com.victorliu.pictureviewer.ImageWatcher

class CustomLoadingUIProvider : ImageWatcher.LoadingUIProvider {
    private val lpCenterInParent = FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    override fun initialView(context: Context?): View {
        val load = ImageView(context)
        lpCenterInParent.gravity = Gravity.CENTER
        load.layoutParams = lpCenterInParent
        load.setImageResource(R.drawable.dice_action)
        return load
    }

    override fun start(loadView: View) {
        loadView.visibility = View.VISIBLE
        ((loadView as ImageView).drawable as AnimationDrawable).start()
    }

    override fun stop(loadView: View) {
        ((loadView as ImageView).drawable as AnimationDrawable).stop()
        loadView.setVisibility(View.GONE)
    }
}