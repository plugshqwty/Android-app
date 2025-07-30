package com.example.app3video

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app3video.ItemsAdapter.MyViewHolder

class BasketAdapter(val items: List<Item>, var context: Context): RecyclerView.Adapter<BasketAdapter.MyViewHolder>() {

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.image_in_basket)
        val title: TextView = view.findViewById(R.id.title_in_basket)    //по 1 в списке
        val price: TextView = view.findViewById(R.id.price_in_basket)
        val desc: TextView = view.findViewById(R.id.desc_in_basket)
        val btn: Button = view.findViewById(R.id.delete_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_basket, parent, false)    //xml
        return com.example.app3video.BasketAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: BasketAdapter.MyViewHolder, position: Int) {
        holder.title.text=items[items.count()-1-position].title
        holder.desc.text=items[items.count()-1-position].desc
        holder.price.text=items[items.count()-1-position].price.toString()

        val imageId = context.resources.getIdentifier(
            items[items.count()-1-position].image,
            "drawable",
            context.packageName
        )

        holder.image.setImageResource(imageId)

        holder.btn.setOnClickListener {
            val item = items[items.count()-1-position]
            BasketSingleton.basket.items.remove(item)

            val intent = Intent(context, BasketActivity::class.java)

            context.startActivity(intent)
        }
    }


}