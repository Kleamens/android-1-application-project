package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database

import android.content.Context
import androidx.room.*
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.CardTypeConverter
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck


@Database(entities = [Deck::class], version = 1 , exportSchema = true)
@TypeConverters(CardTypeConverter::class)
abstract class DecksDatabase :RoomDatabase(){
    abstract fun decksDao():DecksDao

    companion object{
        private var INSTANCE : DecksDatabase? = null
        fun getDatabase(context:Context):DecksDatabase{
            if(INSTANCE == null){
                synchronized(DecksDatabase::class.java)
                {
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DecksDatabase::class.java,
                            "decks_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!

        }
    }
}