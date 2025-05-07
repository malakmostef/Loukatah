package com.example.loukatah

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.loukatah.data.repository.StorageRepository
import com.example.loukatah.presentation.view.navigation.NavGraph
import com.example.loukatah.ui.theme.LoukatahTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val storageRepo = StorageRepository()

    // لتحديد صورة من المعرض
    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri: Uri? = result.data?.data
            uri?.let { uploadImage(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoukatahTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        onPickImage = { launchImagePicker() } // تمرير الدالة لاختيار الصورة
                    )
                }
            }
        }
    }

    private fun launchImagePicker() {
        pickImage.launch(Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        })
    }

    private fun uploadImage(uri: Uri) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val imageUrl = storageRepo.uploadImage(
                    context = this@MainActivity,
                    filePath = "users/user_${System.currentTimeMillis()}/profile.jpg",
                    imageUri = uri
                )

                // عرض رسالة نجاح الرفع
                Toast.makeText(
                    this@MainActivity,
                    "تم رفع الصورة بنجاح!",
                    Toast.LENGTH_SHORT
                ).show()

                // يمكنك هنا إرسال الرابط إلى ViewModel أو حفظه كما تحتاج
                println("رابط الصورة: $imageUrl")

            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "خطأ في رفع الصورة: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}