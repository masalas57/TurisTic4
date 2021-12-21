package com.example.fragmentmodule.data

import com.example.fragmentmodule.model.Cartagena
import retrofit2.http.GET

interface ApiService {

    @GET("masalas57/TurisTic2/cartagena")
    suspend fun getCartagena(): Cartagena

}