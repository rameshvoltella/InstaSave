package com.open.instafun.ui.components


import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.open.instafun.database.InstagramRepository
import com.open.instafun.database.InstagramDBDownloader
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.hilt.navigation.compose.hiltViewModel
import com.open.instafun.viewmodel.AppViewmodel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun VerticalPagerSample2(viewModel: AppViewmodel = hiltViewModel()) {
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
    VerticalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
    ) { page ->
        val isPlaying = page == viewModel.currentPageIndex
        val download = viewModel.downloads.getOrNull(page)
        CustomMediaPlayerWithImages(download?.downloadUrl ?: "",isPlaying)
    }
}


