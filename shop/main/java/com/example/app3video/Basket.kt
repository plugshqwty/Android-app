package com.example.app3video

class Basket (val items: ArrayList<Item> = ArrayList()){
}

object BasketSingleton {
    val basket = Basket()
}
