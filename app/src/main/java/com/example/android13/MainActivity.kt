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
        // ?????????????????????
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(galleryIntent, "????????????"),
            REQUEST_CODE_GALLERY
        )
    }

    fun openPic1(view: View) {
//        ActivityCompat.requestPermissions(this,
//            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_EXTERNAL_PERMISSION)
//        ActivityCompat.requestPermissions(this,
//            arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_CODE_EXTERNAL_PERMISSION)
//        ImageSelector.builder()
//            .useCamera(true) // ????????????????????????
//            .setSingle(false)  //??????????????????
//            .setMaxSelectCount(9) // ??????????????????????????????????????????0?????????????????????
////            .setSelected(false) // ???????????????????????????????????????
//            .canPreview(true) //????????????????????????????????????true
//            .start(this, PHOTO_PICKER_REQUEST_CODE); // ????????????
    }
    fun openPic2(view: View) {
        // Android13 ???????????????
        // ??????????????????????????????????????????????????????
//        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
//        startActivityForResult(intent, PHOTO_PICKER_REQUEST_CODE)
    }

    @RequiresApi(33)
    fun openPic3(view: View) {
        // ??????????????????
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
                    .useCamera(true) // ????????????????????????
                    .setSingle(false)  //??????????????????
                    .setMaxSelectCount(9) // ??????????????????????????????????????????0?????????????????????
//            .setSelected(false) // ???????????????????????????????????????
                    .canPreview(true) //????????????????????????????????????true
                    .start(this, PHOTO_PICKER_REQUEST_CODE); // ????????????
            } else{
                val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val channelId = "ChannelId" // ????????????

                val notification: Notification = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("????????????")
                    .setContentText("????????????")
                    .build()
                // 2. ??????????????????????????????(????????????channelId)
                val channel = NotificationChannel(
                    channelId,
                    "?????????????????????",
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
            val channelId = "ChannelId" // ????????????

        var notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.areNotificationsEnabled()

        val notification: Notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("????????????")
                .setContentText("????????????")
                .build()
            // 2. ??????????????????????????????(????????????channelId)
            val channel = NotificationChannel(
                channelId,
                "?????????????????????",
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
//        val uri = "content://com.example.intentdemo???8080/abc.pdf".toUri()
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