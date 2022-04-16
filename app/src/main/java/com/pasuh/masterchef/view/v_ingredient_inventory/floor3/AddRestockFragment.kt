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
package com.pasuh.masterchef.view.v_ingredient_inventory.floor3

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.RestockAddFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor3.RestockAddViewModel
import com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor3.RestockAddViewModelFactory


class AddRestockFragment : Fragment() {

    private val navigationArgs: AddRestockFragmentArgs by navArgs()

    private val viewModel: RestockAddViewModel by activityViewModels {
        RestockAddViewModelFactory(
            (activity?.application as ItemApplication).database
                .restockAddDao(),
            (activity?.application as ItemApplication).database
                .ingredientAddDao(),
            (activity?.application as ItemApplication).database
                .ingredientDetailDao()
        )
    }

    private var _binding: RestockAddFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.restock_add_fragment, container, false)
        return binding.root
    }

    private fun isRestockValid(): Boolean {
        return viewModel.isIngredientValid(
            binding.restockQuantity.text.toString()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val id = navigationArgs.recipeId
        if (id > 0) {
            binding.saveAction.isEnabled = true
            binding.saveAction.visibility = View.VISIBLE
            binding.saveAction.setOnClickListener { updateIngredient(id) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

    private fun updateIngredient(idRecipe: Int) {
        if (isRestockValid()) {
            val stock = this.binding.restockQuantity.text.toString().toInt()
            viewModel.retrieveRecipe(idRecipe).observe(this.viewLifecycleOwner) { selectedIngredient ->
                if(selectedIngredient!=null) {
                    val updateCount = selectedIngredient.quantityInStock + stock
                    viewModel.updateRecipeTransaction(
                        selectedIngredient.id,
                        selectedIngredient.recipeName,
                        updateCount.toString()
                    )
                    viewModel.addNewRestock(selectedIngredient, stock)
                    val action =
                        AddRestockFragmentDirections.actionAddRestockFragmentToIngredientDetailFragment(
                            idRecipe
                        )
                    findNavController().navigate(action)
                }
            }
        }else{
            Toast.makeText((activity?.application as ItemApplication), "Invalid Input!", Toast.LENGTH_SHORT).show()
        }
    }
}