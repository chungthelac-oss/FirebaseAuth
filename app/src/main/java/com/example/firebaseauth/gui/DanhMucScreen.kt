package com.example.firebaseauth.gui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firebaseauth.AuthViewModel
import com.example.firebaseauth.viewmodel.DanhMucViewModel

@Composable
fun DanhMucScreen(
    navController: NavController,
    authViewModel: AuthViewModel, // BẮT BUỘC THÊM BIẾN NÀY ĐỂ XÀI CHUNG VỚI APP NAVIGATION
    viewModel: DanhMucViewModel = hiltViewModel()
) {
    val danhMucs by viewModel.danhMucs.collectAsState()
    var tenDanhMuc by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "QUẢN LÝ DANH MỤC",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = tenDanhMuc,
            onValueChange = { tenDanhMuc = it },
            label = { Text("Tên danh mục") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    if (tenDanhMuc.isNotBlank()) {
                        viewModel.insertDanhMuc(tenDanhMuc)
                        tenDanhMuc = "" // Reset ô nhập sau khi thêm
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Thêm")
            }
            Button(
                onClick = { viewModel.deleteDanhMuc(danhMucs) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Xóa tất cả")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(danhMucs) { danhMuc ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(danhMuc.name, modifier = Modifier.padding(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // 1. GỌI HÀM NÀY ĐỂ XÓA QUYỀN VÀ TRẠNG THÁI CỦA ADMIN CŨ
                authViewModel.logout()

                // 2. Chuyển về login
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("ĐĂNG XUẤT", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}