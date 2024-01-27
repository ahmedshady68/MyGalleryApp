package com.shady.mygalleryapp.core.util


import android.content.Context
import android.content.pm.PackageManager

fun Context.checkPermissionGranted(permission: String): Boolean =
    checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED