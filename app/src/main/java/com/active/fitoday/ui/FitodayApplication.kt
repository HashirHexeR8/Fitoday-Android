package com.active.fitoday.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class FitodayApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}