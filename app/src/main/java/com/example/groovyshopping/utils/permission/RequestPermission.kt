package com.example.groovyshopping.utils.permission

import android.Manifest
import android.os.Build
import androidx.fragment.app.Fragment
import com.homecookapp.user.utils.Constants
import com.homecookapp.user.utils.permission.HasPermission
import pub.devrel.easypermissions.EasyPermissions

object RequestPermission {

    fun location(hostFragment: Fragment) :Boolean {

        if (HasPermission.location(hostFragment.requireContext())) return true

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                hostFragment,
                "You need to accept location permissions to use this app.",
                Constants.REQ_LOCATION_PERMISSIONS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                hostFragment,
                "You need to accept location permissions to use this app.",
                Constants.REQ_LOCATION_PERMISSIONS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

        return false
    }

    fun externalStorage(hostFragment: Fragment): Boolean {

        if (HasPermission.externalStorage(hostFragment.requireContext())) return true

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            EasyPermissions.requestPermissions(
                hostFragment,
                "You need to accept external permissions to use this app.",
                Constants.REQ_EXTERNAL_PERMISSIONS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            return false
        }

        return true // Auto granted on sdk < 23
    }

}