package com.shady.mygalleryapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shady.mygalleryapp.main.ui.GalleryApp
import com.shady.mygalleryapp.core.ui.theme.MyGalleryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyGalleryAppTheme {
                GalleryApp()
            }
        }
    }
}