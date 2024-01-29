package com.shady.mygalleryapp.core.util.permissions


import android.content.Context
import android.content.pm.PackageManager

fun Context.checkPermissionGranted(permission: String): Boolean =
    checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

fun Context.checkAllPermissionsGranted(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        if (!checkPermissionGranted(permission)) {
            return false
        }
    }
    return true
}

fun checkAllPermissionsGranted(permissions: Map<String, Boolean>): Boolean {
    for ((_, granted) in permissions) {
        if (!granted) {
            return false
        }
    }
    return true
}
