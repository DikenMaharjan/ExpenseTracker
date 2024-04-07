package com.example.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

const val DEFAULT_CATEGORIES_USER_ID = "default_categories"

@Dao
interface CategoryDao {

    @Query("""Select * from CategoryEntity where createdUser = :userID or createdUser = "$DEFAULT_CATEGORIES_USER_ID"""")
    fun getAllCategoriesOfUser(
        userID: String,
    ): Flow<List<CategoryEntity>>

    @Query("Select count(*) from CategoryEntity")
    suspend fun getCategoryCount(): Int

    @Upsert
    suspend fun insert(vararg categories: CategoryEntity)

    suspend fun insert(categories: List<CategoryEntity>) {
        insert(*categories.toTypedArray())
    }
}