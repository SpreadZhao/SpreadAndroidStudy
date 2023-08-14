package com.example.databasetest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.databasetest.data.DeformAdj
import com.example.databasetest.data.DeformVerb
import com.example.databasetest.data.Sentence
import com.example.databasetest.data.Word

@Database(
    version = 1,
    entities = [
        Word::class,
        DeformVerb::class,
        DeformAdj::class,
        Sentence::class
    ]
)
abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        private var instance: WordDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): WordDatabase {
            instance?.let {
                return it
            }
            /**
             * fallbackToDestructiveMigration()
             *      Destroy the database with its tables with data on upgrade,
             *      so that we can easily make changes to the structure of the
             *      new tables.
             */
            return Room.databaseBuilder(
                context.applicationContext,
                WordDatabase::class.java,
                "word_database"
            ).fallbackToDestructiveMigration().build().apply {
                instance = this
            }
        }
    }
}