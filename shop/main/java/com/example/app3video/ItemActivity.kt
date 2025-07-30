package com.example.app3video

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.navigation.NavigationView
import com.razorpay.Checkout

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item)

        val title:TextView = findViewById(R.id.item_list_title_one)
        val text:TextView = findViewById(R.id.item_list_text)


        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_item)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title.text = intent.getStringExtra("itemTitle")
        text.text = intent.getStringExtra("itemText")

        val item = intent.getSerializableExtra(Item::class.java.simpleName) as? Item
        if (item != null) {
            Log.d("TAG", "item is sent: ID = ${item.id}, Image = ${item.image}, Title = ${item.title}, Desc = ${item.desc}, Text = ${item.text}, Price = ${item.price}")
        }
        val basBtn: Button = findViewById(R.id.button_to_basket)
        basBtn.setOnClickListener {
            if (item != null) {
                BasketSingleton.basket.items.add(item)
                Toast.makeText(this, "${item.title} is added to cart", Toast.LENGTH_LONG).show()
                Log.d("TAG", "item is sent!!!!!!!!!")
            } else {
                Log.d("TAG", "item is null, cannot send!")
            }
        }
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