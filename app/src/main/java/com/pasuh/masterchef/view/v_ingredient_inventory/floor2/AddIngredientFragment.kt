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

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.IngredientAddFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.erd.Ingredient
import com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor2.IngredientAddViewModel
import com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor2.IngredientAddViewModelFactory

class AddIngredientFragment : Fragment() {

    private val viewModelMenu: IngredientAddViewModel by activityViewModels {
        IngredientAddViewModelFactory(
            (activity?.application as ItemApplication).database
                .ingredientAddDao(),
            (activity?.application as ItemApplication).database
                .ingredientDetailDao()
        )
    }
    lateinit var ingredient: Ingredient

    private val navigationArgs: IngredientDetailFragmentArgs by navArgs()

    private var _binding: IngredientAddFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.ingredient_add_fragment, container, false)
        return binding.root
    }

    private fun isRecipeValid(): Boolean {
        return viewModelMenu.isRecipeValid(
            binding.recipeName.text.toString(),
            binding.recipeCount.text.toString()
        )
    }

    private fun addNewRecipe() {
        if (isRecipeValid()) {
            viewModelMenu.addNewRecipe(
                binding.recipeName.text.toString(),
                binding.recipeCount.text.toString(),
            )
            val action = AddIngredientFragmentDirections.actionAddIngredientFragmentToIngredientListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.saveAction.isEnabled = true
        binding.saveAction.visibility = View.VISIBLE
        val id = navigationArgs.recipeId
        if (id > 0) {
            viewModelMenu.retrieveRecipe(id).observe(this.viewLifecycleOwner) { selectedRecipe ->
                if(selectedRecipe!=null) {
                    ingredient = selectedRecipe
                    bind(ingredient)
                }
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewRecipe()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

    private fun bind(ingredient: Ingredient) {
        binding.apply {
            recipeName.setText(ingredient.recipeName, TextView.BufferType.SPANNABLE)
            recipeCount.setText(ingredient.quantityInStock.toString(), TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateRecipe() }
        }
    }

    private fun updateRecipe() {
        if (isRecipeValid()) {
            viewModelMenu.updateRecipe(
                this.navigationArgs.recipeId,
                this.binding.recipeName.text.toString(),
                this.binding.recipeCount.text.toString()
            )
            val action = AddIngredientFragmentDirections.actionAddIngredientFragmentToIngredientDetailFragment(navigationArgs.recipeId)
            findNavController().navigate(action)
        }else{
            Toast.makeText((activity?.application as ItemApplication), "Invalid Input!", Toast.LENGTH_SHORT).show()
        }
    }
}
