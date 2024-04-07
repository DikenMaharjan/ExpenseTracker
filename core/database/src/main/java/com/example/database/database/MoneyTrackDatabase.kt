package com.example.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.database.dao.CategoryDao
import com.example.database.dao.ExpenseDao
import com.example.database.entity.CategoryEntity
import com.example.database.entity.ExpenseEntity
import com.example.database.typeconverters.Converters


@Database(
    entities = [
        CategoryEntity::class,
        ExpenseEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converters::class
)
abstract class MoneyTrackDatabase : RoomDatabase() {

    abstract fun getExpenseDao(): ExpenseDao
    abstract fun getCategoryDao(): CategoryDao
}