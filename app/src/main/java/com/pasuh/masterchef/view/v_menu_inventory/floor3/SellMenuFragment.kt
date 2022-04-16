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
import com.pasuh.masterchef.databinding.MenuSellFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.erd.Ingredient
import com.pasuh.masterchef.model.erd.MenuFood
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor3.MenuSellViewModel
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor3.MenuSellViewModelFactory

class SellMenuFragment : Fragment() {

    private val viewModel: MenuSellViewModel by activityViewModels {
        MenuSellViewModelFactory(
            (activity?.application as ItemApplication).database
                .menuSellDao(),
            (activity?.application as ItemApplication).database
                .menuDetailDao()
        )
    }
    lateinit var menuFood: MenuFood

    private val navigationArgs: SellMenuFragmentArgs by navArgs()

    private var _binding: MenuSellFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var listStock: List<Int>
    private lateinit var listBuy: List<Int>
    private lateinit var listRecipe: List<Int>
    private val newStock: MutableList<Int> = mutableListOf()
    private lateinit var listUpdated: List<Ingredient>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.menu_sell_fragment, container, false)
        return binding.root
    }

    private fun isSellValid(): Boolean {
        return viewModel.isSellValid(
            binding.itemSellQuantity.text.toString()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val id = navigationArgs.itemId

        if (id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                if(selectedItem!=null) {
                    menuFood = selectedItem
                }
            }
        }
        viewModel.retrieveSingleIngredients(id).observe(this.viewLifecycleOwner) { selectedItemRecipe ->
            if(selectedItemRecipe!=null) {
                listStock = selectedItemRecipe.stockQuantity.split(",").map { it.toInt() }
                listBuy = selectedItemRecipe.recipeQuantity.split(",").map { it.toInt() }
                listRecipe = selectedItemRecipe.idRecipe.split(",").map { it.toInt() }
                viewModel.getListTransaksiRecipe(listRecipe, listRecipe.count())
                    .observe(this.viewLifecycleOwner) { selectAllRecipe ->
                        if(selectAllRecipe!=null) {
                            listUpdated = selectAllRecipe
                        }
                    }
                bind(menuFood)
            }else{
                binding.empty.isEnabled = true
                binding.empty.visibility = View.VISIBLE
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

    private fun bind(menuFood: MenuFood) {
        binding.empty.isEnabled = false
        binding.empty.visibility = View.GONE
        binding.itemNameLabel.isEnabled = true
        binding.itemNameLabel.visibility = View.VISIBLE
        binding.sellAction.isEnabled = true
        binding.sellAction.visibility = View.VISIBLE
        binding.sellAction.setOnClickListener { addNewTransaction(menuFood) }
    }

    private fun checkStock() : Boolean {
        var status = true
        val quantity = binding.itemSellQuantity.text.toString().toInt()
        for (i in 0 until listStock.count()) {
            val temp = listStock[i] - (listBuy[i]*quantity)
            if(temp < 0){
                status = false
                break
            }else{
                newStock.add(temp)
            }
        }
        return status
    }

    private fun addNewTransaction(menuFood: MenuFood){
        if(checkStock() && isSellValid()){
            for ((index, value) in listUpdated.withIndex()) {
                viewModel.updateRecipeTransaction(value.id,value.recipeName,newStock[index].toString())
            }
            val quantity = binding.itemSellQuantity.text.toString().toInt()
            viewModel.addNewTransaksi(menuFood,quantity)
            val action = SellMenuFragmentDirections.actionSellMenuFragmentToMenuDetailFragment(menuFood.id)
            findNavController().navigate(action)
        }else{
            Toast.makeText((activity?.application as ItemApplication), "Invalid Input! Maybe out of stock", Toast.LENGTH_SHORT).show()
        }
    }
}