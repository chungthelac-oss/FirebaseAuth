package com.example.firebaseauth.repository

import com.example.firebaseauth.data.TinTucDao
import com.example.firebaseauth.entities.TinTuc
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TinTucRepository @Inject constructor(
    private val tinTucDao: TinTucDao
) {
    fun getAllTinTuc(): Flow<List<TinTuc>> {
        return tinTucDao.getAll()
    }

    suspend fun insertTinTuc(tinTuc: TinTuc) {
        tinTucDao.insert(tinTuc)
    }

    suspend fun updateTinTuc(tinTuc: TinTuc) {
        tinTucDao.update(tinTuc)
    }

    suspend fun deleteTinTuc(tinTuc: TinTuc) {
        tinTucDao.delete(tinTuc)
    }
}