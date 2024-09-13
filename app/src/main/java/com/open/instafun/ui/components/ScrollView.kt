package com.open.instafun.ui.components
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.open.instafun.R

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalPagerSample() {
    val pagerState = rememberPagerState(pageCount = {10})

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        MediaPlayerSample("page")
    }
}

@Composable
fun MediaPlayerSample(url: String) {
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }
//https://instagram.fpku4-1.fna.fbcdn.net/o1/v/t16/f2/m69/An9gLGWxc4vSeNgzj3KyaLM5mAdoRXROVBhz0YZ8pGNByN__1_LIS8-hEHne0GlUFzCCdIWPHK0KPzCWHjFIenLO.mp4?stp=dst-mp4&efg=eyJxZV9ncm91cHMiOiJbXCJpZ193ZWJfZGVsaXZlcnlfdnRzX290ZlwiXSIsInZlbmNvZGVfdGFnIjoidnRzX3ZvZF91cmxnZW4uY2xpcHMuYzIuMTA3OC5iYXNlbGluZSJ9&_nc_cat=103&vs=7995748780493830_3995073680&_nc_vs=HBksFQIYOnBhc3N0aHJvdWdoX2V2ZXJzdG9yZS9HRWozTlJNQ3BqZEhHU29NQU4yUnhHblQyVjExYnBSMUFBQUYVAALIAQAVAhg6cGFzc3Rocm91Z2hfZXZlcnN0b3JlL0dBTXVLUnNzRTlkVHhFSUJBRnlla3l4TlBtWVpicV9FQUFBRhUCAsgBACgAGAAbABUAACaUu4uyr4CLQBUCKAJDMywXQEmu2RaHKwIYFmRhc2hfYmFzZWxpbmVfMTA4MHBfdjERAHX%2BBwA%3D&ccb=9-4&oh=00_AYAiQ8QN5S_gH4_4CF5rA8zlMHmd_YMhZnPT_0n-jCQ0kA&oe=66E4234B&_nc_sid=d885a2&dl=1
    val mediaItem = MediaItem.fromUri(url)

//    val mediaItem = MediaItem.fromUri("https://instagram.fpku4-1.fna.fbcdn.net/o1/v/t16/f2/m69/An9gLGWxc4vSeNgzj3KyaLM5mAdoRXROVBhz0YZ8pGNByN__1_LIS8-hEHne0GlUFzCCdIWPHK0KPzCWHjFIenLO.mp4?stp=dst-mp4&efg=eyJxZV9ncm91cHMiOiJbXCJpZ193ZWJfZGVsaXZlcnlfdnRzX290ZlwiXSIsInZlbmNvZGVfdGFnIjoidnRzX3ZvZF91cmxnZW4uY2xpcHMuYzIuMTA3OC5iYXNlbGluZSJ9&_nc_cat=103&vs=7995748780493830_3995073680&_nc_vs=HBksFQIYOnBhc3N0aHJvdWdoX2V2ZXJzdG9yZS9HRWozTlJNQ3BqZEhHU29NQU4yUnhHblQyVjExYnBSMUFBQUYVAALIAQAVAhg6cGFzc3Rocm91Z2hfZXZlcnN0b3JlL0dBTXVLUnNzRTlkVHhFSUJBRnlla3l4TlBtWVpicV9FQUFBRhUCAsgBACgAGAAbABUAACaUu4uyr4CLQBUCKAJDMywXQEmu2RaHKwIYFmRhc2hfYmFzZWxpbmVfMTA4MHBfdjERAHX%2BBwA%3D&ccb=9-4&oh=00_AYAiQ8QN5S_gH4_4CF5rA8zlMHmd_YMhZnPT_0n-jCQ0kA&oe=66E4234B&_nc_sid=d885a2&dl=1")
    player.setMediaItem(mediaItem)
    player.prepare()
    player.playWhenReady = true

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PlayerView(context).apply {
                this.player = player
            }
        }
    )
}


@Composable
fun MediaPlayerSample2(url: String, isPlaying: Boolean) {
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    val mediaItem = MediaItem.fromUri(url)
    player.setMediaItem(mediaItem)
    // Mute the player
    player.volume = 0f

    // Loop the video
    player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
    // Only play if the page is visible
    if (isPlaying) {
        player.prepare()
        player.playWhenReady = true
    } else {
        player.pause()
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PlayerView(context).apply {
                this.player = player
            }
        }
    )
}




