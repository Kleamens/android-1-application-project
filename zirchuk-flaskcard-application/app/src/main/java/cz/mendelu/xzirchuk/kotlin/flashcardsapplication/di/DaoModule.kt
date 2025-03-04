package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.di

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.DecksDao
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.DecksDatabase
import org.koin.dsl.module

val daoModule = module{
    fun provideDaoModule(database: DecksDatabase):DecksDao{
        return database.decksDao()
    }
    single { provideDaoModule(get()) }
}