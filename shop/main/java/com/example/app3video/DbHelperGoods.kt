package com.example.sqliteapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.app3video.Item
import java.io.File
import java.io.FileOutputStream

class DbHelperGoods(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, SCHEMA) {

    companion object {
        private const val DB_NAME = "deviceby.db"
        private const val SCHEMA = 3
        private const val TAG = "DbHelperGoods"

        // Столбцы таблицы goods
        private const val TABLE_GOODS = "goods"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IMAGE = "image"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESC = "desc"
        private const val COLUMN_TEXT = "text"
        private const val COLUMN_PRICE = "price"
    }

    private val dbPath: String = context.getDatabasePath(DB_NAME).path
    private val context: Context = context.applicationContext

    override fun onCreate(db: SQLiteDatabase?) {
        // Не создаем таблицы, так как используем готовую БД из assets
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Логика обновления БД при изменении версии
    }

    /**
     * Инициализирует базу данных, копируя из assets при первом запуске
     */
    fun initializeDatabase(): Boolean {
        return try {
            ensureDatabaseExists()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Database initialization failed", e)
            false
        }
    }

    /**
     * Получает все товары из таблицы goods
     */
    fun getAllItems(): List<Item> {
        if (!checkDatabaseIntegrity()) {
            return emptyList()
        }

        val items = mutableListOf<Item>()
        val db = openReadableDatabase()

        db?.use { database ->
            database.rawQuery(
                "SELECT * FROM $TABLE_GOODS",
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    items.add(extractItemFromCursor(cursor))
                }
            }
        }

        Log.d(TAG, "Loaded ${items.size} items")
        return items
    }

    // ==================== Приватные методы ====================

    private fun ensureDatabaseExists(): Boolean {
        val dbFile = File(dbPath)

        // Удаляем существующую БД для чистого теста
        if (dbFile.exists()) {
            Log.d(TAG, "Deleting existing database...")
            context.deleteDatabase(DB_NAME)
        }

        // Копируем из assets
        return try {
            Log.d(TAG, "Copying database from assets...")

            dbFile.parentFile?.takeIf { !it.exists() }?.mkdirs()

            context.assets.open(DB_NAME).use { inputStream ->
                FileOutputStream(dbPath).use { outputStream ->
                    inputStream.copyTo(outputStream)
                    Log.d(TAG, "Database copied successfully. Size: ${dbFile.length()} bytes")
                    true
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to copy database", e)
            if (dbFile.exists()) dbFile.delete()
            false
        }
    }

    private fun checkDatabaseIntegrity(): Boolean {
        val db = openReadableDatabase() ?: return false

        return try {
            // Проверяем существование таблицы
            val tableExists = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='$TABLE_GOODS'",
                null
            )?.use { it.count > 0 } ?: false

            if (!tableExists) {
                Log.e(TAG, "Table '$TABLE_GOODS' does not exist!")
                logAllTables(db)
            }

            tableExists
        } finally {
            db.close()
        }
    }

    private fun openReadableDatabase(): SQLiteDatabase? {
        return try {
            SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open database", e)
            null
        }
    }

    private fun extractItemFromCursor(cursor: android.database.Cursor): Item {
        return Item(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
            category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
            title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
            desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC)),
            text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT)),
            price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
        )
    }

    private fun logAllTables(db: SQLiteDatabase) {
        Log.d(TAG, "Listing all tables in database:")
        db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)?.use { cursor ->
            while (cursor.moveToNext()) {
                Log.d(TAG, "Table: ${cursor.getString(0)}")
            }
        }
    }
}