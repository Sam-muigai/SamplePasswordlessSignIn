package com.samkt.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samkt.sample.components.Posts
import com.samkt.sample.components.TopBar
import com.samkt.sample.components.bottomNavItems
import com.samkt.sample.data.model.posts
import com.samkt.sample.data.model.samplePost1
import com.samkt.sample.data.model.samplePost2
import com.samkt.sample.data.model.samplePost3
import com.samkt.sample.ui.theme.SampleTheme
import com.samkt.sample.util.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier,navController: NavController) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = {
                    // TODO: Add a new post
                          navController.navigate(Routes.POST_SCREEN)
                },
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.tweet),
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 41.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    bottomNavItems.forEach { item ->
                        Icon(
                            modifier = Modifier.size(21.dp),
                            painter = painterResource(id = item.icon),
                            contentDescription = item.route,
                            tint = if(item.isSelected) Color(0xFF4C9EEB) else Color(0xFF687684)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    content = {
                        item {
                            TopBar()
                            Divider(thickness = 0.5.dp)
                        }
                        items(posts) { post ->
                            Posts(
                                modifier = Modifier,
                                posts = post
                            )
                        }
                    }
                )
            }
        }
    }
}


@Preview()
@Composable
fun HomeScreenPreview() {
    SampleTheme {
//        HomeScreen()
    }
}