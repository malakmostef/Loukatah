package com.example.loukatah.domain.usecase

import com.example.loukatah.data.model.Item
import com.example.loukatah.data.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchItemsUseCase @Inject constructor(
    private val repository:
    ItemRepository
) {
    suspend operator fun invoke(
        query: String,
        category: String?,
        status: String?
    ): Flow<List<Item>> {
        return repository.searchItems(query, category, status)
    }
}