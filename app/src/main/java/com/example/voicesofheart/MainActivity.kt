package com.example.voicesofheart
import com.example.voicesofheart.data.player.PlayerManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.voicesofheart.ui.components.BottomNavBar
import com.example.voicesofheart.ui.navigation.NavGraph
import com.example.voicesofheart.ui.theme.VoicesofheartTheme
import androidx.compose.foundation.layout.Column
import com.example.voicesofheart.ui.components.MiniPlayer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        PlayerManager.init(applicationContext)
        setContent {
            VoicesOfHeartApp()
        }
    }
}

@Composable
fun VoicesOfHeartApp() {
    VoicesofheartTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                Column {
                    MiniPlayer()
                    BottomNavBar(navController)
                }
                } ){ innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavGraph(navController = navController)
            }
        }
    }
}