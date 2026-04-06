package com.example.firebaseauth.gui
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauth.AuthViewModel
import com.example.firebaseauth.AuthState

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val authViewModel: AuthViewModel = hiltViewModel()
    val userRole by authViewModel.userRole.collectAsState()
    val isRoleLoaded by authViewModel.isRoleLoaded.collectAsState()

    val startDestination = if (authViewModel.isLoggedIn()) "news_home" else "login"

    Scaffold(
        bottomBar = {
            if (currentRoute !in listOf("login", "Register")) {
                BottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute,
                    userRole = userRole
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("login") { LoginScreen(navController = navController, authViewModel = authViewModel) }
            composable("Register") { CreateFormRegister(navController) }
            composable("news_home") { NewsMainScreen(navController) }
            composable("create_news") {
                when {
                    !isRoleLoaded -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    userRole == "admin" -> CreateNewsScreen(navController)
                    else -> {
                        LaunchedEffect(Unit) {
                            navController.navigate("news_home") {
                                popUpTo("create_news") { inclusive = true }
                            }
                        }
                    }
                }
            }
            composable("classroom") {
                when {
                    !isRoleLoaded -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    userRole == "admin" -> DanhMucScreen(navController = navController, authViewModel = authViewModel)
                    else -> {
                        LaunchedEffect(Unit) {
                            navController.navigate("news_home") {
                                popUpTo("classroom") { inclusive = true }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?,
    userRole: String
) {
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

        if (userRole == "admin") {
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
}