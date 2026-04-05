package com.example.firebaseauth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauth.entities.DanhMuc
import com.example.firebaseauth.repository.DanhMucRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DanhMucViewModel @Inject constructor(
    private val danhMucRepository: DanhMucRepository
) : ViewModel() {

    val danhMucs: StateFlow<List<DanhMuc>> =
        danhMucRepository.getAllDanhMuc().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun insertDanhMuc(name: String) {
        viewModelScope.launch {
            danhMucRepository.insertDanhMuc(DanhMuc(name = name))
        }
    }

    fun updateDanhMuc(danhMuc: DanhMuc) {
        viewModelScope.launch {
            danhMucRepository.updateDanhMuc(danhMuc)
        }
    }

    fun deleteDanhMuc(danhMucList: List<DanhMuc>) {
        viewModelScope.launch {
            danhMucRepository.deleteDanhMuc(danhMucList)
        }
    }
}