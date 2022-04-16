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
import com.pasuh.masterchef.databinding.MenuAddFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.erd.MenuFood
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor2.MenuAddViewModel
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor2.MenuAddViewModelFactory

class AddMenuFragment : Fragment() {

    private val viewModel: MenuAddViewModel by activityViewModels {
        MenuAddViewModelFactory(
            (activity?.application as ItemApplication).database
                .menuAddDao()
        )
    }
    lateinit var menuFood: MenuFood

    private val navigationArgs: MenuDetailFragmentArgs by navArgs()

    private var _binding: MenuAddFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.menu_add_fragment, container, false)
        return binding.root
    }

    private fun isMenuValid(): Boolean {
        return viewModel.isMenuValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString()
        )
    }

    private fun addNewItem() {
        if (isMenuValid()) {
            viewModel.addNewItem(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString()
            )
            val action = AddMenuFragmentDirections.actionAddMenuFragmentToMenuListFragment()
            findNavController().navigate(action)
        }else{
            Toast.makeText((activity?.application as ItemApplication), "Invalid Input!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.saveAction.isEnabled = true
        binding.saveAction.visibility = View.VISIBLE
        val id = navigationArgs.itemId
        if (id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                if(selectedItem!=null) {
                    menuFood = selectedItem
                    bind(menuFood)
                }
            }
        } else {
            binding.saveAction.setOnClickListener { addNewItem() }
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
        val price = "%.2f".format(menuFood.itemPrice)
        binding.apply {
            itemName.setText(menuFood.itemName, TextView.BufferType.SPANNABLE)
            itemPrice.setText(price, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateItem() }
        }
    }

    private fun updateItem() {
        if (isMenuValid()) {
            viewModel.updateItem(
                this.navigationArgs.itemId,
                this.binding.itemName.text.toString(),
                this.binding.itemPrice.text.toString()
            )
            val action = AddMenuFragmentDirections.actionAddMenuFragmentToMenuListFragment()
            findNavController().navigate(action)
        }else{
            Toast.makeText((activity?.application as ItemApplication), "Invalid Input!", Toast.LENGTH_SHORT).show()
        }
    }
}