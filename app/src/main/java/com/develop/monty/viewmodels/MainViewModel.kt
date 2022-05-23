package com.develop.monty.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.develop.monty.Network.RetroInstance
import com.develop.monty.Network.RetroService
import com.develop.monty.Network.SearchService
import com.develop.monty.Paging.CharacterPagingSource
import com.develop.monty.Paging.SearchPagingSource
import kotlinx.coroutines.flow.Flow

import com.develop.monty.models.Repo

class MainViewModel: ViewModel() {
    var lst = MutableLiveData<ArrayList<Repo>>()
    var newlist = arrayListOf<Repo>()
    var retroService: RetroService
    var searchService: SearchService
    init {
        retroService = RetroInstance.getRetroInstance().create(RetroService::class.java)
        searchService = RetroInstance.getRetroInstance().create(SearchService::class.java)
    }
    fun add(blog: Repo){
        newlist.add(blog)
        lst.value=newlist
    }
    fun getListData(): Flow<PagingData<Repo>> {
        return Pager (config = PagingConfig(pageSize = 1, maxSize = 100),
            pagingSourceFactory = { CharacterPagingSource(retroService) }).flow.cachedIn(viewModelScope)
    }
    fun getSearchData(que: String): Flow<PagingData<Repo>> {
        return Pager (config = PagingConfig(pageSize = 1, maxSize = 100),
            pagingSourceFactory = { SearchPagingSource(searchService,que)}).flow.cachedIn(viewModelScope)
    }
    fun remove_all(){
        lst.value = null
    }

}