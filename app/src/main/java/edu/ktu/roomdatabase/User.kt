package edu.ktu.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @SerializedName("name") @ColumnInfo(name = "first_name") val firstName: String?,
        @SerializedName("surname") @ColumnInfo(name="last_name") val lastName: String?
)

object Data
{
    data class Query(val searchCount: Int)
    data class SearchResult(val query: Query)
    data class Result(val searchResult: SearchResult)
}