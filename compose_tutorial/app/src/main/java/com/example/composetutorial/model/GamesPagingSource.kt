package com.example.composetutorial.model

import androidx.paging.PagingSource
import androidx.paging.PagingState

class GamesPagingSource(
    private val response: suspend (Int) -> GamesResponse
) : PagingSource<Int, Game>() {
    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val nextPage = params.key ?: 1
            val gamesList = response.invoke(nextPage)
            LoadResult.Page(
                data = gamesList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = gamesList
                    .next
                    ?.substringAfter("page=")
                    ?.substringBefore("&")
                    ?.toInt() ?: nextPage
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

}