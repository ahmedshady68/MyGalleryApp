package com.shady.mygalleryapp.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.shady.mygalleryapp.BuildConfig
import com.shady.mygalleryapp.R

@Composable
fun GalleryErrorMessage(
    thrown: Throwable,
    modifier: Modifier = Modifier,
) {
    if (BuildConfig.DEBUG) {
        val clipboardManagerUpdated by rememberUpdatedState(LocalClipboardManager.current)
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            GalleryMessage(
                text = stringResource(id = R.string.unexpected_error),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
            GalleryMessage(
                text = thrown.toString(),
                modifier = Modifier
                    .clickable {
                        clipboardManagerUpdated.setText(
                            AnnotatedString
                                .Builder(thrown.stackTraceToString())
                                .toAnnotatedString(),
                        )
                    }
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }
    } else {
        GalleryCenteredMessage(
            text = stringResource(id = R.string.unexpected_error),
            modifier = modifier,
        )
    }
}
