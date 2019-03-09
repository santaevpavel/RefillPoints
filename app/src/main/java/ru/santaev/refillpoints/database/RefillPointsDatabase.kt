package ru.santaev.refillpoints.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.santaev.refillpoints.database.dao.IRefillPointQueriesDao
import ru.santaev.refillpoints.database.dao.IRefillPointsDao
import ru.santaev.refillpoints.database.entity.RefillPointEntity

@Database(entities = [RefillPointEntity::class], version = 2, exportSchema = false)
abstract class RefillPointsDatabase : RoomDatabase() {

    abstract fun refillPointsDao(): IRefillPointsDao

    abstract fun refillPointQueriesDao(): IRefillPointQueriesDao

    companion object {

        fun buildDatabase(context: Context): RefillPointsDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                RefillPointsDatabase::class.java, "refill_points.db"
            )
                .build()
        }
    }
}


