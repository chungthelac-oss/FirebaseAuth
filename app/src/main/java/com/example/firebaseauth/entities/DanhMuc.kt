package com.example.firebaseauth.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "danhmuc")
data class DanhMuc(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val name:String
) {
}