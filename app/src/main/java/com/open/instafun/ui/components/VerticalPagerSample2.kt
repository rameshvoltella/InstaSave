package com.open.instafun.ui.components

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.TextField
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.open.instafun.viewmodel.AppViewmodel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun VerticalPagerSample2(viewModel: AppViewmodel = hiltViewModel()) {
    var password by remember { mutableStateOf("") }
    var isPasswordCorrect by remember { mutableStateOf(false) }

    // Define your correct password
    val correctPassword = "@@@"
    val pagerState = rememberPagerState(pageCount = { viewModel.downloads.size })
    val context = LocalContext.current

    val refreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    // Call refreshData initially to load data
    LaunchedEffect(Unit) {
        viewModel.refreshData()
    }

    // Monitor the page changes
    LaunchedEffect(pagerState.currentPage) {
        viewModel.setCurrentPage(pagerState.currentPage)
    }

    DisposableEffect(Unit) {
        val activity = context as? Activity
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onDispose {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
    if (!isPasswordCorrect) {
        // Password input section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                    isPasswordCorrect = newPassword == correctPassword
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isPasswordCorrect) "Password correct, loading content..." else "Enter password",
            )
        }
    } else {
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshState)
        ) { page ->
            val isPlaying = page == viewModel.currentPageIndex
            val download = viewModel.downloads.getOrNull(page)
            CustomMediaPlayerWithImages(download?.downloadUrl ?: "", isPlaying)
        }
    }
}


