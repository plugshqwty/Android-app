package com.example.app3video

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?)
    :SQLiteOpenHelper(context, "app", factory, 3) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query ="CREATE TABLE users (id INT PRIMARY KEY, login TEXT UNIQUE, email TEXT, pass TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user:User): Boolean{
        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("pass", user.pass)

        val db = this.writableDatabase
        return try {
            db.insertOrThrow("users", null, values)
            true // Успех
        } catch (e: SQLiteConstraintException) {
            Log.e("DbHelper", "Логин занят: ${e.message}")
            false // Логин уже существует
        } catch (e: Exception) {
            Log.e("DbHelper", "Ошибка при добавлении пользователя: ${e.message}")
            false // Неудача
        } finally {
            db.close()
        }
    }

    fun getUser(login:String, pass: String): Boolean{
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass' ", null)
        return result.moveToFirst()    //найдена запись или нет
        db.close()
    }

    fun getUserByLogin(login: String): Boolean {
        val db = this.readableDatabase
        // Используем параметризованный запрос для предотвращения SQL инъекций
        val result = db.rawQuery("SELECT * FROM users WHERE login = ?", arrayOf(login))

        // Проверяем, найдена ли запись с указанным логином
        val userExists = result.moveToFirst()
        result.close() // Закрываем курсор
        return userExists
    }

}