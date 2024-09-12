package com.open.instafun.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.open.instafun.data.Resource
import com.open.instafun.viewmodel.AppViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkOpenerView(sharedUrl: String?,viewmodel: AppViewmodel = hiltViewModel()) {

    val videoInformation by viewmodel.getVideoLink.observeAsState()
    LaunchedEffect(key1 = Unit) {
            viewmodel.getVideo(sharedUrl!!)

    }

    LaunchedEffect(key1 = videoInformation) {
        if (videoInformation is Resource.Success) {
            Log.d("valala", "" + videoInformation!!.data?.downloadUrl)
        }else  {
            Log.d("valala", "ALREADY")

        }

    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Share URL Example") })
        }
    ) { paddingValues ->
        Content(paddingValues, sharedUrl)
    }
}

@Composable
fun Content(paddingValues: PaddingValues, sharedUrl: String?) {
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
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LinkOpenerView(sharedUrl = "https://example.com")
}
