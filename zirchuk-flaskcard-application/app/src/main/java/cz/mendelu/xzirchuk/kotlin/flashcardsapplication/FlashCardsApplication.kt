package cz.mendelu.xzirchuk.kotlin.flashcardsapplication

import android.app.Application
import android.content.Context
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.di.daoModule
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.di.databaseModule
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.di.repositoryModule
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlashCardsApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        appContext=applicationContext
        startKoin {
            androidContext(this@FlashCardsApplication)
            modules(listOf(
                viewModelModule,
                repositoryModule,
                daoModule,
                databaseModule

            ))

        }
    }

    companion object{
        lateinit var appContext: Context
            private set

    }
}