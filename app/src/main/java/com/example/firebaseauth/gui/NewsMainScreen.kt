package com.example.firebaseauth.gui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.firebaseauth.R
import com.example.firebaseauth.Product
import com.example.firebaseauth.DetailActivity
import com.example.firebaseauth.entities.TinTuc
import com.example.firebaseauth.entities.DanhMuc
import com.example.firebaseauth.viewmodel.DanhMucViewModel
import com.example.firebaseauth.viewmodel.TinTucViewModel

@Composable
fun NewsMainScreen(
    navController: NavController,
    tinTucViewModel: TinTucViewModel = hiltViewModel(),
    danhMucViewModel: DanhMucViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    val tinTucs: List<TinTuc> by tinTucViewModel.tinTucs.collectAsState()
    val danhMucs: List<DanhMuc> by danhMucViewModel.danhMucs.collectAsState()
    val context = LocalContext.current

    // Hardcode list giữ nguyên
    val listPro = listOf(
        Product("Ban tin 1", "Viet Nam vô địch AFF Cup, Khẳng định vị thế Đông Nam Á", 2025, R.drawable.vn),
        Product("Ban tin 2", "Arsenal chạm tay vào chiếc cup Premier League sao bao năm chờ đợi", 2026, R.drawable.arsenal),
        Product("Ban tin 3", "Barcelona thắng đậm trên sân nhà sau đêm qua với tỷ số không tưởng cách biệt lên đến 5 bàn dẫn trước đối thủ", 2026, R.drawable.barca),
        Product("Ban tin 4", "Samsung cũng nhanh chóng Galaxy S26 Ultra với những tính năng mới và bản cập nhật đáng mong đợi nhất??", 2026, R.drawable.s26),
        Product("Ban tin 5", "Apple ra mắt iPhone 17 Pro Max nhanh chóng gây sức hút hay mờ nhạt??", 2025, R.drawable.ip),
        Product("Ban tin 6", "Dòng BMW 330i thể thao cá tính này đang đợi bạn đón nhận tại showroom của chúng tôi", 2025, R.drawable.bmw),
        Product("Ban tin 7", "Địa điểm thu hút khách nước ngoài đến du lịch Việt Nam với vẻ đẹp thiên nhiên", 2025, R.drawable.vn1),
        Product("Ban tin 8", "Trận chiến không hồi kết giữa Mỹ và Iran đang diễn ra rất sôi nổi và vẫn chưa biết ai thắng", 2025, R.drawable.poli),
    )

    // Lọc hardcode theo search
    val filteredPro = listPro.filter {
        it.tenpro.contains(searchText, ignoreCase = true)
    }

    // Lọc tin từ Room theo search
    val filteredTinTuc = tinTucs.filter {
        it.tieuDe.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFF5F5F5))
    ) {
        Text(
            text = "VNEXPRESS",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0XFF9F0606),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Tìm kiếm bài báo") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(12.dp)
        )

        Text(
            text = "Mới nhất",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Tin từ Room hiển thị trước
            items(filteredTinTuc) { tinTuc ->
                val danhMucName = danhMucs.find { it.id == tinTuc.danhMucId }?.name ?: ""
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        AsyncImage(
                            model = tinTuc.hinhAnh.toIntOrNull() ?: R.drawable.vn,
                            contentDescription = tinTuc.tieuDe,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = tinTuc.tieuDe,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Danh mục: $danhMucName",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth().height(35.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                        ) {
                            Text("Đọc thêm", fontSize = 12.sp, color = Color.White)
                        }
                    }
                }
            }

            // Tin hardcode hiển thị sau
            items(filteredPro) { pro ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra("keypro", pro)
                            context.startActivity(intent)
                        },
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        AsyncImage(
                            model = pro.hinh,
                            contentDescription = pro.tenpro,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = pro.tenpro,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Năm: ${pro.year}",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                val intent = Intent(context, DetailActivity::class.java)
                                intent.putExtra("keypro", pro)
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth().height(35.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                        ) {
                            Text("Đọc thêm", fontSize = 12.sp, color = Color.White)
                        }
                    }
                }
            }
        }

        // Total News hiển thị tổng cả 2 loại
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.padding(8.dp).fillMaxWidth().height(120.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Total News",
                        modifier = Modifier.size(48.dp)
                    )
                    Text("Total News: " + (listPro.size + tinTucs.size).toString())
                }
            }
        }
    }
}