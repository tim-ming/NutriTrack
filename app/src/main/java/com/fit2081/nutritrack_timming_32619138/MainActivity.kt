package com.fit2081.nutritrack_timming_32619138

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.fit2081.nutritrack_timming_32619138.commonUI.toast.ToastMessage
import com.fit2081.nutritrack_timming_32619138.data.AppDatabase
import com.fit2081.nutritrack_timming_32619138.nav.Navigator
import com.fit2081.nutritrack_timming_32619138.screens.Screens
import com.fit2081.nutritrack_timming_32619138.screens.clinician.dashboard.ClinicianDashboardScreen
import com.fit2081.nutritrack_timming_32619138.screens.clinician.dashboard.ClinicianDashboardViewModel
import com.fit2081.nutritrack_timming_32619138.screens.clinician.login.ClinicianLoginScreen
import com.fit2081.nutritrack_timming_32619138.screens.clinician.login.ClinicianLoginViewModel
import com.fit2081.nutritrack_timming_32619138.screens.error.ErrorScreen
import com.fit2081.nutritrack_timming_32619138.screens.error.ErrorViewModel
import com.fit2081.nutritrack_timming_32619138.screens.forgotPassword.ForgotPasswordResetScreen
import com.fit2081.nutritrack_timming_32619138.screens.forgotPassword.ForgotPasswordVerifyScreen
import com.fit2081.nutritrack_timming_32619138.screens.forgotPassword.ForgotPasswordViewModel
import com.fit2081.nutritrack_timming_32619138.screens.home.HomeScreen
import com.fit2081.nutritrack_timming_32619138.screens.home.HomeViewModel
import com.fit2081.nutritrack_timming_32619138.screens.insights.InsightsScreen
import com.fit2081.nutritrack_timming_32619138.screens.insights.InsightsViewModel
import com.fit2081.nutritrack_timming_32619138.screens.login.LoginScreen
import com.fit2081.nutritrack_timming_32619138.screens.login.LoginViewModel
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.NutriCoachScreen
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.fruit.FruitViewModel
import com.fit2081.nutritrack_timming_32619138.screens.nutriCoach.tip.TipViewModel
import com.fit2081.nutritrack_timming_32619138.screens.questionnaire.QuestionnaireScreen
import com.fit2081.nutritrack_timming_32619138.screens.questionnaire.QuestionnaireViewModel
import com.fit2081.nutritrack_timming_32619138.screens.register.RegisterNameScreen
import com.fit2081.nutritrack_timming_32619138.screens.register.RegisterPasswordScreen
import com.fit2081.nutritrack_timming_32619138.screens.register.RegisterVerifyScreen
import com.fit2081.nutritrack_timming_32619138.screens.register.RegisterViewModel
import com.fit2081.nutritrack_timming_32619138.screens.settings.SettingsScreen
import com.fit2081.nutritrack_timming_32619138.screens.settings.SettingsViewModel
import com.fit2081.nutritrack_timming_32619138.screens.welcome.WelcomeScreen
import com.fit2081.nutritrack_timming_32619138.screens.welcome.WelcomeViewModel
import com.fit2081.nutritrack_timming_32619138.ui.theme.NutriTrackTheme
import com.fit2081.nutritrack_timming_32619138.util.SessionManager
import setStatusBarTextDark

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            AppDatabase.initialize(
                context = this,
                csvFilePath = "user_data.csv"
            )
        } catch (e: Exception) {
            Log.e("AppDatabase", "Initialization failed", e)
        }
        setStatusBarTextDark(this.window, true)
        setContent {
            val navController = rememberNavController()
            val navigator = remember { Navigator(navController) }

            NutriTrackTheme {
                LockOrientationAppWrapper {
                    MainActivityScaffold(navController, navigator, this)
                }
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun LockOrientationAppWrapper(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val activity = remember(context) { context as Activity }

    val windowSizeClass = calculateWindowSizeClass(activity)

    val orientation = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    } else {
        ActivityInfo.SCREEN_ORIENTATION_FULL_USER
    }

    DisposableEffect(orientation) {
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            activity.requestedOrientation = originalOrientation
        }
    }

    content()
}

@Composable
fun MainActivityScaffold(
    navController: NavHostController,
    navigator: Navigator,
    context: Context,
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            route = Screens.Home.route,
            label = "Home",
            filledIcon = Icons.Filled.Home,
            outlinedIcon = Icons.Outlined.Home
        ),
        BottomNavItem(
            route = Screens.Insights.route,
            label = "Insights",
            filledIcon = Icons.Filled.BarChart,
            outlinedIcon = Icons.Outlined.BarChart
        ),
        BottomNavItem(
            route = Screens.NutriCoach.route,
            label = "NutriCoach",
            filledIcon = Icons.Filled.Psychology,
            outlinedIcon = Icons.Outlined.Psychology
        ),
        BottomNavItem(
            route = Screens.Settings.route,
            label = "Settings",
            filledIcon = Icons.Filled.Settings,
            outlinedIcon = Icons.Outlined.Settings
        )
    )

    // track current navigation state
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomNav = currentDestination?.route in listOf(
        Screens.Home.route,
        Screens.Insights.route,
        Screens.NutriCoach.route,
        Screens.Settings.route
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                if (showBottomNav) {
                    AppNavigationBar(bottomNavItems, navController)
                }
            }
        ) { innerPadding ->
            AppNavigation(navController, innerPadding, navigator)
        }
        ToastMessage()
    }
}

