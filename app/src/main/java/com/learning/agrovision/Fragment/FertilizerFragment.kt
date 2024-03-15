package com.learning.agrovision.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.agrovision.R


class FertilizerFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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




        return inflater.inflate(R.layout.fragment_fertilizer, container, false)
    }
}