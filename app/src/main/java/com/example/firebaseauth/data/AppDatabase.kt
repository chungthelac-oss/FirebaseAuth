package com.example.firebaseauth.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.firebaseauth.entities.DanhMuc
import com.example.firebaseauth.entities.TinTuc

@Database(
    entities = [
        TinTuc::class,
        DanhMuc::class
    ],
    version = 2
)
abstract class AppDatabase: RoomDatabase() {
    abstract val tintucDao: TinTucDao
    abstract val theloaiDao: TheLoaiDao
}