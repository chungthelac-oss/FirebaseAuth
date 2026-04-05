package com.example.firebaseauth.gui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firebaseauth.entities.TinTuc

@Composable
fun NewsItem(
    tinTuc: TinTuc,
    onclick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onclick() }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = tinTuc.tieuDe)
            Text(text = tinTuc.noiDung)
            Text(text = tinTuc.danhMucId.toString())
        }
    }
}