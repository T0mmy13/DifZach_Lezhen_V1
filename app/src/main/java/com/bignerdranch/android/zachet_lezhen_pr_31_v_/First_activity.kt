package com.bignerdranch.android.zachet_lezhen_pr_31_v_

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso

class First_activity : AppCompatActivity() {

    private lateinit var Image : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val temp = sharedPreferences.getString("Temp", "TempIsEmpty")
        val date = sharedPreferences.getString("Date", "DateIsEmpty")
        val timeOfDay = sharedPreferences.getString("Time_of_day", "TimeIsEmpty")
        var iconUrl = sharedPreferences.getString("iconUrl", "UrlIsEmpty")

        Image = findViewById(R.id.imageView)


        iconUrl = "https://cdn.weatherapi.com/weather/64x64/day/116.png"
        Picasso.get()
            .load(iconUrl)
            .into(Image)

        loadBlankFragment(temp, date, timeOfDay, iconUrl)


    }
    private fun loadBlankFragment(temp: String?, date: String?, timeOfDay: String?, iconUrl: String?) {
        val blankFragment = BlankFragment()
        val bundle = Bundle()
        bundle.putString("Temp", temp)
        bundle.putString("Date", date)
        bundle.putString("Time_of_day", timeOfDay)
        bundle.putString("IconUrl", iconUrl)
        blankFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.Fragment_layout, blankFragment)
            .addToBackStack(null)
            .commit()
    }


}