package com.example.loukatah.domain.usecase

import com.example.loukatah.data.model.Item
import com.example.loukatah.data.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemByIdUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(itemId: String): Flow<Item?> {
        return repository.getItemById(itemId)
    }
}