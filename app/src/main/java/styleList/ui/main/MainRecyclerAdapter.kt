package styleList.ui.main

//import com.styleList.androiddata.R
import Closet.Data_Type
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hedgehog.ratingbar.RatingBar
import network.RetrofitClient
import network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import styleList.data.RatingData
import styleList.data.RatingResponse
import styleList.data.StyleType
import styleList.data.Stylelist
import styleList.utilities.PrefsHelper
import java.R
import java.util.*


class MainRecyclerAdapter(var context: Context,
                          val styleview: List<Stylelist>, val itemListener: StylelistItemListener,var st:String):
        Adapter<MainRecyclerAdapter.viewHolder>() {

    var ratingData:RatingData?=null

    var inflater: LayoutInflater? = null
    //var mdata: ArrayList<StyleType>? = null
    var stylelists: List<Stylelist> =styleview.filter { it.style==st } //스타일 별 보여주기 나눠짐
    //var stylelists:List<Stylelist>?=null




    override fun getItemCount() = stylelists!!.size



    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int):viewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutStyle = PrefsHelper.getItemType(parent.context)
        val layoutId = if (layoutStyle == "grid") {
            R.layout.stylelist_grid_item
        } else {
            R.layout.stylelist_list_item
        }


        val view = inflater.inflate(layoutId, parent, false)


        return viewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val styles = stylelists[position]


        with(holder) {
            nameText?.let {
                it.text = styles.imageFile
                it.contentDescription = styles.imageFile

            }


            grading?.setOnRatingChangeListener(
                    RatingBar.OnRatingChangeListener { RatingCount ->
                        Toast.makeText(
                                context,
                                "the fill star is$RatingCount",
                                Toast.LENGTH_SHORT
                        ).show()
                        styles.rating = RatingCount.toInt()
                        ratingData= RatingData(styles.userId,styles.imageID,styles.rating)
                        RatingUpdate(ratingData!!)
                    }


            )
            grading.setStar(styles!!.rating.toFloat())


            Glide.with(context)
                    .asBitmap()
                    .apply { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)}
                    .apply { RequestOptions.skipMemoryCacheOf(true) }
                    .load("${styles.thumbnailUrl}")
                    .centerCrop()
                    .into(stylelistImage)


            holder.itemView.setOnClickListener {
                itemListener.onMonsterItemClick(styles)
            }
        }
    }

    inner class viewHolder(itemView: View, var a : Int) :
            RecyclerView.ViewHolder(itemView) {

        val nameText = itemView.findViewById<TextView>(R.id.nameText)
        val stylelistImage = itemView.findViewById<ImageView>(R.id.monsterImage)
        val grading = itemView.findViewById<com.hedgehog.ratingbar.RatingBar>(R.id.ratingBar)


    }

    /*
    override fun getItemViewType(position: Int): Int {
        when (mdata?.get(position)?.type) {
            1 -> return 1
            2 -> return 2
            3 -> return 3
            4 -> return 4
        }
        return 1
    }
*/


    interface StylelistItemListener {
        fun onMonsterItemClick(stylelist: Stylelist)
    }


    fun RatingUpdate(ratingData: RatingData){
        val servcie: ServiceApi?= RetrofitClient.getClient().create(ServiceApi::class.java)
        servcie?.updateStyleRating(ratingData)?.enqueue(object : Callback<RatingResponse?> {
            override fun onFailure(call: Call<RatingResponse?>, t: Throwable) {
                print("Fail Load Rating")
            }

            override fun onResponse(call: Call<RatingResponse?>, response: Response<RatingResponse?>) {

                var result: RatingResponse? = response.body()
                if (response.body() != null) {
                    result = response.body()

                } else {
                    Log.v("알림", "rating 값이 없습니다.")

                }
            }

        })
    }

}