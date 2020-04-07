package com.example.shoppinglist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.shoppinglist.R
import com.example.shoppinglist.adapters.MyRecyclerViewAdapter
import com.example.shoppinglist.models.ShoppingItemModel
import com.example.shoppinglist.models.ShoppingListModel
import kotlinx.android.synthetic.main.fragment_shopping_lists.*

class ShoppingListsFragment : Fragment() {

    lateinit var myRecyclerViewAdapter: MyRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_lists, container, false)
    }

    var tmp: Int? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ARGUMENTS_KEY) }?.apply {
            tmp = getInt(ARGUMENTS_KEY)
        }


        val tmp2 = listOf(
            ShoppingListModel(
                "biedronka",
                mutableListOf(ShoppingItemModel("cebula", "2", false))
            )
        )

        myRecyclerViewAdapter = MyRecyclerViewAdapter()
        fragment_shopping_list_recycler_view.layoutManager = LinearLayoutManager(context)
        fragment_shopping_list_recycler_view.adapter = myRecyclerViewAdapter
        myRecyclerViewAdapter.updateList(tmp2)

    }

    companion object {
        const val ARGUMENTS_KEY = "argumentsKey"
    }
}
