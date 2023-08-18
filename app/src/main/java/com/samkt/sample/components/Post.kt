package com.samkt.sample.components

import android.media.Image
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.samkt.sample.R
import com.samkt.sample.data.model.Posts
import com.samkt.sample.data.model.posts
import com.samkt.sample.data.model.samplePost1
import com.samkt.sample.data.model.samplePost2
import com.samkt.sample.ui.theme.SampleTheme
import com.samkt.sample.ui.theme.sf_pro_light
import com.samkt.sample.ui.theme.sf_pro_regular

@Composable
fun Posts(
    modifier: Modifier = Modifier,
    posts: Posts
) {
    val context = LocalContext.current
    Surface(modifier = modifier.fillMaxWidth()) {
        posts.run {
            Column(modifier = Modifier) {
                if (liked || retweeted) {
                    val (icon, message) = if (liked)
                        Pair(
                            R.drawable.heart_solid,
                            " $sharedName liked"
                        )
                    else
                        Pair(
                            R.drawable.retweet,
                            " $sharedName retweeted"
                        )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 62.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(12.dp),
                            painter = painterResource(id = icon),
                            contentDescription = null
                        )
                        Text(
                            text = message,
                            style = TextStyle(
                                fontFamily = sf_pro_regular,
                                color = Color(0xFF687684),
                                fontSize = 14.sp
                            )
                        )
                    }
                } else Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(55.dp)
                            .clip(CircleShape),
                        model = ImageRequest.Builder(context)
                            .data(profilePic ?: "")
                            .build(),
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.profile),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontFamily = sf_pro_regular,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                ) {
                                    append("$userName ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontFamily = sf_pro_regular,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 14.sp,
                                        color = Color(0xFF687684)
                                    )
                                ) {
                                    append("$userLabel.$timePosted")
                                }
                            }
                        )
                        Text(
                            text = posts.postInfo,
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.5.sp,
                                fontFamily = sf_pro_light,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        postImage?.let {
                            AsyncImage(
                                modifier = Modifier
                                    .height(175.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp)),
                                model = ImageRequest.Builder(context)
                                    .data(it)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        IconSection(
                            likes = noOfLikes,
                            retweets = noOfReposts,
                            comments = noOfComments
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (isThread) {
                    Row(
                        modifier = Modifier.padding(start = 29.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(37.dp)
                                .clip(CircleShape),
                            model = ImageRequest.Builder(context)
                                .data(profilePic ?: "")
                                .build(),
                            contentDescription = null,
                            placeholder = painterResource(id = R.drawable.profile),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "Show this thread",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = sf_pro_regular,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF4C9EEB),
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Divider(thickness = 0.5.dp)
            }
        }

    }
}


@Composable
fun IconSection(
    modifier: Modifier = Modifier,
    likes: Int,
    retweets: Int,
    comments: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconItem(
            icon = R.drawable.comment,
            number = comments
        )
        IconItem(
            icon = R.drawable.retweet,
            number = retweets
        )
        IconItem(
            icon = R.drawable.heart,
            number = likes
        )
        Icon(
            modifier = Modifier.size(15.dp),
            painter = painterResource(id = R.drawable.share),
            contentDescription = null,
            tint = Color(0xFF687684)
        )
    }
}


@Composable
fun IconItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    number: Int,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(15.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color(0xFF687684)
        )
        Spacer(modifier = Modifier.width(2.dp))
        if (number != 0) {
            Text(
                text = number.toString(),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = sf_pro_regular,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF687684),
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PostsPreview() {
    SampleTheme {
        Posts(posts = samplePost1)
    }
}