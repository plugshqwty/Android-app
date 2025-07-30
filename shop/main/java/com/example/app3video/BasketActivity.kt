package com.example.app3video

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class BasketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_basket)
        val basketList: RecyclerView = findViewById(R.id.basketList)
        basketList.layoutManager = LinearLayoutManager(this)
        basketList.adapter = BasketAdapter(BasketSingleton.basket.items, this)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_basket)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)




    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Открытие бокового меню при нажатии кнопки в ActionBar
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Удаляем вызов открытия бокового меню
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}