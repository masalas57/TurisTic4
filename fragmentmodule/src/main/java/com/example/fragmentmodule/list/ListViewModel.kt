package com.example.fragmentmodule.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fragmentmodule.data.CartagenaRepository
import com.example.fragmentmodule.model.Cartagena
import com.example.fragmentmodule.model.CartagenaItem
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream

class ListViewModel : ViewModel() {

    private var cartagenaLoad : MutableLiveData<ArrayList<CartagenaItem>> = MutableLiveData()
    val onCartagenaLoaded: LiveData<ArrayList<CartagenaItem>> = cartagenaLoad

    private val repository = CartagenaRepository()

    fun getCartagenaFromServer(){
        GlobalScope.launch(Dispatchers.IO) {
            cartagenaLoad.postValue(repository.getCartagena())
        }
    }


    fun loadMockCartagenaFromJson(cartagenaString: InputStream?) {
        val cartagenastring = cartagenaString?.bufferedReader().use { it!!.readText() }
        val gson = Gson()
        cartagenaLoad.value = gson.fromJson(cartagenastring, Cartagena::class.java)
    }
}