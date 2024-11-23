package com.bignerdranch.android.zachet_lezhen_pr_31_v_

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class Second_activity : AppCompatActivity() {

    private lateinit var GoNextBut : Button
    private lateinit var StartBut : Button
    private lateinit var cityEditText: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        cityEditText = findViewById(R.id.cityString)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        loadSavedCity()

        saveCityOnClose()

        GoNextBut = findViewById<Button>(R.id.Go_back_button)
        StartBut = findViewById(R.id.button)
        var Temp = ""; var Date = ""; var Time_of_day = "";
        val queue = Volley.newRequestQueue(this)
        val city = cityEditText.text.toString()
        val key = "d4bacc8e2d1d42bf8a243343240410"
        val url = "http://api.weatherapi.com/v1/current.json?key=${key}&q=${city}&aqi=no"

        StartBut.setOnClickListener{
            if (city.isNotEmpty()) {
                val stringRequest = StringRequest(Request.Method.GET, url, { response->

                    val rootView : View = findViewById(R.id.rootview)
                    val snackBar = Snackbar.make(rootView, "", Snackbar.LENGTH_SHORT)
                    val snackBarView = snackBar.view
                    val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.setTextColor(android.graphics.Color.rgb(77, 137,255))
                    val obj = JSONObject(response)
                    var js = obj.getJSONObject("current")
                    Temp = ("Temperature: " + js.getString("temp_c"))
                    js = obj.getJSONObject("location")
                    Date = (js.getString("localtime"))

                    val Dates = Date.split(" ")
                    Date = "Date: "+Dates[0]
                    Time_of_day = "Time of day: "+Dates[1]

                    js = obj.getJSONObject("current")
                    val condition = js.getJSONObject("condition")
                    val iconUrl = condition.getString("icon")

                    snackBar.setText("Данные получены")
                    snackBar.show()

                    val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("Temp", Temp)
                    editor.putString("Date", Date)
                    editor.putString("Time_of_day", Time_of_day)
                    editor.putString("iconUrl", iconUrl)
                    editor.apply()
                }
                    ,
                    {
                        Log.d("MyLog", "Volley error: $it")
                        val rootView : View = findViewById(R.id.rootview)
                        val snackBar = Snackbar.make(rootView, "Данные не получены", Snackbar.LENGTH_SHORT)
                        val snackBarView = snackBar.view
                        val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                        textView.setTextColor(android.graphics.Color.rgb(77, 137,255))
                        snackBar.show()
                    }
                )
                queue.add(stringRequest)
            }
            else{
                val rootView : View = findViewById(R.id.rootview)
                val snackBar = Snackbar.make(rootView, "Неверный ввод", Snackbar.LENGTH_SHORT)
                val snackBarView = snackBar.view
                val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                textView.setTextColor(android.graphics.Color.rgb(77, 137,255))
                snackBar.show()
            }
        }

        GoNextBut.setOnClickListener {
            val intent = Intent(this, First_activity::class.java)
            startActivity(intent)
        }
    }
    private fun loadSavedCity() {
        val savedCity = sharedPreferences.getString("cityString", "")
        cityEditText.setText(savedCity)
    }

    private fun saveCityOnClose() {
        // Использовать onPause(), onStop() или onDestroy() для сохранения
        this.setOnPauseListener {
            val city = cityEditText.text.toString()
            val editor = sharedPreferences.edit()
            editor.putString("cityString", city)
            editor.apply() // не блокирующий вызов
        }
    }

    private fun setOnPauseListener(action: () -> Unit) {
        this.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                action()
            }
        })
    }
}