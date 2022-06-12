package com.example.android13

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.content.*
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.donkingliang.imageselector.utils.ImageSelector


class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE_GALLERY = 11
        private const val REQUEST_CODE_NOTIFICATION_PERMISSION = 22
        private const val REQUEST_CODE_EXTERNAL_PERMISSION = 33
        private const val PHOTO_PICKER_REQUEST_CODE = 12
        private const val CUSTOM_BROADCAST = "CUSTOM_BROADCAST"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        intentFilter.addAction(CUSTOM_BROADCAST)
        if (Build.VERSION.SDK_INT >= 33) {
//            registerReceiver(sharedBroadcastReceiver, intentFilter,
//                RECEIVER_NOT_EXPORTED)
        }

    }

    private val sharedBroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.i(TAG, "onReceive: ${p1?.action}")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(sharedBroadcastReceiver)
    }


    fun openFile(view: View) {
        // 之前的图片选择
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(galleryIntent, "选择图片"),
            REQUEST_CODE_GALLERY
        )
    }

    fun openPic1(view: View) {
//        ActivityCompat.requestPermissions(this,
//            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_EXTERNAL_PERMISSION)
//        ActivityCompat.requestPermissions(this,
//            arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_CODE_EXTERNAL_PERMISSION)
//        ImageSelector.builder()
//            .useCamera(true) // 设置是否使用拍照
//            .setSingle(false)  //设置是否单选
//            .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
////            .setSelected(false) // 把已选的图片传入默认选中。
//            .canPreview(true) //是否可以预览图片，默认为true
//            .start(this, PHOTO_PICKER_REQUEST_CODE); // 打开相册
    }
    fun openPic2(view: View) {
        // Android13 的图片选择
        // 单张照片选择，会以半屏的样式展开选择
//        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
//        startActivityForResult(intent, PHOTO_PICKER_REQUEST_CODE)
    }

    @RequiresApi(33)
    fun openPic3(view: View) {
        // 多张照片选择
//        val pickImagesMaxLimit = MediaStore.getPickImagesMaxLimit()
//        Log.i(TAG, "openPic3:  pickImagesMaxLimit = $pickImagesMaxLimit")
//        val maxNumPhotosAndVideos = 2
//        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
//        intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, maxNumPhotosAndVideos);
//        startActivityForResult(intent, PHOTO_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(TAG, "onActivityResult: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (requestCode == REQUEST_CODE_EXTERNAL_PERMISSION){
                ImageSelector.builder()
                    .useCamera(true) // 设置是否使用拍照
                    .setSingle(false)  //设置是否单选
                    .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
//            .setSelected(false) // 把已选的图片传入默认选中。
                    .canPreview(true) //是否可以预览图片，默认为true
                    .start(this, PHOTO_PICKER_REQUEST_CODE); // 打开相册
            } else{
                val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val channelId = "ChannelId" // 通知渠道

                val notification: Notification = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("通知标题")
                    .setContentText("通知内容")
                    .build()
                // 2. 获取系统的通知管理器(必须设置channelId)
                val channel = NotificationChannel(
                    channelId,
                    "通知的渠道名称",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                nm.createNotificationChannel(channel)
                nm.notify(11, notification)
            }

        }

    }

    fun sendReceiver(view: View) {
        val intent = Intent()
        intent.action = CUSTOM_BROADCAST
        sendBroadcast(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNotify(view: View) {
//        JobInfo.Builder.setPrefetch()
//        if (Build.VERSION.SDK_INT < 33){
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            Log.i(TAG, "sendNotify: notify enable = ${nm.areNotificationsEnabled()}")
            val channelId = "ChannelId" // 通知渠道

        var notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.areNotificationsEnabled()

        val notification: Notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("通知标题")
                .setContentText("通知内容")
                .build()
            // 2. 获取系统的通知管理器(必须设置channelId)
            val channel = NotificationChannel(
                channelId,
                "通知的渠道名称",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            nm.createNotificationChannel(channel)
            nm.notify(11, notification)
//        }
//        ActivityCompat.requestPermissions(this,
//            arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_NOTIFICATION)
    }

    fun intentJump(view: View) {
        // val intent = Intent()
//        val intent = Intent()
        val intent = Intent("android.intent.action.TEST")
        intent.addCategory("customer.category.here")
        intent.addCategory(Intent.CATEGORY_DEFAULT)
//        val uri = "content://com.example.intentdemo：8080/abc.pdf".toUri()
//        intent.setDataAndType(uri, "text/plain");
//        intent.data = "http://baidu.com".toUri()
//        intent.component = ComponentName(this,"com.example.android13.TestIntentActivity")
//        intent.action = "android.intent.action.TEST11"
        startActivity(intent)
    }

    fun wifiInfo(view: View) {
//        ActivityCompat.requestPermissions(this,
//            arrayOf(Manifest.permission.READ_N), REQUEST_CODE_EXTERNAL_PERMISSION)
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val ssid = wifiManager.connectionInfo.ssid
        Log.i(TAG, "onCreate: ssid = $ssid")
//        wifiManager.startLocalOnlyHotspot(object :WifiManager.LocalOnlyHotspotCallback(){
//
//        })
    }


}