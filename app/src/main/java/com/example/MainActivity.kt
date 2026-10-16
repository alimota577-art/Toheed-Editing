package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.ToheedDatabase
import com.example.data.repository.ToheedRepository
import com.example.ui.components.ToheedBottomNavigationBar
import com.example.ui.components.ToheedTopAppBar
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderTrackingScreen
import com.example.ui.screens.PlaceOrderScreen
import com.example.ui.screens.PlayStoreAndWebGuideScreen
import com.example.ui.screens.PosterStudioScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.theme.ToheedDeepNight
import com.example.ui.theme.ToheedEditsTheme
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.ToheedViewModel
import com.example.ui.viewmodel.ToheedViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = ToheedDatabase.getDatabase(this)
        val repository = ToheedRepository(
            orderDao = database.orderDao(),
            servicePriceDao = database.servicePriceDao()
        )
        val viewModelFactory = ToheedViewModelFactory(repository)

        setContent {
            ToheedEditsTheme {
                val viewModel: ToheedViewModel = viewModel(factory = viewModelFactory)
                ToheedEditsApp(viewModel)
            }
        }
    }
}

@Composable
fun ToheedEditsApp(viewModel: ToheedViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val language by viewModel.language.collectAsState()
    val context = LocalContext.current

    // Handle back button to return to home screen
    BackHandler(enabled = currentScreen != Screen.Home) {
        if (!viewModel.navigateBack()) {
            viewModel.navigateTo(Screen.Home)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToheedTopAppBar(
                language = language,
                onToggleLanguage = { viewModel.toggleLanguage() },
                onOpenWhatsApp = { viewModel.openWhatsApp(context) },
                onOpenAdmin = { viewModel.navigateTo(Screen.AdminDashboard) },
                onOpenGuide = { viewModel.navigateTo(Screen.PlayStoreGuide) }
            )
        },
        bottomBar = {
            ToheedBottomNavigationBar(
                currentScreen = currentScreen,
                language = language,
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        },
        containerColor = ToheedDeepNight
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ToheedDeepNight)
        ) {
            Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
                when (screen) {
                    Screen.Home -> HomeScreen(
                        viewModel = viewModel,
                        language = language,
                        onNavigate = { target -> viewModel.navigateTo(target) }
                    )
                    Screen.Services -> ServicesScreen(
                        viewModel = viewModel,
                        language = language,
                        onNavigate = { target -> viewModel.navigateTo(target) }
                    )
                    Screen.PosterEditor -> PosterStudioScreen(
                        viewModel = viewModel,
                        language = language,
                        onNavigate = { target -> viewModel.navigateTo(target) }
                    )
                    Screen.PlaceOrder -> PlaceOrderScreen(
                        viewModel = viewModel,
                        language = language,
                        onNavigate = { target -> viewModel.navigateTo(target) }
                    )
                    Screen.OrderTracking -> OrderTrackingScreen(
                        viewModel = viewModel,
                        language = language
                    )
                    Screen.AdminDashboard -> AdminDashboardScreen(
                        viewModel = viewModel,
                        language = language
                    )
                    Screen.PlayStoreGuide -> PlayStoreAndWebGuideScreen(
                        viewModel = viewModel,
                        language = language
                    )
                    else -> HomeScreen(
                        viewModel = viewModel,
                        language = language,
                        onNavigate = { target -> viewModel.navigateTo(target) }
                    )
                }
            }
        }
    }
}
