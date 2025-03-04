package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model

import androidx.room.PrimaryKey


data class Card(
    var image: String?,//URI of the image in String form
    var front:String,//Front text of the card
    var back:String,//Text on the back of the card
    var lastCompleted:String, //Date of last completion
    var complete:Boolean
    ) {
    override fun equals(other: Any?): Boolean {
        if(other is Card){
            var card = other as Card
            return card.back == this.back && card.front == this.front &&  card.image == this.image && card.lastCompleted == this.lastCompleted
        }
        return false
    }

}