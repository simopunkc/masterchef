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

package com.pasuh.masterchef.view.v_ingredient_inventory.floor2


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
import com.pasuh.masterchef.databinding.IngredientDetailFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.erd.Ingredient
import com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor2.IngredientDetailViewModel
import com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor2.IngredientDetailViewModelFactory

class IngredientDetailFragment : Fragment() {
    private val navigationArgs: IngredientDetailFragmentArgs by navArgs()
    private lateinit var ingredient: Ingredient

    private var _binding: IngredientDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: IngredientDetailViewModel by activityViewModels {
        IngredientDetailViewModelFactory(
            (activity?.application as ItemApplication).database.ingredientDetailDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.ingredient_detail_fragment, container, false)
        return binding.root
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteRecipe()
            }
            .show()
    }

    private fun deleteRecipe() {
        viewModel.deleteRecipe(ingredient)
        val action = IngredientDetailFragmentDirections.actionIngredientDetailFragmentToIngredientListFragment()
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bind(ingredient: Ingredient) {
        binding.apply {
            resepName = ingredient.recipeName
            resepQuantity = ingredient.quantityInStock.toString()

            addRecipe.isEnabled = true
            addRecipe.visibility = View.VISIBLE
            addRecipe.setOnClickListener { addRecipe(ingredient) }

            deleteRecipe.isEnabled = true
            deleteRecipe.visibility = View.VISIBLE
            deleteRecipe.setOnClickListener { showConfirmationDialog() }

            editRecipe.isEnabled = true
            editRecipe.visibility = View.VISIBLE
            editRecipe.setOnClickListener { editRecipe(ingredient) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val id = navigationArgs.recipeId
        viewModel.retrieveRecipe(id).observe(this.viewLifecycleOwner) { selectedRecipe ->
            if(selectedRecipe!=null) {
                ingredient = selectedRecipe
                bind(ingredient)
            }
        }
    }

    private fun addRecipe(ingredient: Ingredient) {
        val action = IngredientDetailFragmentDirections.actionIngredientDetailFragmentToAddRestockFragment(
            getString(R.string.increase_stock),
            ingredient.id
        )
        this.findNavController().navigate(action)
    }

    private fun editRecipe(ingredient: Ingredient) {
        val action = IngredientDetailFragmentDirections.actionIngredientDetailFragmentToAddIngredientFragment(
            getString(R.string.edit_fragment_title),
            ingredient.id
        )
        this.findNavController().navigate(action)
    }
}