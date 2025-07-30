package com.example.app3video

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_account)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val login=UserSession.login
        val textView: TextView = findViewById(R.id.textView_prof)
        textView.text = "Welcome, $login!"

        val btnLogOut: Button = findViewById(R.id.button_log_out)
        btnLogOut.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
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
}