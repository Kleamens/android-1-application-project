package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared

import android.content.Context
import android.preference.Preference
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {
    companion object{
        private val Context.dataStore:androidx.datastore.core.DataStore<Preferences> by preferencesDataStore("AddInput")
        val ADD_DECK_DESC_KEY = stringPreferencesKey("add_deck_desc_key")
        val ADD_DECK_NAME_KEY = stringPreferencesKey("add_deck_name_key")
        val SEARCH_DECK_NAME = stringPreferencesKey("search_deck_name_key")
    }

    val getDeckName: Flow<String> = context.dataStore.data.map{
        it[ADD_DECK_NAME_KEY] ?:""
    }

    val getDeckDesc: Flow<String> = context.dataStore.data.map{
        it[ADD_DECK_DESC_KEY] ?:""
    }

    val getSearchName: Flow<String> = context.dataStore.data.map{
        it[SEARCH_DECK_NAME] ?:""
    }
    suspend fun saveName(name:String){
        context.dataStore.edit {
            it[ADD_DECK_NAME_KEY] = name
        }
    }

    suspend fun saveSearchName(name:String){
        context.dataStore.edit {
            it[SEARCH_DECK_NAME] = name
        }
    }



    suspend fun saveDesc(desc:String){
        context.dataStore.edit {
            it[ADD_DECK_DESC_KEY] = desc
        }
    }
}