package styleList.data

import Cookie.SaveSharedPreference
import Login_Main.activity.LoginActivity
import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import network.RetrofitClient
import network.ServiceApi
import styleList.LOG_TAG
import styleList.utilities.FileHelper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class StylelistRepository(val app: Application) {

    //private lateinit var stylelistData:MutableLiveData<List<Stylelist>>
    val stylelistData = MutableLiveData<List<Stylelist>>()
    val stylelistDao = StylelistDatabase.getDatabase(app)
            .stylelistDao()
    var userId=SaveSharedPreference.getString(app.applicationContext, "ID")





    init {
        CoroutineScope(Dispatchers.IO).launch {
            val data = stylelistDao.getAll()
            callWebService()
            /*if (data.isEmpty()) {
                Toast.makeText(app, "Load Data", Toast.LENGTH_LONG).show()
                //callWebService()
            } else {
                stylelistData.postValue(data)
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "Using local data", Toast.LENGTH_LONG).show()
                }
            }*/
        }
    }

    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Using remote data", Toast.LENGTH_LONG).show()
            }
            Log.i(LOG_TAG, "Calling web service")
            /*val retrofit = Retrofit.Builder()
                    .baseUrl(WEB_SERVICE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
            val service = retrofit.create(StylelistService::class.java)*/


            val service = RetrofitClient.getClient().create(ServiceApi::class.java)
            val serviceData = service.getStylelistData(userId).body() ?: emptyList()
            stylelistData.postValue(serviceData)
            stylelistDao.deleteAll()
            stylelistDao.insertStylelists(serviceData)

        }
    }

    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    fun refreshDataFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

    private fun saveDataToCache(stylelistData: List<Stylelist>) {
        if (ContextCompat.checkSelfPermission(
                        app,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
        ) {
            stylelistDao.update(stylelists = stylelistData)
            val moshi = Moshi.Builder().build()
            val listType = Types.newParameterizedType(List::class.java, Stylelist::class.java)
            val adapter: JsonAdapter<List<Stylelist>> = moshi.adapter(listType)
            val json = adapter.toJson(stylelistData)
            FileHelper.saveTextToFile(app, json)
        }


    }

    private fun readDataFromCache(): List<Stylelist> {
        val json = FileHelper.readTextFile(app)
        if (json == null) {
            return emptyList()
        }
        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, Stylelist::class.java)
        val adapter: JsonAdapter<List<Stylelist>> = moshi.adapter(listType)
        return adapter.fromJson(json) ?: emptyList()
    }
}






