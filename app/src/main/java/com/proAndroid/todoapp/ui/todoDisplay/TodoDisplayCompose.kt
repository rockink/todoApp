package com.proAndroid.todoapp.ui.todoDisplay

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.proAndroid.todoapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TodoDisplayCard(
    title: String,
    description: String,
    imageUri: String? = null,
    onCardClick: () -> Unit,
    onCardDeleteAction: () -> Unit,
    onCardChangeAction: () -> Unit
) {
    val deleteCardColor = Color(0xFFE91E63)

    var showDeleteAlert by remember { mutableStateOf(false) }
    if (showDeleteAlert) {
        AlertDialog(
            onDismissRequest = { showDeleteAlert = false },
            confirmButton = {
                Button(onClick = { showDeleteAlert = false ;onCardDeleteAction()}) {
                    Text(text = "YES")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteAlert = false }) {
                    Text("NO")
                }
            },
            text = { Text(text = "Are you sure you want to delete todo?")}
        )
    }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onCardClick() }) {
        Column(Modifier.padding(20.dp)) {
            Text(title, fontSize = 18.sp, modifier = Modifier.padding(10.dp))
            Text(description, Modifier.padding(10.dp))
            Image(
                uri = imageUri!!,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(220.dp)
            )
            Row(modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.End)
                .padding(top = 20.dp)) {
                Button(
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = deleteCardColor, contentColor = Color.White),
                    onClick = {showDeleteAlert = true }) {
                    Text(text = "DELETE")
                }
                Button(
                    onClick = onCardChangeAction,
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    elevation =  ButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Text(text = "CHANGE")
                }
            }
        }
    }
}



@Composable
fun Image(
    uri: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
    ) {
    val painter by uriPainter(uri = uri)
    painter?:return
    Image(painter!!,contentDescription, modifier, alignment, contentScale, alpha, colorFilter)
}

@Composable
fun uriPainter(uri: String): State<Painter?> {
    val current = LocalContext.current
    return produceState(initialValue =  null) {
        launch(Dispatchers.Default) {
            val bitmap = Glide.with(current)
                .asBitmap()
                .load(uri)
                .submit()
                .get()
                .asImageBitmap()
            value = BitmapPainter(bitmap)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoDisplayCardPreview() {
//    TodoDisplayCard(
//        "Programming Todo",
//        "Expand our todo App",
//        R.drawable.programming_image,
//        onCardClick = {},
//        onCardChangeAction = {},
//        onCardDeleteAction = {}
//    )
}