@Composable
fun AppNavigationBar(
    bottomNavItems: List<BottomNavItem>,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.route)
                            item.filledIcon
                        else
                            item.outlinedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    // Only navigate if we're not already on this route
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    navigator: Navigator,
) {
    val context = LocalContext.current
    val startDestination = remember {
        val uid = SessionManager(context).getUid()
        if (uid != null) Screens.Home.route else Screens.Welcome.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Welcome.route) {
            val welcomeViewModel: WelcomeViewModel = viewModel()
            WelcomeScreen(
                innerPadding,
                navigator,
                viewModel = welcomeViewModel
            )
        }

        composable(Screens.Login.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                innerPadding,
                navigator,
                loginViewModel
            )
        }


        navigation(
            startDestination = Screens.ForgotPassword.VERIFY.route,
            route = Screens.ForgotPassword.BASE_ROUTE.route
        ) {
            composable(
                Screens.ForgotPassword.VERIFY.route
            ) { entry ->
                val viewModel = entry.sharedViewModel<ForgotPasswordViewModel>(navController)
                ForgotPasswordVerifyScreen(innerPadding, navigator, viewModel)
            }

            composable(Screens.ForgotPassword.RESET.route) { entry ->
                val viewModel = entry.sharedViewModel<ForgotPasswordViewModel>(navController)
                ForgotPasswordResetScreen(innerPadding, navigator, viewModel)
            }
        }

        navigation(
            startDestination = Screens.Register.VERIFY.route,
            route = Screens.Register.BASE_ROUTE.route
        ) {
            composable(Screens.Register.VERIFY.route) { entry ->
                val viewModel = entry.sharedViewModel<RegisterViewModel>(navController)
                RegisterVerifyScreen(innerPadding, navigator, viewModel)
            }

            composable(Screens.Register.NAME.route) { entry ->
                val viewModel = entry.sharedViewModel<RegisterViewModel>(navController)
                RegisterNameScreen(innerPadding, navigator, viewModel)
            }

            composable(Screens.Register.PASSWORD.route) { entry ->
                val viewModel = entry.sharedViewModel<RegisterViewModel>(navController)
                RegisterPasswordScreen(innerPadding, navigator, viewModel)
            }
        }

        composable(Screens.Home.route) {
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(
                innerPadding,
                navigator,
                viewModel = homeViewModel
            )
        }

        composable(
            Screens.Questionnaire.route
        ) {
            val questionnaireViewModel: QuestionnaireViewModel = viewModel()
            QuestionnaireScreen(
                innerPadding,
                navigator,
                questionnaireViewModel
            )
        }

        composable(Screens.Insights.route) {
            val insightsViewModel: InsightsViewModel = viewModel()
            InsightsScreen(
                innerPadding,
                navigator,
                insightsViewModel
            )
        }

        composable(Screens.NutriCoach.route) {
            val tipViewModel: TipViewModel = viewModel()
            val fruitViewModel: FruitViewModel = viewModel()
            NutriCoachScreen(
                innerPadding,
                navigator,
                tipViewModel = tipViewModel,
                fruitViewModel = fruitViewModel
            )
        }

        composable(Screens.Settings.route) {
            val settingsViewModel: SettingsViewModel = viewModel()
            SettingsScreen(innerPadding, navigator, settingsViewModel)
        }

        composable(Screens.Error.BASE_ROUTE) { backStackEntry ->
            val errorViewModel: ErrorViewModel = viewModel()
            val message = backStackEntry.arguments?.getString(Screens.Error.PARAMETER)
                ?: "Not sure what went wrong"
            ErrorScreen(
                navigator = navigator,
                viewModel = errorViewModel,
                message = message
            )
        }

        composable(Screens.Clinician.LOGIN.route) {
            val clinicianLoginViewModel: ClinicianLoginViewModel = viewModel()
            ClinicianLoginScreen(innerPadding, navigator, clinicianLoginViewModel)
        }

        composable(Screens.Clinician.DASHBOARD.route) {
            val clinicianDashboardViewModel: ClinicianDashboardViewModel = viewModel()
            ClinicianDashboardScreen(innerPadding, navigator, clinicianDashboardViewModel)
        }
    }
}

/**
 * Source: https://github.com/philipplackner/SharingDataBetweenScreens/blob/master/app/src/main/java/com/plcoding/sharingdataprep/content/2-SharedViewModel.kt
 */
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}