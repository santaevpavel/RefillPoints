package ru.santaev.refillpoints.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import ru.santaev.refillpoints.database.entity.RefillPointQueryEntity

@Dao
interface IRefillPointQueriesDao {

    @Insert
    fun insertRefillPointQuery(refillPointQuery: RefillPointQueryEntity): Completable

    @Query("SELECT * FROM ${RefillPointQueryEntity.tableName}")
    fun getRefillPointQueries(): Flowable<List<RefillPointQueryEntity>>

    @Query("SELECT * FROM ${RefillPointQueryEntity.tableName} WHERE date > :afterDate")
    fun getRefillPointQueries(afterDate: Long): Flowable<List<RefillPointQueryEntity>>
}