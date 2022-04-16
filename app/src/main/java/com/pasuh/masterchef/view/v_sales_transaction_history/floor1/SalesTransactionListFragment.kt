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

package com.pasuh.masterchef.view.v_sales_transaction_history.floor1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.SalesTransactionListFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.viewmodel.vm_sales_transaction_history.floor1.SalesTransactionListViewModel
import com.pasuh.masterchef.viewmodel.vm_sales_transaction_history.floor1.SalesTransactionListViewModelFactory

class SalesTransactionListFragment : Fragment() {

    private var _binding: SalesTransactionListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModelSales: SalesTransactionListViewModel by activityViewModels {
        SalesTransactionListViewModelFactory(
            (activity?.application as ItemApplication).database.salesTransactionListDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.sales_transaction_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = SalesTransactionListAdapter {
            val action = SalesTransactionListFragmentDirections.actionSalesTransactionListFragmentToSalesTransactionDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModelSales.allTransactions.observe(this.viewLifecycleOwner) { salesTransactions ->
            if(salesTransactions!=null) {
                salesTransactions.let {
                    adapter.submitList(it)
                }
                if (salesTransactions.count() == 0) {
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