package com.example.app2

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
    companion object{

        private const val CUSTOM_BROADCAST = "CUSTOM_BROADCAST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun sendReceiver(view: View) {
        val intent = Intent()
        intent.action = "CUSTOM_BROADCAST"
        sendBroadcast(intent)
    }
    fun intentJump(view: View) {
//        val intent = Intent()
//        val intent = Intent("android.intent.action.TEST")
//        intent.addCategory(Intent.CATEGORY_DEFAULT)
//        intent.addCategory("customer.category.here")
//        val uri = "content://com.example.android13".toUri()
//        intent.setDataAndType(uri, "text/plain")
//        intent.data = "http://baidu.com".toUri()
        intent.component = ComponentName("com.example.android13",
            "com.example.android13.TestIntentActivity")
        intent.action = "android.intent.action.TEST"
        startActivity(intent)
    }
}