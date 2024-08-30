package eu.tutorials.myattendanceapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController


@Composable
fun logmeMenu(navController: NavController,
              todoViewModel: TodoViewModel){

    //> Background Image
    //> Auto open and scan employee Qrcode
    //> Implement latlong
    //> Display Employee details on screen (after scan)
    //> Button (to continue and Save to DB [Room/ SQlite]
    //> Back Menu
    var code by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
        }
    )

    Log.i("Credentials/logmemenu-start", "")

    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        
    ) {
        
        /*Button(onClick = { navController.navigate(Routes.loginmenu)}) {
            
            Text(text = "Back to main menu")
            
        }*/
        if (hasCamPermission) {
            Log.i("Credentials/logmemenu-AAA", hasCamPermission.toString())
            Log.i("Credentials/logmemenu-BBB", "")

            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)


                    /*val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                previewView.width,
                                previewView.height
                            )
                        )
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    */
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { result ->
                            code = result
                        }
                    )
                    try {
                        Log.i("Credentials/logmemenu-CCC", code)
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        Log.i("Credentials/logmemenu2", code)
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = Modifier.weight(1f)
            )
            Log.i("Credentials/logmemenu-DDD", code)
            /*Text(

                text = code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)

            )*/
            if(code != "") {
                Log.i("Credentials/logmemenu-EEE", code)
                ScannedAlert(code = code,
                    onDismiss = {code = ""},
                    todoViewModel = TodoViewModel(),
                    navController
                    )
            }

        }
        
        
    }

}
