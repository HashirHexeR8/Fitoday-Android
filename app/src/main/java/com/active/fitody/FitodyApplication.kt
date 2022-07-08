package com.active.fitody

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import cn.icomon.icdevicemanager.ICDeviceManager
import cn.icomon.icdevicemanager.model.other.ICDeviceManagerConfig

class FitodyApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: FitodyApplication? = null

        fun context() : FitodyApplication {
            return instance as FitodyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val config = ICDeviceManagerConfig()
        config.context = this.applicationContext
        ICDeviceManager.shared().delegate = FitodyDeviceManager.getInstance()
        ICDeviceManager.shared().initMgrWithConfig(config)
    }
}