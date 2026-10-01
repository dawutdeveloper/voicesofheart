package com.example.voicesofheart.data.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.voicesofheart.domain.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object PlayerManager {

    private var exoPlayer: ExoPlayer? = null

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private var playlist: List<Song> = emptyList()

    fun init(context: Context) {
        if (exoPlayer != null) return
        exoPlayer = ExoPlayer.Builder(context.applicationContext).build().apply {
            addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    _isPlaying.value = isPlaying
                }
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    val match = playlist.firstOrNull { it.uri.toString() == mediaItem?.mediaId }
                    if (match != null) _currentSong.value = match
                }
            })
        }
    }

    fun playSong(song: Song, songList: List<Song> = listOf(song)) {
        playlist = songList
        val startIndex = songList.indexOf(song).coerceAtLeast(0)
        val mediaItems = songList.map {
            MediaItem.Builder().setUri(it.uri).setMediaId(it.uri.toString()).build()
        }
        exoPlayer?.apply {
            setMediaItems(mediaItems, startIndex, 0L)
            prepare()
            play()
        }
        _currentSong.value = song
    }

    fun togglePlayPause() {
        exoPlayer?.let { if (it.isPlaying) it.pause() else it.play() }
    }

    fun skipNext() = exoPlayer?.seekToNext()
    fun skipPrevious() = exoPlayer?.seekToPrevious()

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }
}