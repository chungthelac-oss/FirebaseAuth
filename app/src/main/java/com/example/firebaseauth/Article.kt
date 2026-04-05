package com.example.firebaseauth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(val id: Int,
                   val title: String,
                   val description: String,
                   val category: String,
                   val imageURL: Int) : Parcelable {
}