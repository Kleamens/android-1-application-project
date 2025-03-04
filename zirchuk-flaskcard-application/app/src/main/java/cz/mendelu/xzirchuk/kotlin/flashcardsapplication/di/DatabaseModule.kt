package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.di

import android.content.Context
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.DecksDatabase
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(context: Context):DecksDatabase{
        return DecksDatabase.getDatabase(context)
    }
    single { provideDatabase(get()) }
}