package ru.santaev.refillpoints.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.santaev.refillpoints.database.entity.RefillPointEntity

@Dao
interface IRefillPointsDao {

    @Query("SELECT * FROM ${RefillPointEntity.tableName}")
    fun getRefillPoints(): Flowable<List<RefillPointEntity>>

    @Query("SELECT * FROM ${RefillPointEntity.tableName} WHERE external_id=:externalId")
    fun getRefillPoint(externalId: String): Single<RefillPointEntity>

    @Query("SELECT * FROM ${RefillPointEntity.tableName} WHERE id=:id")
    fun getRefillPoint(id: Long): Single<RefillPointEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(entities: List<RefillPointEntity>): Completable

    @Update
    fun update(refillPointDto: RefillPointEntity): Completable

    @Query("DELETE FROM ${RefillPointEntity.tableName}")
    fun deleteAll(): Completable
}