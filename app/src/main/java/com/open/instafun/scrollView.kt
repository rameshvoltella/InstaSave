package com.open.instafun
import androidx.compose.foundation.ExperimentalFoundationApi
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalPagerSample() {
    val pagerState = rememberPagerState(pageCount = {10})

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        MediaPlayerSample(page)
    }
}

@Composable
fun MediaPlayerSample(page: Int) {
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }
//https://instagram.fpku4-1.fna.fbcdn.net/o1/v/t16/f2/m69/An9gLGWxc4vSeNgzj3KyaLM5mAdoRXROVBhz0YZ8pGNByN__1_LIS8-hEHne0GlUFzCCdIWPHK0KPzCWHjFIenLO.mp4?stp=dst-mp4&efg=eyJxZV9ncm91cHMiOiJbXCJpZ193ZWJfZGVsaXZlcnlfdnRzX290ZlwiXSIsInZlbmNvZGVfdGFnIjoidnRzX3ZvZF91cmxnZW4uY2xpcHMuYzIuMTA3OC5iYXNlbGluZSJ9&_nc_cat=103&vs=7995748780493830_3995073680&_nc_vs=HBksFQIYOnBhc3N0aHJvdWdoX2V2ZXJzdG9yZS9HRWozTlJNQ3BqZEhHU29NQU4yUnhHblQyVjExYnBSMUFBQUYVAALIAQAVAhg6cGFzc3Rocm91Z2hfZXZlcnN0b3JlL0dBTXVLUnNzRTlkVHhFSUJBRnlla3l4TlBtWVpicV9FQUFBRhUCAsgBACgAGAAbABUAACaUu4uyr4CLQBUCKAJDMywXQEmu2RaHKwIYFmRhc2hfYmFzZWxpbmVfMTA4MHBfdjERAHX%2BBwA%3D&ccb=9-4&oh=00_AYAiQ8QN5S_gH4_4CF5rA8zlMHmd_YMhZnPT_0n-jCQ0kA&oe=66E4234B&_nc_sid=d885a2&dl=1

    val mediaItem = MediaItem.fromUri("https://instagram.fpku4-1.fna.fbcdn.net/o1/v/t16/f2/m69/An9gLGWxc4vSeNgzj3KyaLM5mAdoRXROVBhz0YZ8pGNByN__1_LIS8-hEHne0GlUFzCCdIWPHK0KPzCWHjFIenLO.mp4?stp=dst-mp4&efg=eyJxZV9ncm91cHMiOiJbXCJpZ193ZWJfZGVsaXZlcnlfdnRzX290ZlwiXSIsInZlbmNvZGVfdGFnIjoidnRzX3ZvZF91cmxnZW4uY2xpcHMuYzIuMTA3OC5iYXNlbGluZSJ9&_nc_cat=103&vs=7995748780493830_3995073680&_nc_vs=HBksFQIYOnBhc3N0aHJvdWdoX2V2ZXJzdG9yZS9HRWozTlJNQ3BqZEhHU29NQU4yUnhHblQyVjExYnBSMUFBQUYVAALIAQAVAhg6cGFzc3Rocm91Z2hfZXZlcnN0b3JlL0dBTXVLUnNzRTlkVHhFSUJBRnlla3l4TlBtWVpicV9FQUFBRhUCAsgBACgAGAAbABUAACaUu4uyr4CLQBUCKAJDMywXQEmu2RaHKwIYFmRhc2hfYmFzZWxpbmVfMTA4MHBfdjERAHX%2BBwA%3D&ccb=9-4&oh=00_AYAiQ8QN5S_gH4_4CF5rA8zlMHmd_YMhZnPT_0n-jCQ0kA&oe=66E4234B&_nc_sid=d885a2&dl=1")
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
