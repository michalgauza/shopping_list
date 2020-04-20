package com.example.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.adapters.ShoppingListsRecyclerViewAdapter
import com.example.shoppinglist.databinding.ArchivedFragmentShoppingListsBinding
import com.example.shoppinglist.models.ShoppingListModel
import com.example.shoppinglist.vm.ArchivedListsFragmentViewModel
import org.koin.android.ext.android.inject

class ArchivedShoppingListsFragment : Fragment() {

    private val viewModel by inject<ArchivedListsFragmentViewModel>()

    private val shoppingListsListLiveDataObserver =
        Observer<List<ShoppingListModel>> { shoppingListsList ->
            shoppingListsRecyclerViewAdapter.submitList(shoppingListsList)
        }

    private lateinit var binding: ArchivedFragmentShoppingListsBinding

    private lateinit var shoppingListsRecyclerViewAdapter: ShoppingListsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ArchivedFragmentShoppingListsBinding.inflate(inflater, container, false).apply {
            viewModel = this@ArchivedShoppingListsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerAdapter()

        viewModel.archivedShoppingListsListLiveData.observe(
            viewLifecycleOwner,
            shoppingListsListLiveDataObserver
        )
    }

    private fun setupRecyclerAdapter() {
        shoppingListsRecyclerViewAdapter = ShoppingListsRecyclerViewAdapter().apply {
            shoppingListDetailsCallback = { listModel ->
                navigateToListDetails(listModel)
            }
            removeShoppingListCallback = { listModel ->
                viewModel.deleteList(listModel)
            }
            archiveShoppingListCallback =
                { shoppingListModel, archived ->
                    shoppingListModel.isArchived = archived
                    viewModel.updateOrInsertList(shoppingListModel)
                }
        }

        with(binding.archivedFragmentShoppingListRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = shoppingListsRecyclerViewAdapter
        }
    }

    private fun navigateToListDetails(shoppingList: ShoppingListModel) {
        ShoppingListDetailsActivity.getIntent(
            requireContext(),
            shoppingList.name,
            shoppingList.id,
            true
        ).run { startActivity(this) }
    }
}
