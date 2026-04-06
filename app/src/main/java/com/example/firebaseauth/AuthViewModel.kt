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

    private val _userRole = MutableStateFlow("")
    val userRole: StateFlow<String> = _userRole.asStateFlow()

    private val _isRoleLoaded = MutableStateFlow(false)
    val isRoleLoaded: StateFlow<Boolean> = _isRoleLoaded.asStateFlow()

    init {
        if (repo.getCurrentUser() != null) {
            // Đã đăng nhập → load role ngay
            viewModelScope.launch {
                _userRole.value = repo.getUserRole()
                _isRoleLoaded.value = true
            }
        } else {
            // Chưa đăng nhập → không cần load role
            _isRoleLoaded.value = true
        }
    }

    private fun loadUserRole() {
        viewModelScope.launch {
            _userRole.value = repo.getUserRole()
            _isRoleLoaded.value = true
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            _isRoleLoaded.value = false  // ← reset ngay khi bắt đầu login
            _userRole.value = ""         // ← xóa role cũ
            val result = repo.login(email, password)
            if (result.isSuccess) {
                _userRole.value = repo.getUserRole()  // load role mới
                _isRoleLoaded.value = true
                _authState.value = AuthState.Success
            } else {
                _isRoleLoaded.value = true  // ← set true kể cả khi lỗi
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
                _userRole.value = "user"
                _isRoleLoaded.value = true
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
        _userRole.value = ""
        _isRoleLoaded.value = false
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    fun isLoggedIn() = repo.getCurrentUser() != null
}