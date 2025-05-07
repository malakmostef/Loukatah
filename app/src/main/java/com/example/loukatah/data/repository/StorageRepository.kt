package com.example.loukatah.data.repository

import android.content.Context
import android.net.Uri
import com.example.loukatah.utils.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import kotlin.time.Duration.Companion.seconds

class StorageRepository {
    suspend fun uploadImage(
        context: Context,
        bucketName: String = "photos", // اسم الـ Bucket في Supabase
        filePath: String,
        imageUri: Uri
    ): String {
        return withContext(Dispatchers.IO) {
            // قراءة ملف الصورة
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val bytes = inputStream?.readBytes() ?: throw Exception("Cannot read image")

            // رفع الصورة إلى Supabase
            SupabaseClient.storage.from(bucketName).upload(
                path = filePath, // مثال: "users/user_123/profile.jpg"
                data = bytes,
                upsert = true // استبدال الملف إذا كان موجودًا
            )

            // إنشاء رابط مشفر للصورة (صالحة لمدة 24 ساعة)
            SupabaseClient.storage.from(bucketName).createSignedUrl(
                path = filePath,
                expiresIn = (60 * 60 * 24).seconds
            ).toString()
        }
        suspend fun getPublicUrl(
            bucketName: String = "photos",
            filePath: String
        ): String = withContext(Dispatchers.IO) {
            // للحصول على رابط عام (إذا كانت الصورة public)
            SupabaseClient.storage.from(bucketName).publicUrl(filePath)
        }
    }
}