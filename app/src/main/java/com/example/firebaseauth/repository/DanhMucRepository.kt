package com.example.firebaseauth.repository

import com.example.firebaseauth.data.TheLoaiDao
import com.example.firebaseauth.entities.DanhMuc
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DanhMucRepository @Inject constructor(
    private val theLoaiDao: TheLoaiDao
) {
    fun getAllDanhMuc(): Flow<List<DanhMuc>> {
        return theLoaiDao.getAll()
    }

    suspend fun insertDanhMuc(danhMuc: DanhMuc) {
        theLoaiDao.insert(danhMuc)
    }

    suspend fun updateDanhMuc(danhMuc: DanhMuc) {
        theLoaiDao.update(danhMuc)
    }

    suspend fun deleteDanhMuc(danhMuc: List<DanhMuc>) {
        theLoaiDao.delete(danhMuc)
    }
}