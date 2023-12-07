package com.github.vinizaan.vehiclevaultsystem.repository

import androidx.lifecycle.LiveData
import com.github.vinizaan.vehiclevaultsystem.data.Carro
import com.github.vinizaan.vehiclevaultsystem.data.CarroDAO

class CarroRepository (private val carroDAO: CarroDAO) {

    suspend fun insert(carro: Carro){
        carroDAO.insert(carro)
    }

    suspend fun update(carro: Carro){
        carroDAO.update(carro)
    }

    suspend fun delete(carro: Carro){
        carroDAO.delete(carro)
    }

    fun getAllCars(): LiveData<List<Carro>> {
        return carroDAO.getAllCars()
    }

    fun getCarById(id: Int): LiveData<Carro>{
        return carroDAO.getCarById(id)
    }

}