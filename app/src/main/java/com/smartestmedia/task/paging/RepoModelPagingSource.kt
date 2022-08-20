package com.smartestmedia.task.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smartestmedia.task.api.ApiService
import com.smartestmedia.task.model.RepoModel
import com.smartestmedia.task.model.ResultApi


class RepoModelPagingSource
    (
    private val apiService: ApiService
) : PagingSource<Int, RepoModel>() {

    override fun getRefreshKey(state: PagingState<Int, RepoModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, RepoModel> {

        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getAllCharacters(currentPage)
            val responseData = mutableListOf<RepoModel>()

            val jsonResponse: ResultApi? = response.body()
            val jdata = listOf(jsonResponse)
            jdata.forEach {
                it?.let { it1 -> responseData.addAll(it1) }
            }


            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}