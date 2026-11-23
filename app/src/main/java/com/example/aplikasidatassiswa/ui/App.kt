package com.example.aplikasidatassiswa.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aplikasidatassiswa.data.AppPreferences
import com.example.aplikasidatassiswa.ui.screen.DaftarSiswaScreen
import com.example.aplikasidatassiswa.ui.screen.DashboardScreen
import com.example.aplikasidatassiswa.ui.screen.FormSiswaScreen
import com.example.aplikasidatassiswa.ui.screen.LoginScreen
import com.example.aplikasidatassiswa.ui.screen.ProfilScreen
import com.example.aplikasidatassiswa.ui.theme.AplikasiDataSiswaTheme

@Composable
fun AplikasiDataSiswaApp() {
    val context = LocalContext.current
    val prefs = remember { AppPreferences(context.applicationContext) }
    val darkTheme by prefs.darkTheme.collectAsStateWithLifecycle()
    val owner = context as ViewModelStoreOwner
    val vm: SiswaViewModel = viewModel(owner)
    val navController = rememberNavController()

    AplikasiDataSiswaTheme(darkTheme = darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = if (prefs.isLoggedIn.value) "dashboard" else "login"
            ) {
                composable("login") {
                    LoginScreen(
                        onLogin = { user ->
                            prefs.login(user)
                            navController.navigate("dashboard") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }

                composable("dashboard") {
                    DashboardScreen(vm = vm, navController = navController)
                }

                composable("daftar") {
                    DaftarSiswaScreen(vm = vm, navController = navController)
                }

                composable(
                    route = "form/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.LongType })
                ) { entry ->
                    val id = entry.arguments?.getLong("id") ?: 0L
                    FormSiswaScreen(
                        vm = vm,
                        siswaId = id,
                        onSaved = { navController.popBackStack() },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("profil") {
                    ProfilScreen(
                        prefs = prefs,
                        navController = navController,
                        onLogout = {
                            prefs.logout()
                            navController.navigate("login") {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
