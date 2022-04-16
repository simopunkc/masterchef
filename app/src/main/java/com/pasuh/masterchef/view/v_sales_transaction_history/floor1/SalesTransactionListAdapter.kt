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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pasuh.masterchef.databinding.SalesTransactionListSalesTransactionBinding
import com.pasuh.masterchef.model.orm.SalesTransactionHistory

class SalesTransactionListAdapter(private val onItemClicked: (SalesTransactionHistory) -> Unit) :
    ListAdapter<SalesTransactionHistory, SalesTransactionListAdapter.SalesTransactionViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesTransactionViewHolder {
        return SalesTransactionViewHolder(
            SalesTransactionListSalesTransactionBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: SalesTransactionViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class SalesTransactionViewHolder(private var binding: SalesTransactionListSalesTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sales: SalesTransactionHistory) {
            binding.transactionName = sales.itemName
            binding.transactionDate = sales.tglInserted+" "+sales.jamInserted
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<SalesTransactionHistory>() {
            override fun areItemsTheSame(oldTransaksi: SalesTransactionHistory, newTransaksi: SalesTransactionHistory): Boolean {
                return oldTransaksi === newTransaksi
            }

            override fun areContentsTheSame(oldTransaksi: SalesTransactionHistory, newTransaksi: SalesTransactionHistory): Boolean {
                return oldTransaksi.idMenu == newTransaksi.idMenu
            }
        }
    }
}