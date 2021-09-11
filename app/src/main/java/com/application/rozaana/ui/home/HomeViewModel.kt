package com.application.rozaana.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.rozaana.model.PexelData
import com.application.rozaana.repo.HomeRepository
import com.application.rozaana.util.network.RequestResult
import kotlinx.coroutines.launch

class HomeViewModel(private val flavorRepository: HomeRepository) : ViewModel() {

    var pageData = MutableLiveData<RequestResult<Any>>()
    fun getLandingScreenData(pageNumber: Int) {
        viewModelScope.launch {
            pageData.value = RequestResult.Loading("Loading...")
            pageData.value = RequestResult.Success(flavorRepository.getFlavorData(pageNumber))
        }
    }

}
