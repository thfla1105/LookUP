package styleList.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import styleList.IMAGE_BASE_URL
//import com.styleList.androiddata.R
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "stylelists")
data class Stylelist (
        @PrimaryKey(autoGenerate = true)
        val imageID: Int,
        val imageFile: String,
        val coordiID: Int,
        val temp:Int,
        val userId: String,
        var rating: Int,
        var top: String,
        var bottom: String,
        var dress: String,
        var outwear: String,
        var coordi_literal: String,
        var style: String

) {
    val imageUrl
        get() = "$IMAGE_BASE_URL/$imageFile.webp"
    val thumbnailUrl
        get() = "$IMAGE_BASE_URL/${imageFile}.webp"
}