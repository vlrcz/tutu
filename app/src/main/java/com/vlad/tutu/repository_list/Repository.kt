package com.vlad.tutu.repository_list

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vlad.tutu.db.ReposContract
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = ReposContract.TABLE_NAME)
data class Repository(
    @Json(name = "id")
    @PrimaryKey
    @ColumnInfo(name = ReposContract.Columns.ID)
    val id: Long,
    @Json(name = "name")
    @ColumnInfo(name = ReposContract.Columns.NAME)
    val name: String?,
    @Json(name = "full_name")
    @ColumnInfo(name = ReposContract.Columns.FULL_NAME)
    val fullName: String?
) : Parcelable