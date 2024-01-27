package com.shady.mygalleryapp.main.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Photo
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen() {
    NavigationBar {
        NavigationBarItem(selected = true,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Photo,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            label = {
                Text(text = "Images")
            })
        NavigationBarItem(selected = true,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.PhotoLibrary,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            label = {
                Text(text = "Buckets")
            })
    }
}

@Preview
@Composable
fun Preview() {
    MainScreen()
}