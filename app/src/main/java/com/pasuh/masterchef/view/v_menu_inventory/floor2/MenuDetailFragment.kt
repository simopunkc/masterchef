/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pasuh.masterchef.view.v_menu_inventory.floor2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.MenuDetailFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.erd.MenuFood
import com.pasuh.masterchef.model.erd.getFormattedPrice
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor2.MenuDetailViewModel
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor2.MenuDetailViewModelFactory

class MenuDetailFragment : Fragment() {
    private val navigationArgs: MenuDetailFragmentArgs by navArgs()
    private lateinit var menuFood: MenuFood

    private var _binding: MenuDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuDetailViewModel by activityViewModels {
        MenuDetailViewModelFactory(
            (activity?.application as ItemApplication).database.menuDetailDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.menu_detail_fragment, container, false)
        return binding.root
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem() {
        viewModel.deleteItem(menuFood)
        val action = MenuDetailFragmentDirections.actionMenuDetailFragmentToMenuListFragment()
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bind(menuFood: MenuFood) {
        binding.apply {
            menuName = menuFood.itemName
            menuPrice = menuFood.getFormattedPrice()

            sellItem.isEnabled = true
            sellItem.visibility = View.VISIBLE
            sellItem.setOnClickListener { goToSellQuantity(menuFood) }

            deleteItem.isEnabled = true
            deleteItem.visibility = View.VISIBLE
            deleteItem.setOnClickListener { showConfirmationDialog() }

            editMenu.isEnabled = true
            editMenu.visibility = View.VISIBLE
            editMenu.setOnClickListener { editItem(menuFood) }

            addIngredient.isEnabled = true
            addIngredient.visibility = View.VISIBLE
            addIngredient.setOnClickListener { addIngredient(menuFood) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            if(selectedItem!=null) {
                menuFood = selectedItem
                bind(menuFood)
            }
        }

        val adapter = MenuIngredientListAdapter {
            val action =  MenuDetailFragmentDirections.actionMenuDetailFragmentToMenuIngredientDetailFragment(
                id,
                it.id
            )
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModel.retrieveListIngredient(id).observe(this.viewLifecycleOwner) { Ingredients ->
            Ingredients?.let {
                adapter.submitList(it)
            }
        }
        viewModel.getTotalRecipe().observe(this.viewLifecycleOwner) { TotalIngredient ->
            if(TotalIngredient!=null && TotalIngredient.totalCount > 0) {
                binding.addIngredient.visibility = View.VISIBLE
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun editItem(menuFood: MenuFood) {
        val action = MenuDetailFragmentDirections.actionMenuDetailFragmentToAddMenuFragment(
            getString(R.string.edit_fragment_title),
            menuFood.id
        )
        this.findNavController().navigate(action)
    }

    private fun addIngredient(menuFood: MenuFood) {
        val action = MenuDetailFragmentDirections.actionMenuDetailFragmentToDropdownListFragment(
            getString(R.string.choose_recipe),
            menuFood.id
        )
        this.findNavController().navigate(action)
    }

    private fun goToSellQuantity(menuFood: MenuFood){
        val action = MenuDetailFragmentDirections.actionMenuDetailFragmentToSellMenuFragment(
            getString(R.string.quantity),
            menuFood.id
        )
        this.findNavController().navigate(action)
    }
}