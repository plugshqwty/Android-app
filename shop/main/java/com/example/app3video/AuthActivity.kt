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

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)

        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        val userLogin: EditText = findViewById(R.id.user_login_auth)
        val userPass: EditText = findViewById(R.id.user_pass_auth)
        val button: Button = findViewById(R.id.button_auth)


        linkToReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if(login==""||pass==""){
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_LONG).show()
            }
            else{
                val db = DbHelper(this,null)
                val isAuth = db.getUser(login, pass)

                if(isAuth){
                    UserSession.login = login
                    Log.d("AuthActivity", "LOOOOOGGGG  $login ")
                    Toast.makeText(this, "User $login is logged in", Toast.LENGTH_LONG).show()
                    userLogin.text.clear()
                    userPass.text.clear()

                    val intent = Intent(this, ItemsActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "User $login is not authorized", Toast.LENGTH_LONG).show()
                }


            }

        }

    }

}