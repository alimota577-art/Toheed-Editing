package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OrderEntity::class, ServicePriceEntity::class], version = 1, exportSchema = false)
abstract class ToheedDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun servicePriceDao(): ServicePriceDao

    companion object {
        @Volatile
        private var INSTANCE: ToheedDatabase? = null

        fun getDatabase(context: Context): ToheedDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToheedDatabase::class.java,
                    "toheed_edits_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
