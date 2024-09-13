package com.open.instafun.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.open.instafun.R
import com.open.instafun.data.Resource
import com.open.instafun.database.InstagramDBDownloader
import com.open.instafun.viewmodel.AppViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkOpenerView(sharedUrl: String?,viewmodel: AppViewmodel = hiltViewModel()) {

    val videoInformation by viewmodel.getVideoLink.observeAsState()
    LaunchedEffect(key1 = Unit) {
            viewmodel.getVideo(sharedUrl!!)
            viewmodel.refreshData()


    }


    LaunchedEffect(key1 = videoInformation) {
        if (videoInformation is Resource.Success) {
            Log.d("valala", "" + videoInformation!!.data?.downloadUrl)
            viewmodel.refreshData()
        }else  {
            Log.d("valala", "ALREADY")

        }

    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Share URL Example") })
        }
    ) { paddingValues ->
        Content(paddingValues, sharedUrl,viewmodel,viewmodel.downloads)
    }
}

@Composable
fun Content(paddingValues: PaddingValues, sharedUrl: String?,viewmodel:AppViewmodel,videoList: List<InstagramDBDownloader>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Shared URL:")
        Spacer(modifier = Modifier.height(8.dp))

        // Display the shared URL if it's available
        if (sharedUrl != null) {
            Text(text = sharedUrl)
        } else {
            Text(text = "No URL shared")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display the video list from the database
        LazyColumn {
            items(videoList.size) { video ->
                viewmodel.downloads.getOrNull(video)?.let {
                    VideoListItem(video = it, onDelete = {
                    viewmodel.deleteVideo(videoList.get(video))
                    })
                }
            }
        }

    }
}

@Composable
fun VideoListItem(video: InstagramDBDownloader, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Thumbnail on the left

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(video.videoThumbnail)
                .crossfade(true)
                .placeholder(R.drawable.ic_thump_place)
                .error(R.drawable.ic_thump_place)
                .build(),
            contentDescription = "Drawable Image",
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .width(100.dp)
                .height(64.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        // Title in the center
        Text(
            text = video.videoTitle,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )

        // Delete button (image) on the right
        Image(
            painter = painterResource(id = R.drawable.garbage),
            contentDescription = "Delete",
            modifier = Modifier
                .size(24.dp) // You can adjust the size based on your need
                .clickable { onDelete() } // Handle click event
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LinkOpenerView(sharedUrl = "https://example.com")
}
