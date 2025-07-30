package com.example.app3video

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPass: EditText = findViewById(R.id.user_pass)
        val button: Button = findViewById(R.id.button_reg)

        val linkToAuth: TextView = findViewById(R.id.link_to_auth)


        button.setOnClickListener {
            Log.d("MainActivity", "Register button clicked")
            val login = userLogin.text.toString().trim()

            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()

            val isLoginValid = validateLogin(login)
            val isEmailValid = validateEmail(email)
            val isPasswordValid = validatePassword(pass)

            if (login.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_LONG).show()
            } else if (!isLoginValid) {
                Toast.makeText(this, "Invalid login format", Toast.LENGTH_LONG).show()
            } else if (!isEmailValid) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_LONG).show()
            } else if (!isPasswordValid) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_LONG).show()
            }
            else{
                val user = User(login, email, pass)
                val db = DbHelper(this, null)
3
                // Проверка, существует ли уже пользователь с таким логином
                if (db.getUserByLogin(login)) {
                    Toast.makeText(this, "A user with this login already exists", Toast.LENGTH_SHORT).show()
                } else {
                    val isRegistered = db.addUser(user) // Здесь получаем Boolean

                    // Обработка результата добавления пользователя в базу данных
                    if (isRegistered) {
                        Toast.makeText(this, "User $login is added", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, ItemsActivity::class.java)
                        startActivity(intent)

                        userLogin.text.clear()
                        userEmail.text.clear()
                        userPass.text.clear()
                    } else {
                        // Ошибка при добавлении пользователя
                        Toast.makeText(this, "Error adding user. Please try again.", Toast.LENGTH_LONG).show()
                    }
                }

            }

        }

        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validateLogin(login: String): Boolean {
        // от 5 до 15 символов
        val loginRegex = Regex("^[a-zA-Z0-9]{5,15}$")
        return loginRegex.matches(login)
    }

    private fun validateEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        return emailRegex.matches(email)
    }

    private fun validatePassword(password: String): Boolean {
         val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
        return passwordRegex.matches(password)
    }
}