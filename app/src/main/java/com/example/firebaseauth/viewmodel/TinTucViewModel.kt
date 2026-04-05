package com.example.firebaseauth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauth.entities.TinTuc
import com.example.firebaseauth.repository.TinTucFirestoreRepository
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

    private val firestoreRepo = TinTucFirestoreRepository()

    // Lấy tin từ Firestore realtime
    val tinTucs: StateFlow<List<TinTuc>> =
        firestoreRepo.getAllTinTuc().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Thêm tin lên Firestore
    fun insertTinTuc(tinTuc: TinTuc) {
        viewModelScope.launch {
            firestoreRepo.insertTinTuc(tinTuc)
        }
    }

    // Xóa tin trên Firestore - cần docId
    fun deleteTinTuc(docId: String) {
        viewModelScope.launch {
            firestoreRepo.deleteTinTuc(docId)
        }
    }

    // Giữ lại các hàm Room cũ phòng cần
    fun insertTinTucLocal(tinTuc: TinTuc) {
        viewModelScope.launch {
            tinTucRepository.insertTinTuc(tinTuc)
        }
    }
}