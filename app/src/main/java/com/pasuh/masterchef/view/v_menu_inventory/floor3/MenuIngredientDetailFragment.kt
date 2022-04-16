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

package com.pasuh.masterchef.view.v_menu_inventory.floor3


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.MenuIngredientDetailFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.erd.MenuIngredient
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor3.MenuIngredientDetailViewModel
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor3.MenuIngredientDetailViewModelFactory

class MenuIngredientDetailFragment : Fragment() {
    private val navigationArgs: MenuIngredientDetailFragmentArgs by navArgs()
    private lateinit var menuIngredient: MenuIngredient

    private var _binding: MenuIngredientDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModelMenu: MenuIngredientDetailViewModel by activityViewModels {
        MenuIngredientDetailViewModelFactory(
            (activity?.application as ItemApplication).database.menuIngredientDetailDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.menu_ingredient_detail_fragment, container, false)
        return binding.root
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteIngredient()
            }
            .show()
    }

    private fun deleteIngredient() {
        viewModelMenu.deleteIngredient(menuIngredient)
        val action = MenuIngredientDetailFragmentDirections.actionMenuIngredientDetailFragmentToMenuDetailFragment(
            navigationArgs.itemId
        )
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bind(menuIngredient: MenuIngredient) {
        binding.apply {
            recipeQuantity = menuIngredient.recipeQuantity.toString()
            recipeUpdated = menuIngredient.recipeUpdated

            deleteIngredient.isEnabled = true
            deleteIngredient.visibility = View.VISIBLE
            deleteIngredient.setOnClickListener { showConfirmationDialog() }

            editIngredient.isEnabled = true
            editIngredient.visibility = View.VISIBLE
            editIngredient.setOnClickListener { editIngredient(menuIngredient) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val id = navigationArgs.recipeId
        viewModelMenu.retrieveIngredient(id).observe(this.viewLifecycleOwner) { selectedIngredient ->
            if(selectedIngredient!=null) {
                menuIngredient = selectedIngredient
                bind(menuIngredient)
            }
        }
    }

    private fun editIngredient(menuIngredient: MenuIngredient) {
        val action = MenuIngredientDetailFragmentDirections.actionMenuIngredientDetailFragmentToAddMenuIngredientFragment(
            getString(R.string.edit_fragment_title),
            navigationArgs.itemId,
            -1,
            menuIngredient.id
        )
        this.findNavController().navigate(action)
    }
}