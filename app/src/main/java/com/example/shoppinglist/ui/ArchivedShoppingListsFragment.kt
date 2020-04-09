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
import com.example.shoppinglist.vm.ArchivedShoppingListsFragmentViewModel
import org.koin.android.ext.android.inject

class ArchivedShoppingListsFragment : Fragment() {

    private val viewModel by inject<ArchivedShoppingListsFragmentViewModel>()

    private val shoppingListsListLiveDataObserver =
        Observer<List<ShoppingListModel>> { shoppingListsList ->
            shoppingListsRecyclerViewAdapter.submitList(shoppingListsList)
        }

    private lateinit var binding: ArchivedFragmentShoppingListsBinding

    private lateinit var shoppingListsRecyclerViewAdapter: ShoppingListsRecyclerViewAdapter


    override fun onResume() {
        super.onResume()

        viewModel.fetchArchivedLists()
    }


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

        viewModel.fetchArchivedLists()
        viewModel.archivedShoppingListsListLiveData.observe(
            viewLifecycleOwner,
            shoppingListsListLiveDataObserver
        )
    }

    private fun setupRecyclerAdapter() {
        shoppingListsRecyclerViewAdapter = ShoppingListsRecyclerViewAdapter()
        binding.archivedFragmentShoppingListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.archivedFragmentShoppingListRecyclerView.adapter = shoppingListsRecyclerViewAdapter
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

    private fun navigateToListDetails(item: ShoppingListModel) {
        ShoppingListDetailsActivity.getIntent(requireContext(), item.name, item.id, true).run { startActivity(this) }
    }
}
