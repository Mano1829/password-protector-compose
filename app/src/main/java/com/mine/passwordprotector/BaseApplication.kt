package com.mine.passwordprotector

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("TAG" , "onCreated")
    }

}