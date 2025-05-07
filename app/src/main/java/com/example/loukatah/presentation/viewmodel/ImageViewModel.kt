package com.example.loukatah.presentation.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loukatah.data.repository.StorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel() {
    private val repository = StorageRepository()

    private val _imageUrl = MutableStateFlow("")
    val imageUrl = _imageUrl.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun uploadImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val url = repository.uploadImage(
                    context = context,
                    filePath = "users/${System.currentTimeMillis()}.jpg",
                    imageUri = uri
                )
                _imageUrl.value = url
            } catch (e: Exception) {
                _error.value = e.message ?: "حدث خطأ غير معروف"
            } finally {
                _isLoading.value = false
            }
        }
    }
}