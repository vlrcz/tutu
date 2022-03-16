package com.vlad.tutu.feature.repositories.list

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vlad.tutu.feature.repositories.list.Repository.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = TABLE_NAME)
data class Repository(
    @PrimaryKey
    val id: Long,
    val name: String,
    @Json(name = FULL_NAME)
    @ColumnInfo(name = FULL_NAME)
    val fullName: String
) : Parcelable {

    companion object {
        const val TABLE_NAME = "repositories"
        const val FULL_NAME = "full_name"
    }
}