package com.shaban.myapplication

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.arkivanov.mvikotlin.timetravel.server.TimeTravelServer

class App : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        // Time-Travel is a debugging tool and may affect performance, ideally it should not be used in production.
        // It allows you to record all events and states from all active Stores
        TimeTravelServer().start()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {  // Configure memory cache settings.
                MemoryCache.Builder(this)
                    .maxSizePercent(0.10)   // Set memory cache size to 10% of the app's total memory.
                    .build()
            }
            .diskCache {    // Configure disk cache settings
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache")) // Set disk cache directory path
                    .maxSizeBytes(5 * 1024 * 1024)  // Set disk cache size to 5 MB.
                    .build()
            }
            .logger(DebugLogger())  // Enable debug logging for ImageLoader operations like (get image from memory or disk).
            .build()
    }
}
