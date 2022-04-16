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
package com.pasuh.masterchef.view.v_menu_inventory.floor4

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
import com.pasuh.masterchef.databinding.MenuIngredientAddFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.erd.MenuIngredient
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor4.MenuIngredientAddViewModel
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor4.MenuIngredientAddViewModelFactory
import java.text.DateFormat
import java.util.*


class AddMenuIngredientFragment : Fragment() {

    private val navigationArgs: AddMenuIngredientFragmentArgs by navArgs()

    private val viewModelMenu: MenuIngredientAddViewModel by activityViewModels {
        MenuIngredientAddViewModelFactory(
            (activity?.application as ItemApplication).database
                .menuIngredientAddDao()
        )
    }
    private lateinit var menuIngredient: MenuIngredient

    private var _binding: MenuIngredientAddFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.menu_ingredient_add_fragment, container, false)
        return binding.root
    }

    private fun isIngredientValid(): Boolean {
        return viewModelMenu.isIngredientValid(
            binding.ingredientQuantity.text.toString()
        )
    }

    private fun addNewIngredient() {
        if (isIngredientValid()) {
            viewModelMenu.addNewIngredient(
                navigationArgs.itemId,
                navigationArgs.recipeId,
                this.binding.ingredientQuantity.text.toString().toInt(),
                DateFormat.getDateInstance().format(Date())
            )
            val action = AddMenuIngredientFragmentDirections.actionAddMenuIngredientFragmentToMenuDetailFragment(navigationArgs.itemId)
            findNavController().navigate(action)
        }else{
            Toast.makeText((activity?.application as ItemApplication), "Invalid Input!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val id = navigationArgs.ingredientId
        if (id > 0) {
            viewModelMenu.retrieveIngredient(id).observe(this.viewLifecycleOwner) { selectedIngredient ->
                if(selectedIngredient!=null) {
                    menuIngredient = selectedIngredient
                    bind(menuIngredient)
                }
            }
        } else {
            binding.saveAction.isEnabled = true
            binding.saveAction.visibility = View.VISIBLE
            binding.saveAction.setOnClickListener { addNewIngredient() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

    private fun bind(menuIngredient: MenuIngredient) {
        binding.apply {
            ingredientQuantity.setText(menuIngredient.recipeQuantity.toString(), TextView.BufferType.SPANNABLE)
            saveAction.isEnabled = true
            saveAction.visibility = View.VISIBLE
            saveAction.setOnClickListener { updateIngredient() }
        }
    }

    private fun updateIngredient() {
        if (isIngredientValid()) {
            viewModelMenu.updateIngredient(
                menuIngredient.id,
                menuIngredient.idMenu,
                menuIngredient.idRecipe,
                this.binding.ingredientQuantity.text.toString().toInt(),
                DateFormat.getDateInstance().format(Date())
            )
            val action = AddMenuIngredientFragmentDirections.actionAddMenuIngredientFragmentToMenuDetailFragment(navigationArgs.itemId)
            findNavController().navigate(action)
        }else{
            Toast.makeText((activity?.application as ItemApplication), "Invalid Input!", Toast.LENGTH_SHORT).show()
        }
    }
}