package com.learning.agrovision.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.learning.agrovision.Fragment.CropPrediction
import com.learning.agrovision.Fragment.Model
import com.learning.agrovision.Fragment.PricePrediction
import com.learning.agrovision.Fragment.YeildPridiction
import com.learning.agrovision.R
import com.learning.agrovision.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(CropPrediction())

//        binding.bottomNavigationView.background = null
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.crop_prediction -> replaceFragment(CropPrediction())
                R.id.crop_yield_prediction -> replaceFragment(YeildPridiction())
                R.id.crop_price_pridiction -> replaceFragment(PricePrediction())
                R.id.Model -> replaceFragment(Model())
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

}