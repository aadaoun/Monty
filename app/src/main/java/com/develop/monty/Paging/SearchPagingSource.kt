package com.develop.monty.Paging

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.develop.monty.MainActivity
import com.develop.monty.Network.RetroService
import com.develop.monty.Network.SearchService
import com.develop.monty.models.Repo

class SearchPagingSource(val apiService: SearchService,val que:String): PagingSource<Int, Repo>() {
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.getSearchAPI(nextPage.toString()).filter { it.name.contains(que) }
            var nextPageNumber: Int? = null
            if(nextPage<200) {
                nextPageNumber = nextPage+1
            }
            var prevPageNumber: Int? = null
            if(nextPage!=1) {
                prevPageNumber = nextPage-1
            }

            LoadResult.Page(data = response,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )

        }

        catch (e: Exception) {
            Log.d("@@@", "load: "+e.toString())
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }



}
