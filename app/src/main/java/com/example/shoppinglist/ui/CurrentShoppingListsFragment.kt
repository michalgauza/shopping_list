package com.example.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.adapters.ShoppingListsRecyclerViewAdapter
import com.example.shoppinglist.databinding.CurrentFragmentShoppingListsBinding
import com.example.shoppinglist.models.ShoppingListModel
import com.example.shoppinglist.vm.CurrentShoppingListsFragmentViewModel
import org.koin.android.ext.android.inject

class CurrentShoppingListsFragment : Fragment() {

    private val viewModel by inject<CurrentShoppingListsFragmentViewModel>()

    private lateinit var binding: CurrentFragmentShoppingListsBinding
    private lateinit var shoppingListsRecyclerViewAdapter: ShoppingListsRecyclerViewAdapter

    private val shoppingListsListLiveDataObserver =
        Observer<List<ShoppingListModel>> { shoppingListsList ->
            shoppingListsRecyclerViewAdapter.submitList(shoppingListsList)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentFragmentShoppingListsBinding.inflate(inflater, container, false).apply {
            viewModel = this@CurrentShoppingListsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchCurrentLists()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerAdapter()

        viewModel.fetchCurrentLists()
        binding.currentFragmentShoppingListFab.setOnClickListener {
            setupDialog()
        }

        viewModel.currentShoppingListsListLiveData.observe(
            viewLifecycleOwner,
            shoppingListsListLiveDataObserver
        )
    }

    private fun setupRecyclerAdapter() {
        shoppingListsRecyclerViewAdapter = ShoppingListsRecyclerViewAdapter()
        binding.currentFragmentShoppingListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.currentFragmentShoppingListRecyclerView.adapter = shoppingListsRecyclerViewAdapter
        shoppingListsRecyclerViewAdapter.listDetailsCallback = { listModel ->
            navigateToListDetails(listModel)
        }
        shoppingListsRecyclerViewAdapter.removeListCallback = { listModel ->
            viewModel.deleteList(listModel)
        }
        shoppingListsRecyclerViewAdapter.archiveListCallback = { shoppingListModel, archived ->
            shoppingListModel.isArchived = archived
            viewModel.updateOrInsertList(shoppingListModel)
        }
    }

    private fun setupDialog() {
        val input = EditText(requireContext())
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("New list")
        builder.setView(input)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { _, _ ->
            if (input.text.isEmpty().not()) {
                val newItem = ShoppingListModel(
                    name = input.text.toString(),
                    shoppingItemsList = mutableListOf()
                )
                viewModel.updateOrInsertList(newItem)
            } else {
                input.error = "Please provide name for list."
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun navigateToListDetails(item: ShoppingListModel) {
        ShoppingListDetailsActivity.getIntent(requireContext(), item.name, item.id, false).run { startActivity(this) }
    }
}
