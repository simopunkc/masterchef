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

package com.pasuh.masterchef.view.v_menu_inventory.floor1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pasuh.masterchef.databinding.MenuListMenuBinding
import com.pasuh.masterchef.model.erd.MenuFood
import com.pasuh.masterchef.model.erd.getFormattedPrice

class MenuListAdapter(private val onItemClicked: (MenuFood) -> Unit) :
    ListAdapter<MenuFood, MenuListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            MenuListMenuBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: MenuListMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menuFood: MenuFood) {
            binding.menuName = menuFood.itemName
            binding.menuPrice = menuFood.getFormattedPrice()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MenuFood>() {
            override fun areItemsTheSame(oldMenuFood: MenuFood, newMenuFood: MenuFood): Boolean {
                return oldMenuFood === newMenuFood
            }

            override fun areContentsTheSame(oldMenuFood: MenuFood, newMenuFood: MenuFood): Boolean {
                return oldMenuFood.itemName == newMenuFood.itemName
            }
        }
    }
}