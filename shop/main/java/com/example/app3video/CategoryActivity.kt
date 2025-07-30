package com.example.app3video

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class CategoryActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category)
        val itemsList: RecyclerView = findViewById(R.id.itemsListCat)
        val items = arrayListOf<Item>()
        items.clear()

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarCat)
        setSupportActionBar(toolbar)

        if(items.isEmpty()) {
            val categoryName = intent.getStringExtra("CATEGORY_NAME")
            for (i in CatalogSingleton.catalog.items.indices) {
                if (CatalogSingleton.catalog.items[i].category == categoryName) {

                    items.add(CatalogSingleton.catalog.items[i])
                }

            }
        }

        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ItemsAdapter(items, this)

        drawerLayout = findViewById(R.id.drawer_layout)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val basketButton: Button = findViewById(R.id.basket_button1)

        basketButton.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }
        val loginButton: Button = findViewById(R.id.button_login1)

        loginButton.setOnClickListener {
            val intent = Intent(this, MyAccountActivity::class.java)
            startActivity(intent)
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

}