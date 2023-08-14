package com.example.composetutorial.repo

import androidx.paging.PagingData
import com.example.composetutorial.model.Game
import com.example.composetutorial.model.Response
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getAllGames(): Flow<PagingData<Game>>
    fun getDetailGame(id: Int): Flow<Response<Game>>
}