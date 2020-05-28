package com.example.demo_rxjava_android.view.rxBus

import android.app.Application

class MyApplication : Application() {

    val TAG = "MyApplication"
    private var bus: RxBus? = null


    override fun onCreate() {
        super.onCreate()
        bus = RxBus()
    }

     fun bus(): RxBus? {
        return bus
    }


}