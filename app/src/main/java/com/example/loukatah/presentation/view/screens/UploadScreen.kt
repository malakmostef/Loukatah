package com.example.loukatah.presentation.view.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.loukatah.presentation.viewmodel.ImageViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UploadScreen(onPickImage: () -> Unit) {
    val context = LocalContext.current
    val viewModel: ImageViewModel = viewModel()
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            viewModel.uploadImage(context, it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // زر اختيار الصورة
        Button(onClick = onPickImage) {
            Text("اختر صورة للرفع")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // عرض حالة التحميل
        if (viewModel.isLoading.value) {
            CircularProgressIndicator()
        }

        // عرض الخطأ إذا حدث
        if (viewModel.error.value.isNotEmpty()) {
            Text(
                text = viewModel.error.value,
                color = MaterialTheme.colorScheme.error
            )
        }

        // عرض الصورة المختارة
        imageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "الصورة المختارة",
                modifier = Modifier.size(200.dp)
            )
        }

        // عرض الصورة بعد الرفع
        if (viewModel.imageUrl.value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("تم رفع الصورة بنجاح!")
            Image(
                painter = rememberAsyncImagePainter(viewModel.imageUrl.value),
                contentDescription = "الصورة المرفوعة",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}
