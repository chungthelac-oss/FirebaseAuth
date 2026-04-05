package com.example.firebaseauth.gui
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.firebaseauth.R
import com.example.firebaseauth.entities.TinTuc
import com.example.firebaseauth.viewmodel.DanhMucViewModel
import com.example.firebaseauth.viewmodel.TinTucViewModel


@Composable
fun CreateNewsScreen(
    @Suppress("UNUSED_PARAMETER") navController: NavController,
    viewModel: TinTucViewModel = hiltViewModel(),
    viewModelCategory: DanhMucViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("") }

    val availableImages = listOf(
        R.drawable.s26, R.drawable.arsenal, R.drawable.barca,
        R.drawable.vn, R.drawable.ip, R.drawable.vn1,
        R.drawable.bmw, R.drawable.poli
    )

    var selectedImages by remember { mutableStateOf(listOf<Int>()) }

    val newsList by viewModel.tinTucs.collectAsState(initial = emptyList())
    val categories by viewModelCategory.danhMucs.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("TẠO TIN TỨC MỚI", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Red)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tiêu đề bài báo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Nội dung chi tiết") },
            modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp)
        )

        Text("Chọn hình ảnh cho bài viết:", fontWeight = FontWeight.Bold)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(availableImages) { imgRes ->
                val isSelected = selectedImages.contains(imgRes)
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Color.Red else Color.Transparent)
                        .padding(if (isSelected) 4.dp else 0.dp)
                        .clickable {
                            selectedImages = if (isSelected) {
                                selectedImages - imgRes
                            } else {
                                selectedImages + imgRes
                            }
                        }
                ) {
                    AsyncImage(
                        model = imgRes,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        if (selectedImages.isNotEmpty()) {
            Text("Hình ảnh đã chọn (${selectedImages.size}):", fontSize = 12.sp, color = Color.Gray)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                items(selectedImages) { img ->
                    AsyncImage(
                        model = img,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop   // ← thêm dòng này
                    )
                }
            }
        }
        DanhMucDropDownList(
            danhMucs = categories,
            selectedDanhMuc = categories.find { it.id == categoryId.toIntOrNull() },
            onDanhMucSelected = { categoryId = it.id.toString() }
        )

        Button(
            onClick = {
                val catIdInt = categoryId.toIntOrNull()
                if (title.isNotBlank() && catIdInt != null) {
                    viewModel.insertTinTuc(
                        TinTuc(
                            tieuDe = title,
                            noiDung = content,
                            hinhAnh = selectedImages.firstOrNull()?.toString() ?: "",
                            danhMucId = catIdInt
                        )
                    )
                    Toast.makeText(context, "Tạo bài viết thành công", Toast.LENGTH_SHORT).show()
                    title = ""; content = ""; categoryId = ""; selectedImages = emptyList()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text("ĐĂNG BÀI VIẾT", color = Color.White)
        }

        Text("Tin đã đăng:", fontWeight = FontWeight.Bold)

        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(newsList) { newsItem ->
                val categoryName = categories.find { it.id == newsItem.danhMucId }?.name ?: "Chưa phân loại"

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = newsItem.hinhAnh.toIntOrNull() ?: R.drawable.vn,  // ← thêm fallback
                            contentDescription = null,
                            modifier = Modifier.size(70.dp).clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = newsItem.tieuDe,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Chuyên mục: $categoryName",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }

                        IconButton(onClick = { viewModel.deleteTinTuc(newsItem) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Xóa",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}