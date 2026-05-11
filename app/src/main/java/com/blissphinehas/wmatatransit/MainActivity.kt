package com.blissphinehas.wmatatransit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.blissphinehas.wmatatransit.data.db.AppDatabase
import com.blissphinehas.wmatatransit.data.repository.TransitRepository
import com.blissphinehas.wmatatransit.di.ViewModelFactory
import com.blissphinehas.wmatatransit.ui.arrivals.ArrivalsScreen
import com.blissphinehas.wmatatransit.ui.arrivals.ArrivalsViewModel
import com.blissphinehas.wmatatransit.ui.favorites.FavoritesScreen
import com.blissphinehas.wmatatransit.ui.favorites.FavoritesViewModel
import com.blissphinehas.wmatatransit.ui.map.MapScreen
import com.blissphinehas.wmatatransit.ui.map.MapViewModel
import com.blissphinehas.wmatatransit.ui.theme.WMATATransitCompanionTheme

sealed class Screen(val route: String) {
    object Map : Screen("map")
    object Favorites : Screen("favorites")
    object Arrivals : Screen("arrivals/{stopId}/{stopName}") {
        fun createRoute(stopId: String, stopName: String) = "arrivals/$stopId/$stopName"
    }
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getInstance(this)
        val repository = TransitRepository(db)
        val factory = ViewModelFactory(repository)

        setContent {
            WMATATransitCompanionTheme {
                MainScreen(factory)
            }
        }
    }
}

@Composable
fun MainScreen(factory: ViewModelFactory) {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(Screen.Map, "Map", Icons.Default.Place),
        BottomNavItem(Screen.Favorites, "Favorites", Icons.Default.Favorite)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == item.screen.route
                        } == true,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        val mapViewModel: MapViewModel = viewModel(factory = factory)
        val favoritesViewModel: FavoritesViewModel = viewModel(factory = factory)
        val arrivalsViewModel: ArrivalsViewModel = viewModel(factory = factory)

        NavHost(
            navController = navController,
            startDestination = Screen.Map.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Map.route) {
                MapScreen(
                    viewModel = mapViewModel,
                    onStopClick = { stop ->
                        navController.navigate(
                            Screen.Arrivals.createRoute(stop.stopId, stop.name)
                        )
                    }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    viewModel = favoritesViewModel,
                    onStopClick = { stop ->
                        navController.navigate(
                            Screen.Arrivals.createRoute(stop.stopId, stop.name)
                        )
                    }
                )
            }
            composable(Screen.Arrivals.route) { backStackEntry ->
                val stopId = backStackEntry.arguments?.getString("stopId") ?: ""
                val stopName = backStackEntry.arguments?.getString("stopName") ?: ""
                ArrivalsScreen(
                    stopId = stopId,
                    stopName = stopName,
                    viewModel = arrivalsViewModel
                )
            }
        }
    }
}