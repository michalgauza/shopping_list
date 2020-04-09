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
import com.example.shoppinglist.R
import com.example.shoppinglist.adapters.ShoppingListsRecyclerViewAdapter
import com.example.shoppinglist.databinding.CurrentFragmentShoppingListsBinding
import com.example.shoppinglist.models.ShoppingListModel
import com.example.shoppinglist.utils.closeKeyboard
import com.example.shoppinglist.utils.showKeyboard
import com.example.shoppinglist.vm.CurrentListsFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject


class CurrentShoppingListsFragment : Fragment() {

    private val viewModel by inject<CurrentListsFragmentViewModel>()

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
        binding.currentFragmentShoppingListFab.setOnClickListener {
            setupDialog()
        }
        viewModel.currentShoppingListsListLiveData.observe(
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
            archiveShoppingListCallback = { shoppingListModel, archived ->
                shoppingListModel.isArchived = archived
                viewModel.updateOrInsertList(shoppingListModel)
            }
        }
        with(binding.currentFragmentShoppingListRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = shoppingListsRecyclerViewAdapter
        }
    }

    private fun setupDialog() {
        val input = EditText(requireContext()).apply {
            requestFocus()
        }
        showKeyboard(requireContext())
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.add_list_dialog_title))
            setView(input)
            setCancelable(false)
            setPositiveButton(getString(R.string.add_list_dialog_positive_button)) { _, _ ->
                addShoppingList(input)
            }
            setNegativeButton(getString(R.string.add_list_dialog_negative_button)) { dialog, _ ->
                dialog.cancel()
                closeKeyboard(requireContext())
            }
            show()
        }
    }

    private fun addShoppingList(input: EditText) {
        if (input.text.isEmpty().not()) {
            viewModel.updateOrInsertList(
                ShoppingListModel(
                    name = input.text.toString(),
                    shoppingItemsList = mutableListOf()
                )
            )
            closeKeyboard(requireContext())
        } else {
            Snackbar.make(
                binding.currentFragmentShoppingListConstraintLayout,
                getString(R.string.add_list_error_message),
                Snackbar.LENGTH_SHORT
            ).show()
            closeKeyboard(requireContext())
        }
    }

    private fun navigateToListDetails(item: ShoppingListModel) {
        ShoppingListDetailsActivity.getIntent(requireContext(), item.name, item.id, false)
            .run { startActivity(this) }
    }
}
