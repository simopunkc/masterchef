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

package com.pasuh.masterchef.view.v_maximum_profit.floor1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pasuh.masterchef.databinding.ProfitListProfitBinding
import com.pasuh.masterchef.model.orm.MaxProfit

class ProfitListAdapter(private val onItemClicked: (MaxProfit) -> Unit) :
    ListAdapter<MaxProfit, ProfitListAdapter.ProfitViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfitViewHolder {
        return ProfitViewHolder(
            ProfitListProfitBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ProfitViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ProfitViewHolder(private var binding: ProfitListProfitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(maxprofit: MaxProfit) {
            binding.apply {
                binding.maxName = maxprofit.menu
                binding.maxPrice = maxprofit.profit.toString()
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MaxProfit>() {
            override fun areItemsTheSame(oldProfit: MaxProfit, newProfit: MaxProfit): Boolean {
                return oldProfit === newProfit
            }

            override fun areContentsTheSame(oldProfit: MaxProfit, newProfit: MaxProfit): Boolean {
                return oldProfit.profit == newProfit.profit
            }
        }
    }
}