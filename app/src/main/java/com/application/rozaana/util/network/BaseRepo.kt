package com.application.rozaana.util.network

import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

open class BaseRepo {
    protected val ioDispatcher = Dispatchers.IO

}
