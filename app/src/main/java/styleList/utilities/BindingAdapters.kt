package styleList.utilities

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.NumberFormat

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
            .asBitmap()
            .apply { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)}
            .apply { RequestOptions.skipMemoryCacheOf(true) }
            .load("$imageUrl")
            .into(view)
}

/*@BindingAdapter("price")
fun itemPrice(view: TextView, value: Double) {
    val formatter = NumberFormat.getCurrencyInstance()
    val text = "${formatter.format(value)} / each"
    view.text = text
}*/

