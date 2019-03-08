package ru.santaev.refillpoints.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = RefillPointEntity.tableName,
    indices = [
        Index(
            value = ["external_id"],
            unique = true
        )
    ]
)
data class RefillPointEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "external_id")
    val externalId: String,
    @ColumnInfo(name = "partner_name")
    val partnerName: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "work_hours")
    val workHours: String?,
    @ColumnInfo(name = "phones")
    val phones: String?,
    @ColumnInfo(name = "address_info")
    val addressInfo: String?,
    @ColumnInfo(name = "full_address")
    val fullAddress: String,
    @ColumnInfo(name = "is_viewed")
    val isViewed: Boolean,
    @ColumnInfo(name = "update_date")
    val updateDate: Long
) {

    companion object {

        const val tableName = "refill_points"
    }
}
