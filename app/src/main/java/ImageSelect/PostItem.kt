package ImageSelect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostItem(
        val imageID:Int,val nowpurpose:Int, val image:Int
):Parcelable