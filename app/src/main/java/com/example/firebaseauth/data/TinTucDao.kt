package com.example.firebaseauth.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.firebaseauth.entities.TinTuc
import kotlinx.coroutines.flow.Flow

@Dao
interface TinTucDao {
    @Query("select * from tintuc")
    fun getAll(): Flow<List<TinTuc>>
    @Insert
    suspend fun insert(tintuc: TinTuc)
    @Update
    suspend fun update(tintuc: TinTuc)
    @Delete
    suspend fun delete(tintuc: TinTuc)
}