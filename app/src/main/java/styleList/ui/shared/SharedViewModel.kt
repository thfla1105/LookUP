package styleList.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import styleList.data.Stylelist
import styleList.data.StylelistRepository

class SharedViewModel(val app: Application) : AndroidViewModel(app) {
    private val dataRepo = StylelistRepository(app)
    val stylelistData = dataRepo.stylelistData

    val selectedStylelist = MutableLiveData<Stylelist>()
    val activityTitle = MutableLiveData<String>()

    init {
        updateActivityTitle()
        //updateGrade()

    }

   /* fun setGrade(selectedMonster: Stylelist,ratingBar: RatingBar){
        RatingBar.OnRatingChangeListener { RatingCount: Float ->
            this.selectedMonster.value?.scariness=RatingCount.toInt()
        }
    }*/


    fun refreshData() {
        dataRepo.refreshDataFromWeb()
        //updateGrade()
    }

    fun updateActivityTitle() {
        val signature =
            PreferenceManager.getDefaultSharedPreferences(app)
                .getString("signature", "스타일별 선호도 ")
        activityTitle.value = " $signature"

    }





}


