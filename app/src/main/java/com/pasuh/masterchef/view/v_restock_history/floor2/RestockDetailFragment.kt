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

package com.pasuh.masterchef.view.v_restock_history.floor2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.RestockDetailFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.orm.RestockHistory
import com.pasuh.masterchef.viewmodel.vm_restock_history.floor2.RestockDetailViewModel
import com.pasuh.masterchef.viewmodel.vm_restock_history.floor2.RestockDetailViewModelFactory

class RestockDetailFragment : Fragment() {
    private val navigationArgs: RestockDetailFragmentArgs by navArgs()
    private lateinit var restock: RestockHistory

    private var _binding: RestockDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RestockDetailViewModel by activityViewModels {
        RestockDetailViewModelFactory(
            (activity?.application as ItemApplication).database.restockDetailDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.restock_detail_fragment, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bind(restock: RestockHistory) {
        binding.apply {
            restockName = restock.recipeName
            restockQuantity = restock.quantityInTransaction.toString()
            restockDate = restock.tglInserted
            restockJam = restock.jamInserted
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val id = navigationArgs.restockId
        viewModel.retrieveRestock(id).observe(this.viewLifecycleOwner) { selectedRestock ->
            if(selectedRestock!=null) {
                restock = selectedRestock
                bind(restock)
            }
        }
    }
}