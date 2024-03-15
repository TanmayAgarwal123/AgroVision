package com.learning.agrovision.Activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.learning.agrovision.Model.FertilizerInputModel
import com.learning.agrovision.R
import com.learning.agrovision.ViewModel.APIViewModel
import com.learning.agrovision.databinding.ActivityFertilizerPredictionBinding

class FertilizerPredictionActivity : BaseActivity() {
    lateinit var viewModel:APIViewModel
    var tempValue:Int=-1
    var humidityValue:Int=-1
    var moistureValue:Int=-1
    var soilValue:Int=-1
    var cropValue:Int=-1
    var nitrogenValue:Int=-1
    var potassiumValue:Int=-1
    var phospherousValue:Int=-1
    lateinit var singleValueTypePopUp:Dialog
    lateinit var binding:ActivityFertilizerPredictionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityFertilizerPredictionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[APIViewModel::class.java]
        val soilTypeMap = mapOf(
            "Black" to 0,
            "Clayey" to 1,
            "Loamy" to 2,
            "Red" to 3,
            "Sandy" to 4
        )
        val cropTypeMap = mapOf(
            "Barley" to 0,
            "Cotton" to 1,
            "Ground Nuts" to 2,
            "Maize" to 3,
            "Millets" to 4,
            "Oil seeds" to 5,
            "Paddy" to 6,
            "Pulses" to 7,
            "Sugarcane" to 8,
            "Tobacco" to 9,
            "Wheat" to 10
        )
        val fertilizerTypeMap = mapOf(
            "10-26-26" to 0,
            "14-35-14" to 1,
            "17-17-17" to 2,
            "20-20" to 3,
            "28-28" to 4,
            "DAP" to 5,
            "Urea" to 6
        )
        val cropNamesList: ArrayList<String> = ArrayList(cropTypeMap.keys)
        val soilList: ArrayList<String> = ArrayList(soilTypeMap.keys)
        val fertilizerList: ArrayList<String> = ArrayList(fertilizerTypeMap.keys)

        binding.tempValue.setOnClickListener {
            singleValueTypePopUp("Please enter the temperature value", tempValue) { newValue ->
                tempValue = newValue
                Log.d("rk", tempValue.toString())
                binding.tempValue.text=tempValue.toString()
            }
        }
        binding.humidityValue.setOnClickListener {
            singleValueTypePopUp("Please enter the humidity value", tempValue) { newValue ->
                humidityValue = newValue
                Log.d("rk", humidityValue.toString())
                binding.humidityValue.text=humidityValue.toString()
            }
        }
        binding.moistureValue.setOnClickListener {
            singleValueTypePopUp("Please enter the moisture value", tempValue) { newValue ->
                moistureValue = newValue
                Log.d("rk", moistureValue.toString())
                binding.moistureValue.text=moistureValue.toString()
            }
        }
        binding.nitrogenValue.setOnClickListener {
            singleValueTypePopUp("Please enter the nitrogen value", tempValue) { newValue ->
                nitrogenValue = newValue
                Log.d("rk", nitrogenValue.toString())
                binding.nitrogenValue.text=nitrogenValue.toString()
            }
        }
        binding.potassiumValue.setOnClickListener {
            singleValueTypePopUp("Please enter the potassium value", tempValue) { newValue ->
                potassiumValue = newValue
                Log.d("rk", potassiumValue.toString())
                binding.potassiumValue.text=potassiumValue.toString()
            }
        }
        binding.phosphorusValue.setOnClickListener {
            singleValueTypePopUp("Please enter the phosphorous value", tempValue) { newValue ->
                phospherousValue = newValue
                Log.d("rk", phospherousValue.toString())
                binding.phosphorusValue.text=phospherousValue.toString()
            }
        }
        binding.soilValue.setOnClickListener {
            singleValueChoosePopUp("Please choose one soil value",soilList) { newValue ->
                soilValue = newValue
                Log.d("rk", soilValue.toString())
                binding.soilValue.text=soilList[soilValue]
            }
        }
        binding.cropValue.setOnClickListener {
            singleValueChoosePopUp("Please choose one crop value",cropNamesList) { newValue ->
                cropValue = newValue
                Log.d("rk", cropValue.toString())
                binding.cropValue.text=cropNamesList[cropValue]
            }
        }
        try {
            binding.calculateBtn.setOnClickListener {
                Log.d("rk","hii")
                if(tempValue!=-1 && humidityValue!=-1 && moistureValue!=-1 && soilValue!=-1 && cropValue!=-1 && nitrogenValue!=-1 && potassiumValue !=-1 && phospherousValue!=-1)
                {
                    showProgressbar(this)
                    viewModel.Fertilizer(this, FertilizerInputModel(tempValue,humidityValue,moistureValue,soilValue,cropValue,nitrogenValue,potassiumValue,phospherousValue));
                }
                else
                {
                    Toast(this,"Please enter all parameters")
                }
            }
        }catch (e:Exception)
        {
            Log.d("rk",e.message.toString())
        }


        viewModel.observe_registerNewUser().observe(this, Observer { res->
            cancelProgressbar()
            if(res.isSuccessful)
            {
                res.body()!!.prediction?.get(0)?.let { Log.d("rk", it.toString()) }
                binding.ans.text= "The recommended fertilizer is ${fertilizerList[res.body()!!.prediction?.get(0)!!]}"
            }
        })
    }

    private fun singleValueTypePopUp(t: String, value: Int, callback: (Int) -> Unit) {
        try {
            singleValueTypePopUp = Dialog(this)
            val view: View = LayoutInflater.from(this).inflate(R.layout.single_value_type_popup, null)
            val submitOtpButton = view.findViewById<TextView>(R.id.Enter_otp_btn)
            val editTextValue = view.findViewById<EditText>(R.id.single_value_type_ed)
            view.findViewById<TextView>(R.id.single_value_type_tv).text = t
            singleValueTypePopUp.setContentView(view)
            singleValueTypePopUp.setCanceledOnTouchOutside(false)
            val window = singleValueTypePopUp.window
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            window?.setGravity(Gravity.BOTTOM)
            submitOtpButton.setOnClickListener {
                val newValue = editTextValue.text.toString().toInt()
                callback(newValue)
                singleValueTypePopUp.dismiss()
            }
            singleValueTypePopUp.show()
        } catch (e: Exception) {
            Log.d("rk", e.message.toString())
        }
    }

    fun singleValueChoosePopUp(title:String,lis:ArrayList<String>,callback: (Int) -> Unit) {
        var checkedItem = 0
        var selectedIndexForParamter = 0
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(title)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice)
        for(item in lis)
        {
            arrayAdapter.add(item)
        }
        builder.setSingleChoiceItems(arrayAdapter, checkedItem) { dialog, which ->
            selectedIndexForParamter = which
        }
        builder.setPositiveButton("Select") { dialog, which ->
            Log.d("rk",selectedIndexForParamter.toString())
            try {
                callback(selectedIndexForParamter)
            } catch (e: Exception) {
                Log.d("rk", e.message.toString())
            }
        }
        builder.setNegativeButton("Cancel", null)
        val dialog: android.app.AlertDialog? = builder.create()
        if (dialog != null) {
            dialog.show()
        }
    }

    fun errorFn(message:String)
    {
        cancelProgressbar()
        Toast(this,message)
    }
}