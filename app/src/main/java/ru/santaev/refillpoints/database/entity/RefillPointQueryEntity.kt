package ru.santaev.refillpoints.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = RefillPointQueryEntity.tableName
)
data class RefillPointQueryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "radius")
    val radius: Long,
    @ColumnInfo(name = "date")
    val date: Long
) {

    companion object {

        const val tableName = "refill_point_queries"
    }
}
