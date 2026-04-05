package com.example.firebaseauth.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tintuc",
    foreignKeys = [ForeignKey(
        entity = DanhMuc::class,
        parentColumns = ["id"],
        childColumns = ["danhMucId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TinTuc(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tieuDe: String = "",
    val noiDung: String = "",
    val hinhAnh: String = "",
    val danhMucId: Int = 0
)