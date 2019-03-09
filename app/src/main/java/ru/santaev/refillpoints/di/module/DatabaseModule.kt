package ru.santaev.refillpoints.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.data.factory.IDatabaseFactory
import ru.santaev.refillpoints.database.DatabaseFactory
import ru.santaev.refillpoints.database.RefillPointsDatabase
import ru.santaev.refillpoints.database.dao.IRefillPointQueriesDao
import ru.santaev.refillpoints.database.dao.IRefillPointsDao

@Module
class DatabaseModule {

    @Provides
    fun provideRefillPointsDao(database: RefillPointsDatabase): IRefillPointsDao {
        return database.refillPointsDao()
    }

    @Provides
    fun provideRefillPointQueriesDao(database: RefillPointsDatabase): IRefillPointQueriesDao {
        return database.refillPointQueriesDao()
    }

    @Provides
    fun provideDatabase(context: Context): RefillPointsDatabase {
        return RefillPointsDatabase.buildDatabase(context)
    }

    @Provides
    fun provideDatabaseFactory(
        refillPointsDao: IRefillPointsDao,
        refillPointQueriesDao: IRefillPointQueriesDao
    ): IDatabaseFactory {
        return DatabaseFactory(refillPointsDao, refillPointQueriesDao)
    }
}