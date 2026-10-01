package com.example.voicesofheart.ui.library

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.voicesofheart.data.media.SongScanner
import com.example.voicesofheart.domain.Song
import androidx.compose.foundation.clickable
import com.example.voicesofheart.data.player.PlayerManager

@Composable
fun LibraryScreen() {
    val context = LocalContext.current
    var songs by remember { mutableStateOf<List<Song>>(emptyList()) }
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasPermission = granted }

    LaunchedEffect(hasPermission) {
        if (!hasPermission) {
            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            songs = SongScanner(context).scanSongs()
        }
    }

    if (!hasPermission) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Permission needed to read your music files")
        }
        return
    }

    if (songs.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No songs found on this device")
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(songs) { song ->
            ListItem(
                headlineContent = { Text(song.title, style = MaterialTheme.typography.titleMedium) },
                supportingContent = { Text(song.artist, style = MaterialTheme.typography.bodyMedium)},
                modifier = Modifier.clickable { PlayerManager.playSong(song, songs)}
            )
            HorizontalDivider()
        }
    }
}