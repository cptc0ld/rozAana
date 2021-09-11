package com.application.rozaana.repo

import android.content.res.Resources
import com.application.rozaana.model.PexelData
import com.application.rozaana.service.HomeService
import com.application.rozaana.util.network.BaseRepo
import com.application.rozaana.util.network.RetrofitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class HomeRepository(val resources: Resources) : BaseRepo() {
    var retrofit = RetrofitInstance.getBasicRetroFitInstance("https://api.pexels.com")
    private val service: HomeService = retrofit.create(HomeService::class.java)

    suspend fun getFlavorData(pageNumber: Int): PexelData {
        return withContext(ioDispatcher) {

            val response = async {
                service.getMedia(getHeaderMap(),"8xntbhr", pageNumber, 3)
            }
            return@withContext response.await()
        }
    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "563492ad6f9170000100000114b6977ec8504ff3ad79e38fcd1d6de8"
        return headerMap
    }
}
