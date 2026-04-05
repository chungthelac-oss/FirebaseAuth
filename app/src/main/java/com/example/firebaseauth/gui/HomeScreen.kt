package com.example.firebaseauth.gui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firebaseauth.AuthViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Home Page")
        Button(onClick =  {
            viewModel.logout()
            navController.navigate("login"){
                popUpTo("news_home"){
                    inclusive=true
                }
            }
        }) {
            Text("logout")
        }
    }
}