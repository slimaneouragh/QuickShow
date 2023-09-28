package com.example.remch.view.Camera_Screen

import androidx.compose.runtime.Composable
import com.example.remch.MyViewModel
import com.example.remch.utils.NoPermissionScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Camera(viewModel: MyViewModel) {
    val cameraPermission = rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    MainContent(
        hasPermission = cameraPermission.status.isGranted,
        onRequestPermissions = cameraPermission::launchPermissionRequest,
        viewModel = viewModel
    )
}

@Composable
fun MainContent(
    hasPermission: Boolean,
    onRequestPermissions: () -> Unit,
    viewModel: MyViewModel
) {
    if (hasPermission) {
        CameraScreen(viewModel = viewModel)
    } else {
        NoPermissionScreen (onRequestPermissions)
    }

}