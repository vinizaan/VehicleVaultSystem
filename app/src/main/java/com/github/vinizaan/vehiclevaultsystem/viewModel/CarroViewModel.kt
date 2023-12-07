package com.github.vinizaan.vehiclevaultsystem.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.vinizaan.vehiclevaultsystem.data.Carro
import com.github.vinizaan.vehiclevaultsystem.data.CarroDatabase
import com.github.vinizaan.vehiclevaultsystem.repository.CarroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarroViewModel(application: Application): AndroidViewModel(application) {
    private val repository: CarroRepository
    var allCars : LiveData<List<Carro>>
    lateinit var carro : LiveData<Carro>

    init {
        val dao = CarroDatabase.getDatabase(application).carroDAO()
        repository = CarroRepository(dao)
        allCars = repository.getAllCars()
    }

    fun insert(carro: Carro) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(carro)
    }

    fun update(carro: Carro) = viewModelScope.launch(Dispatchers.IO){
        repository.update(carro)
    }

    fun delete(carro: Carro) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(carro)
    }


    fun getCarById(id: Int) {
        viewModelScope.launch {
            carro = repository.getCarById(id)
        }


    }


}