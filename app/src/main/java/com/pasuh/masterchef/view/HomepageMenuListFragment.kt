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

package com.pasuh.masterchef.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.HomepageMenuListFragmentBinding

class HomepageMenuListFragment : Fragment() {

    private var _binding: HomepageMenuListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.homepage_menu_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.menuRecipeInventory.isEnabled = true
        binding.menuRecipeInventory.visibility = View.VISIBLE
        binding.menuRecipeInventory.setOnClickListener {
            this.findNavController().navigate(R.id.action_homepageMenuListFragment_to_ingredientListFragment)
        }
        binding.menuRestockHistory.isEnabled = true
        binding.menuRestockHistory.visibility = View.VISIBLE
        binding.menuRestockHistory.setOnClickListener {
            this.findNavController().navigate(R.id.action_homepageMenuListFragment_to_restockListFragment)
        }
        binding.menuInventory.isEnabled = true
        binding.menuInventory.visibility = View.VISIBLE
        binding.menuInventory.setOnClickListener {
            this.findNavController().navigate(R.id.action_homepageMenuListFragment_to_menuListFragment)
        }
        binding.menuTransactionHistory.isEnabled = true
        binding.menuTransactionHistory.visibility = View.VISIBLE
        binding.menuTransactionHistory.setOnClickListener {
            this.findNavController().navigate(R.id.action_homepageMenuListFragment_to_salesTransactionListFragment)
        }
        binding.menuMaxProfit.isEnabled = true
        binding.menuMaxProfit.visibility = View.VISIBLE
        binding.menuMaxProfit.setOnClickListener {
            this.findNavController().navigate(R.id.action_homepageMenuListFragment_to_profitListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}