package com.example.loukatah.domain.usecase

import com.example.loukatah.data.model.Item
import com.example.loukatah.data.repository.ItemRepository
import javax.inject.Inject

class UpdateItemUseCase @Inject constructor(
    private val repository:
    ItemRepository
) {
    suspend operator fun invoke(item: Item) {
        repository.updateItem(item)
    }
}