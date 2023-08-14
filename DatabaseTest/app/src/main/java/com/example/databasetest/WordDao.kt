package com.example.databasetest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.databasetest.data.DeformVerb
import com.example.databasetest.data.Sentence
import com.example.databasetest.data.Word

@Dao
interface WordDao {
    @Insert
    fun insertSentence(sentence: Sentence): Long

    @Insert
    fun insertDeformVerb(deformVerb: DeformVerb): Long

    @Insert
    fun insertWord(word: Word): Long

    @Query("select * from sentence")
    fun getAllSentences(): List<Sentence>

    @Query("select * from word")
    fun getAllWords(): Array<Word>
}