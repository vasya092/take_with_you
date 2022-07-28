package com.example.takewith

import android.app.Application
import com.example.takewith.data.TakeableDatabase

class BaseApplication : Application() {
    val database : TakeableDatabase by lazy { TakeableDatabase.getDatabase(this) }
}