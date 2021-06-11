package styleList.ui.detail


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import java.R
import styleList.data.StylelistDao
import styleList.data.StylelistDatabase
import styleList.ui.shared.SharedViewModel
import com.hedgehog.ratingbar.RatingBar.OnRatingChangeListener
import java.databinding.FragmentDetailBinding
import androidx.databinding.ViewDataBinding
import network.RetrofitClient
import network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import styleList.data.RatingData
import styleList.data.RatingResponse

/**
* A simple [Fragment] subclass.
*/
class DetailFragment : Fragment()  {

    private lateinit var viewModel: SharedViewModel
    private lateinit var navController: NavController
    var ratingData:RatingData?=null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(
                requireActivity(), R.id.nav_host
        )

        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        val binding = FragmentDetailBinding.inflate(
                inflater, container, false
        )

        //viewModel?.let{binding.viewModel = it}
        binding.setLifecycleOwner(this)
        //binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val grading=view?.findViewById<com.hedgehog.ratingbar.RatingBar>(R.id.ratingBar)
        /* grading?.setOnRatingBarChangeListener{
                 ratingBar, rating, fromUser ->
             grading.onRatingBarChangeListener
             Toast.makeText(context, "$rating", Toast.LENGTH_SHORT).show()
             //monster.like=rating.toDouble()
             viewModel.selectedMonster.value?.scariness=rating.toInt()
         }*/
        grading?.setStar(viewModel.selectedStylelist.value?.rating?.toFloat()!!)

        //grading?.setStarFillDrawable(resources.getDrawable(R.drawable.heart_full))
        //grading?.setStarEmptyDrawable(resources.getDrawable(R.drawable.heart_empty))

        grading?.setOnRatingChangeListener(
                OnRatingChangeListener{ RatingCount ->

                    Toast.makeText(
                            context,
                            "the fill star is$RatingCount",
                            Toast.LENGTH_SHORT
                    ).show()
                    viewModel?.selectedStylelist?.value?.rating=RatingCount.toInt()
                    var nowStyle=viewModel.selectedStylelist.value
                    ratingData=RatingData(nowStyle?.userId, nowStyle!!.imageID,nowStyle.rating)
                    RatingUpdate(ratingData!!)
                }
        )

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
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
