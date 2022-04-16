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

package com.pasuh.masterchef.view.v_restock_history.floor1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pasuh.masterchef.databinding.RestockListRestockBinding
import com.pasuh.masterchef.model.orm.RestockHistory

class RestockListAdapter(private val onItemClicked: (RestockHistory) -> Unit) :
    ListAdapter<RestockHistory, RestockListAdapter.RestockViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestockViewHolder {
        return RestockViewHolder(
            RestockListRestockBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: RestockViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class RestockViewHolder(private var binding: RestockListRestockBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restock: RestockHistory) {
            binding.restockName = restock.recipeName
            binding.restockDate = restock.tglInserted+" "+restock.jamInserted
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<RestockHistory>() {
            override fun areItemsTheSame(oldRestock: RestockHistory, newRestock: RestockHistory): Boolean {
                return oldRestock === newRestock
            }

            override fun areContentsTheSame(oldRestock: RestockHistory, newRestock: RestockHistory): Boolean {
                return oldRestock.idRecipe == newRestock.idRecipe
            }
        }
    }
}