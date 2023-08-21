package com.samkt.sample.screens.post_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.samkt.sample.R
import com.samkt.sample.ui.theme.sf_pro_light
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PostViewModel = viewModel()

) {
    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::chooseImage
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = null
                    )
                }
                Button(
                    onClick = {
                              viewModel.createPost()
                        // TODO: Add a post
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4C9EEB)
                    )
                ) {
                    Text(text = "Post")
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(text = viewModel.successful.value)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(context)
                        .data("https://cdn.pixabay.com/photo/2023/01/28/20/23/ai-generated-7751688_1280.jpg")
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.profile)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    // TODO: Choose the image
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.image),
                        contentDescription = null
                    )
                }
                viewModel.imageUrl?.let {
                    AsyncImage(
                        modifier = Modifier
                            .height(40.dp)
                            .width(60.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        model = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            BasicTextField(
                modifier = Modifier.padding(start = 58.dp),
                value = viewModel.postInformation,
                onValueChange = viewModel::onPostInfo,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = sf_pro_light,
                    fontWeight = FontWeight(400)
                ),
                decorationBox = { innerTextField ->
                    if (viewModel.postInformation.isEmpty()) {
                        Text(
                            text = "Whats happening?",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = sf_pro_light,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF687684),
                            )
                        )
                    }
                    innerTextField()
                }
            )
            viewModel.fbUrl?.let {
                AsyncImage(
                    modifier = Modifier
                        .height(40.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

        }

    }
}
