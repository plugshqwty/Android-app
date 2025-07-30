package com.example.app3video

import com.example.sqliteapp.DbHelperGoods
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class ItemsActivity : AppCompatActivity() {
    private lateinit var itemsList: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)

        itemsList = findViewById(R.id.itemsList)
        //val items = arrayListOf<Item>()

        CatalogSingleton.catalog.items.clear()

        val dbHelperGoods = DbHelperGoods(this)
        val dbHelper = DbHelperGoods(this)
        if (dbHelper.initializeDatabase()) {
            val items = dbHelper.getAllItems()
            // Работаем с items
        } else {
            Log.e("MainActivity", "Не удалось инициализировать БД")
            Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
        }
        CatalogSingleton.catalog.items.addAll(dbHelperGoods.getAllItems()) // Получаем данные из базы и добавляем в массив

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        // Установка Toolbar как ActionBar
        setSupportActionBar(toolbar)

        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ItemsAdapter(CatalogSingleton.catalog.items, this)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        // Обработка нажатий на элементы меню
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // Обработка элементов меню
                // Например:
                R.id.pods->{
                    val intent=Intent(this, CategoryActivity::class.java)
                    intent.putExtra("CATEGORY_NAME", "Headphones")
                    startActivity(intent)
                }
                R.id.watch->{
                    val intent=Intent(this, CategoryActivity::class.java)
                    intent.putExtra("CATEGORY_NAME", "Watch")
                    startActivity(intent)
                }
                R.id.hairdr->{
                    val intent=Intent(this, CategoryActivity::class.java)
                    intent.putExtra("CATEGORY_NAME", "Hairdryers")
                    startActivity(intent)
                }
                R.id.charge->{
                    val intent=Intent(this, CategoryActivity::class.java)
                    intent.putExtra("CATEGORY_NAME", "Chargers/Powerbanks")
                    startActivity(intent)
                }
                R.id.accsess->{
                    val intent=Intent(this, CategoryActivity::class.java)
                    intent.putExtra("CATEGORY_NAME", "Accessories")
                    startActivity(intent)
                }



            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


        val basketButton: Button = findViewById(R.id.basket_button)

        basketButton.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.button_login)

        loginButton.setOnClickListener {
            val intent = Intent(this, MyAccountActivity::class.java)
            startActivity(intent)
        }

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // Установка кнопки "Назад" в ActionBar для открытия бокового меню
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Открытие бокового меню при нажатии кнопки в ActionBar
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setQueryHint("Type here to search")

        // Установка слушателя для обработки текстовых запросов
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Обработка текста при отправке
                // Ваш код здесь
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val adapter = itemsList.adapter as ItemsAdapter // Получаем адаптер
                adapter.filter(newText) // Вызываем метод фильтрации
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


}