package com.example.database.datasource

import com.example.database.dao.CategoryDao
import com.example.database.dao.DEFAULT_CATEGORIES_USER_ID
import com.example.database.entity.CategoryEntity
import com.example.utils.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocalCategoryDataSource @Inject constructor(
    private val categoryDao: CategoryDao,
    @ApplicationScope private val scope: CoroutineScope,
) {
    init {
        addDefaultCategories()
    }

    fun getCategories(userId: String): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategoriesOfUser(userId)
    }

    suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insert(category)
    }

    private fun addDefaultCategories() {
        scope.launch {
            val numOfCounts = categoryDao.getCategoryCount()
            if (numOfCounts == 0) {
                categoryDao.insert(
                    defaultCategories.map { name ->
                        CategoryEntity(
                            id = name.lowercase(),
                            name = name,
                            createdUser = DEFAULT_CATEGORIES_USER_ID
                        )
                    }
                )
            }
        }
    }

    companion object {
        private val defaultCategories = listOf(
            "Household", "Subscription", "Food", "Pet", "Medical"
        )
    }
}