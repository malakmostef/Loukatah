package com.example.loukatah.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.loukatah.data.model.ItemCategory
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemCategoryRepositoryImpl @Inject constructor(
) : ItemCategoryRepository {
    private val categoriesCollection = Firebase.firestore.collection("categories")
    private val _categoryFlow = MutableSharedFlow<List<ItemCategory>>(replay = 1)
    private var snapshotListener: ListenerRegistration? = null

    init {
        // تحميل الفئات الأساسية عند البدء
        loadInitialCategories()
        setupFirestoreListener()
    }

    private fun loadInitialCategories() {
        categoriesCollection.get().addOnSuccessListener { snapshot ->
            if (snapshot.isEmpty) {
                // إذا لم تكن هناك فئات، ننشئ الفئات الأساسية
                createDefaultCategories()
            }
        }
    }

    private fun createDefaultCategories() {
        val defaultCategories = listOf(
            mapOf(
                "id" to "1",
                "name" to "أغراض شخصية",
                "iconName" to "people"
            ),
            mapOf(
                "id" to "2",
                "name" to "إلكترونيات",
                "iconName" to "phone_android"
            ),
            mapOf(
                "id" to "3",
                "name" to "مستندات",
                "iconName" to "newspaper"
            )
        )

        defaultCategories.forEach { category ->
            categoriesCollection.document(category["id"] as String).set(category)
        }
    }

    private fun setupFirestoreListener() {
        snapshotListener = categoriesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener

            val categories = snapshot?.documents?.mapNotNull { doc ->
                val name = doc.getString("name") ?: return@mapNotNull null
                val iconName = doc.getString("iconName") ?: "all_inbox"

                ItemCategory(
                    id = doc.id,
                    name = name,
                    selectedIcon = getIconByName(iconName, true),
                    unselectedIcon = getIconByName(iconName, false)
                )
            } ?: emptyList()

            CoroutineScope(Dispatchers.IO).launch {
                _categoryFlow.emit(categories)
            }
        }
    }

    private fun getIconByName(name: String, isSelected: Boolean): ImageVector {
        return when (name) {
            "people" -> if (isSelected) Icons.Filled.People else Icons.Outlined.People
            "phone_android" -> if (isSelected) Icons.Filled.PhoneAndroid else Icons.Outlined.PhoneAndroid
            "newspaper" -> if (isSelected) Icons.Filled.Newspaper else Icons.Outlined.Newspaper
            else -> if (isSelected) Icons.Filled.AllInbox else Icons.Outlined.AllInbox
        }
    }

    override fun getCategories(): Flow<List<ItemCategory>> = _categoryFlow
}