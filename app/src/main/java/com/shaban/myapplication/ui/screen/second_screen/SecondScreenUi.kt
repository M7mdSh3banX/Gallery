package com.shaban.myapplication.ui.screen.second_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.shaban.myapplication.ui.component.Loading

@Composable
fun SecondScreenUi(component: SecondScreen) {
    val model by component.models.subscribeAsState()

    Loading(isLoading = model.isLoading)
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = model.images.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(model.images) { image ->
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(shape = MaterialTheme.shapes.medium)
                            .background(color = Color.White),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            onClick = component::onClickBack
        ) {
            Text(text = "Back")
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = component::onClickNext
        ) {
            Text(text = "Next")
        }
    }
}