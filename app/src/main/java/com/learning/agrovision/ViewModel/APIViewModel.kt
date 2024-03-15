package com.learning.agrovision.ViewModel

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.learning.agrovision.API.ApiService
import com.learning.agrovision.Activity.FertilizerPredictionActivity
import com.learning.agrovision.Fragment.YeildPridiction
import com.learning.agrovision.Model.FertilizerInputModel
import com.learning.agrovision.Model.FertilizerOutputModel
import com.learning.agrovision.Model.YeidlOutputModel
import com.learning.agrovision.Model.YeildInputModel
import com.learning.agrovision.Utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ResponseCache

class APIViewModel : ViewModel() {

    var result_fertilizer  : MutableLiveData<Response<FertilizerOutputModel>> = MutableLiveData()
    fun Fertilizer(activity: FertilizerPredictionActivity, input: FertilizerInputModel) {
        if (checkForInternet(activity)) {
            val matchApi = Constants.getInstance().create(ApiService::class.java)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = matchApi.fertilizer(input)
                    Log.d("rk",result.toString() )
                    withContext(Dispatchers.Main) {
                        if (result.isSuccessful) {
                            result_fertilizer.value = result
                        } else {
                            val errorBody = result.errorBody()?.string()
                            val errorMessage = parseErrorMessage(errorBody)
                            activity.errorFn(errorMessage ?: "Unknown error")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("rk", "Exception: ${e.message}")
                    activity.errorFn("Registration failed. Please check your internet connection and try again.")
                }
            }
        } else {
            activity.errorFn("Please switch on your internet and retry")
        }
    }
    fun observe_registerNewUser(): LiveData<Response<FertilizerOutputModel>> = result_fertilizer




    var result_yeild  : MutableLiveData<Response<YeidlOutputModel>> = MutableLiveData()
    fun yeild(context: Context,input: YeildInputModel,fragment:YeildPridiction) {
        if (checkForInternet1(context)) {
            val matchApi = Constants.getInstance1().create(ApiService::class.java)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = matchApi.cropYield(input)
                    Log.d("rk",result.toString() )
                    withContext(Dispatchers.Main) {
                        if (result.isSuccessful) {
                            result_yeild.value = result
                        } else {
                            val errorBody = result.errorBody()?.string()
                            val errorMessage = parseErrorMessage(errorBody)
                            fragment.errorFn(errorMessage ?: "Unknown error")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("rk", "Exception: ${e.message}")
                    fragment.errorFn("Registration failed. Please check your internet connection and try again.")
                }
            }
        } else {
            fragment.errorFn("Please switch on your internet and retry")
        }
    }
    fun observe_yeild(): LiveData<Response<YeidlOutputModel>> = result_yeild













    private fun parseErrorMessage(errorBody: String?): String? {
        errorBody?.let {
            try {
                val jsonObject = Gson().fromJson(it, JsonObject::class.java)
                return jsonObject?.get("error")?.asString
            } catch (e: Exception) {
                Log.e("rk", "Error parsing error message: ${e.message}")
            }
        }
        return null
    }
    private fun checkForInternet(context: Activity): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
    private fun checkForInternet1(context: Context): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}