package com.ddanddan.watch.application

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.ddanddan.watch.util.WorkerUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WatchApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        WorkerUtils.run {
            setupDailyMidnightResetWorker(this@WatchApp) // 자정에 칼로리 초기화
            scheduleSyncCaloriesWorker(this@WatchApp) // 1시간 간격으로 칼로리 보정
        }
    }
}
