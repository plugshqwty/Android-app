package com.example.app3video

import java.io.Serializable

class Item(val id:Int,val image:String,val category:String, val title:String,val desc:String,val text:String, val price:Int):
    Serializable {
}