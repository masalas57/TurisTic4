package com.example.fragmentmodule.data

class CartagenaRepository {

    suspend fun getCartagena() = ApiFactory.retrofit.getCartagena()

}