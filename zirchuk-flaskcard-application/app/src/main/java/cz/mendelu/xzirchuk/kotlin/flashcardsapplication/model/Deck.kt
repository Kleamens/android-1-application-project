package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import com.google.gson.reflect.TypeToken

@Entity(tableName = "decks")
data class Deck(

    @ColumnInfo var name:String,
        @ColumnInfo var cards:ArrayList<Card>,
    @ColumnInfo var lastVisited:String,//is saved as String and is converted
    @ColumnInfo var description:String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id:Long? = null
)
{
    fun copy(name: String = this.name,
             cards: ArrayList<Card> = this.cards,
             lastVisited:String = this.lastVisited,
             description: String = this.description):Deck{
        return Deck(name= name, cards = cards,lastVisited = lastVisited,description=description)
    }
}

class CardTypeConverter{
    @TypeConverter
    fun fromStringToList(value:String?):ArrayList<Card>{

        val sType = object : TypeToken<ArrayList<Card>>() { }.type
       return Gson().fromJson(value, sType)

    }

    @TypeConverter
    fun fromListToString(list:ArrayList<Card?>):String{

        return Gson().toJson(list)
    }


    @TypeConverter
    fun fromCardToString(card: Card):String{
        return Gson().toJson(card)
    }
    @TypeConverter
    fun fromStringtoCard(string: String):Card{
        val sType = object : TypeToken<Card>() { }.type
        return Gson().fromJson(string,sType)
    }
}