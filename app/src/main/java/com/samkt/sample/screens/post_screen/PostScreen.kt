package com.samkt.sample.screens.post_screen

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.samkt.sample.util.UiEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val state = viewModel.uiState.collectAsState().value
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(
        key1 = true,
        block = {
            focusRequester.requestFocus()
            viewModel.uiEvents.collect {
                when (it) {
                    is UiEvents.PopBackStack -> {
                        navController.popBackStack()
                    }

                    is UiEvents.ShowSnackBar -> Unit
                }
            }
        }
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
                        focusManager.clearFocus()
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
                        focusManager.clearFocus()
                        focusRequester.freeFocus()
                        viewModel.createPost()
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
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
            ) {
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
                }
                BasicTextField(
                    modifier = Modifier
                        .padding(start = 58.dp)
                        .focusRequester(focusRequester),
                    value = state.postInformation,
                    onValueChange = viewModel::onPostInfo,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = sf_pro_light,
                        fontWeight = FontWeight(400)
                    ),
                    decorationBox = { innerTextField ->
                        if (state.postInformation.isEmpty()) {
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
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    state.imageUrl?.let {
                        AsyncImage(
                            modifier = Modifier
                                .height(300.dp)
                                .width(260.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            model = it,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(modifier = Modifier) {
                    Divider()
                    IconButton(
                        onClick = {
                            // TODO: Choose the image
                            scope.launch {
                               focusManager.clearFocus()
                                delay(500)
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color(0xFF4C9EEB)
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = R.drawable.gallery),
                            contentDescription = null
                        )
                    }
                }
            }
            if (state.loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun KeyboardAwareContent() {
    var text by remember {
        mutableStateOf("")
    }
}


@Composable
fun isKeyBoardVisible(): Boolean {
    val context = LocalContext.current
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.isActive
}
