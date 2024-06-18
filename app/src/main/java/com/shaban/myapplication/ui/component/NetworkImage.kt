package com.shaban.myapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

/**
 * A composable function that loads and displays an image from a network URL with support for SVG format.
 *
 * @param imageUrl the URL of the image to be loaded from the network.
 * @param placeholder resource ID of the placeholder image to be displayed if the main image cannot be loaded.
 * @param modifier Modifier for styling and layout customization.
 * @param contentDescription a description of the image for accessibility purposes. Defaults to an empty string.
 * @param contentScale defines how the image should be scaled inside the view. Defaults to ContentScale.Crop.
 */
@Composable
fun NetworkImage(
    imageUrl: String,
    placeholder: Int,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop,
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .decoderFactory(SvgDecoder.Factory()) // To support svg format
        .fallback(placeholder)  // Set the fallback drawable to use if data is null.
        .crossfade(true)
        .build()

    SubcomposeAsyncImage(
        model = imageRequest,
        loading = {
            Image(
                painter = painterResource(id = placeholder),
                contentDescription = "Loading Placeholder",
                contentScale = ContentScale.Crop
            )
        },
        error = {
            Image(
                painter = painterResource(id = placeholder),
                contentDescription = "Error Placeholder",
                contentScale = ContentScale.Crop
            )
        },
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier.background(Color.Transparent),
    )
}