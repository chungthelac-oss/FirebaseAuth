package com.example.firebaseauth.gui
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauth.AuthViewModel
import com.example.firebaseauth.viewmodel.DanhMucViewModel
import com.example.firebaseauth.viewmodel.TinTucViewModel

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute !in listOf("login", "Register")) {
                BottomNavigationBar(navController, currentRoute)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("login") {
                LoginScreen(navController)
            }
            composable("Register") {
                CreateFormRegister(navController)  // hoặc RegisterScreen
            }
            composable("news_home") {
                NewsMainScreen(navController)      // ← trang chủ báo
            }
            composable("create_news") {
                CreateNewsScreen(navController)    // ← đăng bài
            }
            composable("classroom") {
                DanhMucScreen(navController)       // ← quản lý danh mục
            }

        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == "news_home",
            onClick = {
                if (currentRoute != "news_home") {
                    navController.navigate("news_home") {
                        popUpTo("news_home") { inclusive = true }
                    }
                }
            },
            label = { Text("Trang chủ") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") }
        )
        NavigationBarItem(
            selected = currentRoute == "create_news",
            onClick = {
                if (currentRoute != "create_news") {
                    navController.navigate("create_news")
                }
            },
            label = { Text("Tạo tin") },
            icon = { Icon(Icons.Filled.Add, contentDescription = "CreateNews") }
        )
        NavigationBarItem(
            selected = currentRoute == "classroom",
            onClick = {
                if (currentRoute != "classroom") {
                    navController.navigate("classroom")
                }
            },
            label = { Text("Danh mục") },
            icon = { Icon(Icons.Filled.Menu, contentDescription = "Menu") }
        )
    }
}