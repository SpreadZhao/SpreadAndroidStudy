package com.example.composetutorial.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.composetutorial.model.Game
import com.example.composetutorial.model.GamesPagingSource
import com.example.composetutorial.model.Response
import com.example.composetutorial.network.GamesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class GamesRepositoryImpl constructor(
    private val gamesService: GamesService = GamesService.getInstance()
): GamesRepository {
    override fun getAllGames(): Flow<PagingData<Game>> = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            GamesPagingSource(
                response = { pageNext ->
                    gamesService.getAllGames(
                        page = pageNext,
                        pageSize = 5
                    )
                }
            )
        }
    ).flow

    override fun getDetailGame(id: Int): Flow<Response<Game>> = flow {
        try {
            emit(Response.Loading)
            val responseApi = gamesService.getGamesDetail(id = id)
            emit(Response.Success(responseApi))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}