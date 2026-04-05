package com.example.firebaseauth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(val mapro:String, val tenpro:String, val year: Int, val hinh:Int) :
    Parcelable {

}