package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.di

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.DecksDao
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.DecksRepossitoryImpl
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import org.koin.dsl.module

val repositoryModule = module{
    fun provideCardsRepository(dao: DecksDao):IDecksRepository{
        return DecksRepossitoryImpl(dao)
    }
    single { provideCardsRepository(get()) }
}