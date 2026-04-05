package com.example.firebaseauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Lấy dữ liệu sản phẩm từ Intent
            val pro = intent.getParcelableExtra<Product>("keypro")
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Chi tiết tin tức", color = Color.White) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red),
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState()) // Cho phép cuộn nếu nội dung dài
                ) {
                    // 1. Hình ảnh lớn ở trên cùng (Full Width)
                    AsyncImage(
                        model = pro?.hinh,
                        contentDescription = pro?.tenpro,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        // 2. Mã tin tức (Nhỏ, mờ)
                        Text(
                            text = "Mã tin: ${pro?.mapro}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // 3. Tiêu đề tin tức (Lớn, đậm)
                        Text(
                            text = pro?.tenpro ?: "Không có tiêu đề",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 28.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // 4. Giá hoặc thông tin bổ sung
                        Text(
                            text = "Năm: ${pro?.year}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF1B5E20) // Màu xanh lá đậm
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                        // 5. Nội dung giả lập (Cho giống VNExpress)
                        Text(
                            text = "Nội dung chi tiết của bản tin sẽ được cập nhật tại đây. " +
                                    "Đây là phần mô tả chi tiết cho tin tức '${pro?.tenpro}'. " +
                                    "Ứng dụng đang hiển thị thông tin đầy đủ về sản phẩm/tin tức bạn đã chọn từ danh sách.",
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
        }
    }
}