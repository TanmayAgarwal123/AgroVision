package com.learning.agrovision.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.agrovision.Activity.FertilizerPredictionActivity
import com.learning.agrovision.databinding.FragmentModelBinding


class Model : Fragment() {
    lateinit var binding:FragmentModelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= FragmentModelBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding.fertilizerPrediction.setOnClickListener {
            startActivity(Intent(requireActivity(),FertilizerPredictionActivity::class.java))
       }
        return binding.root
    }
}