package com.example.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.models.ShoppingListModel
import kotlinx.android.synthetic.main.list_card_view.view.*

class MyRecyclerViewAdapter : RecyclerView.Adapter<MyViewHolder>() {

    val shoppingListsList = mutableListOf<ShoppingListModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_card_view, parent, false)
        return MyViewHolder(view)
    }

    fun updateList(newList: List<ShoppingListModel>) {
        shoppingListsList.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = shoppingListsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(shoppingListsList[position])
    }

}

class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: ShoppingListModel){
        view.list_card_view_text_view.text = item.name
    }
}
