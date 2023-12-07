package com.github.vinizaan.vehiclevaultsystem.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CarroDAO {
    @Insert
    suspend fun insert(carro: Carro)

    @Update
    suspend fun update (carro: Carro)

    @Delete
    suspend fun delete(carro: Carro)

    @Query("SELECT * FROM carro ORDER BY modelo")
    fun getAllCars(): LiveData<List<Carro>>

    @Query("SELECT * FROM carro WHERE id=:id")
    fun getCarById(id: Int): LiveData<Carro>


}