@Composable
fun CustomMediaPlayer(url: String, isPlaying: Boolean) {
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    val mediaItem = MediaItem.fromUri(url)
    player.setMediaItem(mediaItem)
    player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
    player.volume = 0f // Mute the player

    var isControlVisible by remember { mutableStateOf(false) }
    var playbackPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }

    // To automatically hide controls after a delay
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            player.prepare()
            player.playWhenReady = true
        } else {
            player.pause()
        }

        // Update duration and playback position periodically
        while (isPlaying) {
            duration = player.duration
            playbackPosition = player.currentPosition
            delay(1000) // Update every second
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    isControlVisible = !isControlVisible
                    coroutineScope.launch {
                        delay(3000)
                        isControlVisible = false
                    }
                })
            }
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    this.player = player
                    this.useController = false // Disable default controls
                }
            }
        )

        // Custom Play/Pause Button
        if (isControlVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {
                        if (player.isPlaying) {
                            player.pause()
                        } else {
                            player.play()
                        }
                    }
            ) {

                Image(
                    painter = if (player.isPlaying) {
                        painterResource(id = R.drawable.ic_player_pause) // Replace with your pause image
                    } else {
                        painterResource(id = R.drawable.ic_player_play) // Replace with your play image
                    },
                    contentDescription = if (player.isPlaying) "Pause" else "Play",
                    modifier = Modifier.size(64.dp)
                )

            }

            // Custom SeekBar
            Slider(
                value = playbackPosition.toFloat(),
                onValueChange = {
                    player.seekTo(it.toLong())
                    playbackPosition = it.toLong()
                },
                valueRange = 0f..duration.toFloat(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun CustomMediaPlayerWithImages(url: String, isPlaying: Boolean) {
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }
    player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
    player.volume = 0f // Mute the player

    val mediaItem = MediaItem.fromUri(url)
    var hasInitializedPlayer by remember { mutableStateOf(false) }
    var isControlVisible by remember { mutableStateOf(false) }
    var playbackPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }
    var isUserSeeking by remember { mutableStateOf(false) } // To track if user is seeking manually

    // To automatically hide controls after a delay
    val coroutineScope = rememberCoroutineScope()

    // Initialize the player and set the media item only once
    LaunchedEffect(player, url) {
        if (!hasInitializedPlayer) {
            player.setMediaItem(mediaItem)
            player.prepare()
            hasInitializedPlayer = true
        }
    }

    // Control the play/pause state
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            player.playWhenReady = true
        } else {
            player.pause()
        }

        // Update duration and playback position periodically when playing
        while (isPlaying) {
            if (!isUserSeeking) {
                duration = player.duration.takeIf { it > 0 } ?: 0L
                playbackPosition = player.currentPosition
            }
            delay(1000) // Update every second
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    isControlVisible = !isControlVisible
                    coroutineScope.launch {
                        delay(3000)
                        isControlVisible = false
                    }
                })
            }
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    this.player = player
                    this.useController = false // Disable default controls
                }
            }
        )

        // Custom Play/Pause Image Button
        if (isControlVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {
                        if (player.isPlaying) {
                            player.pause()
                        } else {
                            player.play()
                        }
                    }
            ) {
                Image(
                    painter = if (player.isPlaying) {
                        painterResource(id = R.drawable.ic_player_pause) // Replace with your pause image
                    } else {
                        painterResource(id = R.drawable.ic_player_play) // Replace with your play image
                    },
                    contentDescription = if (player.isPlaying) "Pause" else "Play",
                    modifier = Modifier.size(64.dp)
                )
            }

            // Custom SeekBar
            if (duration > 0) {
                Slider(
                    value = playbackPosition.toFloat(),
                    onValueChange = {
                        isUserSeeking = true // User is interacting with the slider
                        playbackPosition = it.toLong()
                    },
                    onValueChangeFinished = {
                        // User finished seeking, update the player position
                        player.seekTo(playbackPosition)
                        isUserSeeking = false // Reset flag after seeking
                    },
                    valueRange = 0f..duration.toFloat(),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}
