package com.meew.overdroch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.meew.overdroch.R
import com.meew.overdroch.data.maps.GameMap
import com.meew.overdroch.ui.BottomNavigationBar
import com.meew.overdroch.ui.theme.Grey


@Composable
fun Maps( onBottomBarClick: (String) -> Unit,onMapClick: (String) -> Unit, gameMaps:MutableList<GameMap>) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    title = { Text(text = "Select a map") }
                )
            },
            bottomBar = {
                BottomNavigationBar { t -> onBottomBarClick(t) }
            },
            backgroundColor = Color.Transparent
        ) {
            LazyColumn(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 43.dp)) {
                itemsIndexed(gameMaps) { id, map ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .clickable { onMapClick(map.name) },
                        elevation = 10.dp
                    ) {
                        Column {
                            AsyncImage(
                                model = map.screenshot as String,
                                contentDescription = map.name,
                                modifier = Modifier.clip(
                                    RoundedCornerShape(
                                        topEnd = 4.dp,
                                        topStart = 4.dp,
                                        bottomEnd = 0.dp,
                                        bottomStart = 0.dp
                                    )
                                ).shadow(1.dp),
                                alpha = 0.9f,
                                alignment = Alignment.TopCenter,

                                )

                            Spacer(modifier = Modifier.height(0.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.background(Grey.copy(alpha = 0.5f))
                            ) {
                                Text(map.name, Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }
        }
    }
}
