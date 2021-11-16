package com.clivewatts.pawpawscroll.ui.viewmodel

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.clivewatts.pawpawscroll.data.models.response.ResponseGoodBoys
import com.clivewatts.pawpawscroll.data.repository.RepositoryGoodBoys
import com.clivewatts.pawpawscroll.data.repository.RepositoryGoodBoysImpl
import com.clivewatts.pawpawscroll.ui.DogActionsInterface
import com.clivewatts.pawpawscroll.utils.DownloadUtils
import com.clivewatts.pawpawscroll.utils.DownloadUtils.Companion.downloadFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

val repo : RepositoryGoodBoys by inject(RepositoryGoodBoysImpl::class.java)
class DogListViewModel(private val context : Context) : ViewModel(), DogActionsInterface {

    private val _doggoList : MutableLiveData<MutableList<String?>?> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()
    val downloadImage = MutableLiveData<String>()
    val shareUrl = MutableLiveData<String>()
    val viewImage = MutableLiveData<String>()
    val doggoList: LiveData<MutableList<String?>?>
        get() = _doggoList

    private fun fetchDoggos( pageSize : Int) {

        MainScope().launch {
            loading.postValue(true)
            withContext(Dispatchers.IO) {
                val toLoad = repo.fetchGoodBoys(pageSize)
                if (!toLoad.message.isNullOrEmpty()) {
                    var currentList = _doggoList.value
                    if (currentList == null)
                        currentList = mutableListOf()
                    currentList?.addAll(toLoad.message)
                    currentList.let {
                        _doggoList.postValue(currentList)
                    }
                }
            }
            loading.postValue(false)
        }


    }

    fun loadMoreDoggos( pageSize : Int ) {
        fetchDoggos(pageSize)
    }

    fun fetchSomeDoggos() {
        fetchDoggos(50)
    }

    init {
        fetchSomeDoggos()
    }

    override fun onImageTapped(url : String) {
        viewImage.postValue(url)
    }

    override fun onSaveTapped(url : String) {
        downloadImage.postValue(url)
    }

    override fun onShareTapped(url : String) {
        shareUrl.postValue(url)
    }


}