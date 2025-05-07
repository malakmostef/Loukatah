package com.example.loukatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loukatah.data.model.Item
import com.example.loukatah.domain.usecase.SearchItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchItems: SearchItemsUseCase
) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Item>>(emptyList())
    val searchResults: StateFlow<List<Item>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun search(query: String, category: String?, status: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                searchItems(query, category, status).collect { items: List<Item> ->
                    _searchResults.value = items
                }
            } catch (e: Exception) {
                _error.value = "فشل في البحث: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
