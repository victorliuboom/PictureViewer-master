package com.victorliu.pictureviewer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.victorliu.pictureviewer.view.CustomLoadingUIProvider

private var iwHelper: ImageWatcherHelper? = null
var isTranslucentStatus = false


class MainActivity : AppCompatActivity(),ImageWatcher.OnPictureLongPressListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        新的初始化方式二，不再需要在布局文件中加入<ImageWatcher>标签 减少布局嵌套
        iwHelper =
            ImageWatcherHelper.with(this, GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(this)
                .setOnStateChangedListener(object : ImageWatcher.OnStateChangedListener {
                    override fun onStateChangeUpdate(
                        imageWatcher: ImageWatcher?,
                        clicked: ImageView?,
                        position: Int,
                        uri: Uri,
                        animatedValue: Float,
                        actionTag: Int
                    ) {
                        Log.e(
                            "IW",
                            "onStateChangeUpdate [$position][$uri][$animatedValue][$actionTag]"
                        )
                    }

                    override fun onStateChanged(
                        imageWatcher: ImageWatcher?,
                        position: Int,
                        uri: Uri,
                        actionTag: Int
                    ) {
                        if (actionTag == ImageWatcher.STATE_ENTER_DISPLAYING) {
                            Toast.makeText(
                                applicationContext,
                                "点击了图片 [$position]$uri",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (actionTag == ImageWatcher.STATE_EXIT_HIDING) {
                            Toast.makeText(applicationContext, "退出了查看大图", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) //                .setIndexProvider(new CustomDotIndexProvider())//自定义页码指示器（默认数字）
                .setLoadingUIProvider(CustomLoadingUIProvider()) // 自定义LoadingUI
        var list= listOf(Uri.parse("https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF"),Uri.parse("https://t7.baidu.com/it/u=3785402047,1898752523&fm=193&f=GIF"),Uri.parse("https://t7.baidu.com/it/u=3378243861,1554020226&fm=193&f=GIF"))
        findViewById<Button>(R.id.btn).setOnClickListener {
            iwHelper?.show(list,0)
        }
    }

    override fun onPictureLongPress(v: ImageView?, uri: Uri?, pos: Int) {
        Toast.makeText(
            v!!.context.applicationContext,
            "长按了第" + (pos + 1) + "张图片",
            Toast.LENGTH_SHORT
        ).show()

    }
}