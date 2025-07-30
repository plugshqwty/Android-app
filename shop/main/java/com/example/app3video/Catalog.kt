package com.example.app3video

class Catalog(val items: ArrayList<Item> = ArrayList()) {
}

object CatalogSingleton {
    val catalog = Catalog()
}