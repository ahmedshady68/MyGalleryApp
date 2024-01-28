package com.shady.mygalleryapp.core.util.permissions


import android.content.Context
import android.content.pm.PackageManager

fun Context.checkPermissionGranted(permission: String): Boolean =
    checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

fun Context.checkAllPermissionsGranted(permissions: Array<String>): Boolean {
    return true
}

fun checkAllPermissionsGranted(permissions: Map<String, Boolean>): Boolean {
    return true
}
