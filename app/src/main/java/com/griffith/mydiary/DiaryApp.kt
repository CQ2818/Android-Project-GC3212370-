package com.griffith.mydiary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.griffith.mydiary.data.DiaryEntry
import com.griffith.mydiary.data.DiaryRepository
import com.griffith.mydiary.ui.screens.DetailScreen
import com.griffith.mydiary.ui.screens.EditScreen
import com.griffith.mydiary.ui.screens.HomeScreen
import com.griffith.mydiary.ui.screens.SettingsScreen
import com.griffith.mydiary.ui.theme.MyDiaryTheme

object Routes {
    const val HOME = "home"
    const val SETTINGS = "settings"
    const val EDIT = "edit?id={id}"
    const val DETAIL = "detail/{id}"
}

@Composable
fun DiaryApp() {
    val context = LocalContext.current
    val entries = remember {
        mutableStateListOf<DiaryEntry>().apply {
            addAll(DiaryRepository.load(context))
        }
    }

    var darkTheme by remember { mutableStateOf(false) }
    var showTime by remember { mutableStateOf(true) }

    val navController = rememberNavController()

    MyDiaryTheme(darkTheme = darkTheme) {
        NavHost(
            navController = navController,
            startDestination = Routes.HOME
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    entries = entries,
                    showTime = showTime,
                    onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                    onAdd = { navController.navigate("edit?id=-1") },
                    onOpenDetail = { id -> navController.navigate("detail/$id") }
                )
            }

            composable(
                route = Routes.EDIT,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: -1L
                val initialEntry = entries.firstOrNull { it.id == id }

                EditScreen(
                    initial = initialEntry,
                    onBack = { navController.popBackStack() },
                    onSave = { savedEntry ->
                        val index = entries.indexOfFirst { it.id == savedEntry.id }
                        if (index >= 0) {
                            entries[index] = savedEntry
                        } else {
                            entries.add(0, savedEntry)
                        }
                        DiaryRepository.save(context, entries)
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Routes.DETAIL,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: -1L
                val entry = entries.firstOrNull { it.id == id }

                DetailScreen(
                    entry = entry,
                    showTime = showTime,
                    onBack = { navController.popBackStack() },
                    onEdit = { navController.navigate("edit?id=$id") },
                    onDelete = {
                        entries.removeAll { it.id == id }
                        DiaryRepository.save(context, entries)
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.SETTINGS) {
                SettingsScreen(
                    darkTheme = darkTheme,
                    onDarkThemeChange = { darkTheme = it },
                    showTime = showTime,
                    onShowTimeChange = { showTime = it },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
