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

package com.pasuh.masterchef.view.v_sales_transaction_history.floor2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.SalesTransactionDetailFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.orm.SalesTransactionHistory
import com.pasuh.masterchef.viewmodel.vm_sales_transaction_history.floor2.SalesTransactionDetailViewModel
import com.pasuh.masterchef.viewmodel.vm_sales_transaction_history.floor2.SalesTransactionDetailViewModelFactory

class SalesTransactionDetailFragment : Fragment() {
    private val navigationArgs: SalesTransactionDetailFragmentArgs by navArgs()
    private lateinit var transaction: SalesTransactionHistory

    private var _binding: SalesTransactionDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SalesTransactionDetailViewModel by activityViewModels {
        SalesTransactionDetailViewModelFactory(
            (activity?.application as ItemApplication).database.salesTransactionDetailDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.sales_transaction_detail_fragment, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bind(sales: SalesTransactionHistory) {
        binding.apply {
            transactionName = sales.itemName
            transactionQuantity = sales.quantityInTransaction.toString()
            transactionDate = sales.tglInserted
            transactionJam = sales.jamInserted
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val id = navigationArgs.transaksiId
        viewModel.retrieveTransaksi(id).observe(this.viewLifecycleOwner) { selectedTransaction ->
            if(selectedTransaction!=null) {
                transaction = selectedTransaction
                bind(transaction)
            }
        }
    }
}