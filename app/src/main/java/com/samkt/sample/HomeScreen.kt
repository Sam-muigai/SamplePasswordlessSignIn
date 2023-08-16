package com.samkt.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samkt.sample.components.Posts
import com.samkt.sample.components.TopBar
import com.samkt.sample.data.model.samplePost1
import com.samkt.sample.data.model.samplePost2
import com.samkt.sample.data.model.samplePost3
import com.samkt.sample.ui.theme.SampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = { },
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.tweet),
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())) {
            TopBar()
            Divider()
            Posts(
                modifier = Modifier,
                posts = samplePost1
            )
            Posts(
                modifier = Modifier,
                posts = samplePost2
            )
            Posts(
                modifier = Modifier,
                posts = samplePost3
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    SampleTheme {
        HomeScreen()
    }
}