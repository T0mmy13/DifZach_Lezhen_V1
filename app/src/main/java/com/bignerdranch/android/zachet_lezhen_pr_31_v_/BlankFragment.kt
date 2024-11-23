package com.bignerdranch.android.zachet_lezhen_pr_31_v_

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class BlankFragment : Fragment() {

    private lateinit var tempTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var timeOfDayTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank, container, false)

        tempTextView = view.findViewById(R.id.tempTextView)
        dateTextView = view.findViewById(R.id.dateTextView)
        timeOfDayTextView = view.findViewById(R.id.timeOfDayTextView)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        arguments?.let {
            val temp = it.getString("Temp")
            val date = it.getString("Date")
            val timeOfDay = it.getString("Time_of_day")


            tempTextView.text = temp ?: "No Temp"
            dateTextView.text = date ?: "No Date"
            timeOfDayTextView.text = timeOfDay ?: "No Time"
        }
        return view
    }
}