package com.example.firebaseauth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauth.entities.TinTuc
import com.example.firebaseauth.repository.TinTucRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TinTucViewModel @Inject constructor(
    private val tinTucRepository: TinTucRepository
) : ViewModel() {

    val tinTucs: StateFlow<List<TinTuc>> =
        tinTucRepository.getAllTinTuc().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun insertTinTuc(tinTuc: TinTuc) {
        viewModelScope.launch {
            tinTucRepository.insertTinTuc(tinTuc)
        }
    }

    fun updateTinTuc(tinTuc: TinTuc) {
        viewModelScope.launch {
            tinTucRepository.updateTinTuc(tinTuc)
        }
    }

    fun deleteTinTuc(tinTuc: TinTuc) {
        viewModelScope.launch {
            tinTucRepository.deleteTinTuc(tinTuc)
        }
    }
}