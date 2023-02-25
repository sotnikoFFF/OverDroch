package com.meew.overdroch.ui.screens

import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.ui.theme.BlueWhite
import com.meew.overdroch.ui.theme.Grey
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun HeroDetailScreen(hero: Hero,  bottomNav: (String) -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(Color.DarkGray)
        ) {
            YoutubeScreen(
                hero.story?.media?.link!!.replace("https://www.youtube.com/watch?v=", "", true).replace("/", ""),
                Modifier.fillMaxSize()
            )
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Row {
                AsyncImage(
                    model = hero.portrait as String,
                    contentDescription = hero.name,
                    modifier = Modifier.clip(RoundedCornerShape(topEnd = 40.dp , topStart = 40.dp, bottomEnd = 40.dp, bottomStart = 40.dp)),
                    alpha = 0.9f,
                    alignment = Alignment.TopCenter,
                )
            // Display hero name and role
            Text(
                text = "- ${hero.name} -" + "\n" +
                        "  ${hero.role}  ",
                style = MaterialTheme.typography.h5.copy(Grey),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
            // Display hero abilities
            Text(
                text = "- Abilities -",
                style = MaterialTheme.typography.subtitle1.copy(Color.White),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
            )
            for (ability in hero.abilities!!) {
                Text(
                    text = "- ${ability.name}: ${ability.description}",
                    style = MaterialTheme.typography.body1.copy(Grey),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "- Summary -",
                style = MaterialTheme.typography.subtitle1.copy(Color.White),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
            )
            Text(
                text = hero.story!!.summary!!,
                style = MaterialTheme.typography.body1.copy(Grey),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
            )
            for (chapter in hero.story!!.chapters!!) {
                Spacer(modifier = Modifier.height(20.dp))
                AsyncImage(
                    model = chapter.picture as String,
                    contentDescription = hero.name,
                    modifier = Modifier.background(BlueWhite.copy(alpha = 0.8f)).border(
                        width = 4.dp,
                        color = Grey,
                        shape = RoundedCornerShape(0.dp)
                    ),
                    alpha = 0.9f,
                    alignment = Alignment.TopCenter,
                )
                Text(
                    text = "${chapter.title}",
                    style = MaterialTheme.typography.subtitle1.copy(Color.White),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                )
                Text(
                    text = "- ${chapter.content}",
                    style = MaterialTheme.typography.body1.copy(Grey),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
@Composable
fun YoutubeScreen(
    videoId: String,
    modifier: Modifier
) {
    val ctx = LocalContext.current
    AndroidView(factory = {
        var view = YouTubePlayerView(it)
        view.enableAutomaticInitialization=true
        view.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        )
        view
//                view

    })
}