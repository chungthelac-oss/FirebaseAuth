package com.example.firebaseauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val repo = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // ← Thêm userRole
    private val _userRole = MutableStateFlow("")  // ← đổi "user" thành ""
    val userRole: StateFlow<String> = _userRole.asStateFlow()

    init {
        // Load role khi app khởi động nếu đã đăng nhập
        if (repo.getCurrentUser() != null) {
            loadUserRole()
        }
    }

    private fun loadUserRole() {
        viewModelScope.launch {
            _userRole.value = repo.getUserRole()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repo.login(email, password)
            if (result.isSuccess) {
                loadUserRole()  // Load role trước
                delay(500)      // ← chờ 500ms để load xong
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error(
                    result.exceptionOrNull()?.message ?: "Đăng nhập thất bại!"
                )
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repo.register(email, password)
            if (result.isSuccess) {
                _userRole.value = "user"  // ← Mới đăng ký luôn là user
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error(
                    result.exceptionOrNull()?.message ?: "Đăng ký thất bại!"
                )
            }
        }
    }

    fun logout() {
        repo.logout()
        _authState.value = AuthState.Idle
        _userRole.value = ""  // ← đổi "user" thành ""
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    fun isLoggedIn() = repo.getCurrentUser() != null
}