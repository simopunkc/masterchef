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
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.MenuDropdownIngredientListFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor3.DropdownListViewModel
import com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor3.DropdownListViewModelFactory

class MenuDropdownIngredientListFragment : Fragment() {

    private val navigationArgs: MenuDropdownIngredientListFragmentArgs by navArgs()

    private var _binding: MenuDropdownIngredientListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DropdownListViewModel by activityViewModels {
        DropdownListViewModelFactory(
            (activity?.application as ItemApplication).database.dropdownListDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.menu_dropdown_ingredient_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val menuId = navigationArgs.itemId
        val adapter = MenuDropdownIngredientListAdapter {
            val action =  MenuDropdownIngredientListFragmentDirections.actionDropdownListFragmentToAddMenuIngredientFragment(
                getString(R.string.add_fragment_title),
                menuId,
                it.id,
                -1
            )
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModel.retrieveDropdownIngredient(menuId).observe(this.viewLifecycleOwner) { items ->
            if(items!=null) {
                items.let {
                    adapter.submitList(it)
                }
                if (items.count() == 0) {
                    binding.empty.isEnabled = true
                    binding.empty.visibility = View.VISIBLE
                } else {
                    binding.empty.isEnabled = false
                    binding.empty.visibility = View.GONE
                }
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
