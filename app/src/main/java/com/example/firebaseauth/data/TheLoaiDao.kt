package com.example.firebaseauth.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.firebaseauth.entities.DanhMuc
import kotlinx.coroutines.flow.Flow

@Dao
interface TheLoaiDao {
    @Query("select * from danhmuc")
    fun getAll(): Flow<List<DanhMuc>>

    @Insert
    suspend fun insert(danhmuc: DanhMuc)

    @Update
    suspend fun update(danhmuc: DanhMuc)

    @Delete
    suspend fun delete(danhmuc: List<DanhMuc>)
}
