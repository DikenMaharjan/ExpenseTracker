package com.example.data.category

import com.example.data.model.Category
import com.example.data.model.toModel
import com.example.database.datasource.LocalCategoryDataSource
import com.example.database.entity.CategoryEntity
import com.example.datastore.proto.UserDataDataSource
import com.example.utils.dispatcher.AppDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CategoryRepository @Inject constructor(
    private val localCategoryDataSource: LocalCategoryDataSource,
    private val userDataDataSource: UserDataDataSource,
    private val appDispatchers: AppDispatchers
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    val categories = userDataDataSource
        .loggedInUserID
        .flatMapLatest { userId ->
            if (userId != null) {
                localCategoryDataSource.getCategories(userId)
            } else {
                flowOf(listOf())
            }
        }.map { it.map(CategoryEntity::toModel) }

    suspend fun insertCategory(
        name: String
    ) {
        withContext(appDispatchers.background) {
            userDataDataSource.withUserID { userId ->
                localCategoryDataSource.insertCategory(
                    CategoryEntity(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        createdUser = userId
                    )
                )
            }
        }
    }
}

