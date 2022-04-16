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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pasuh.masterchef.databinding.MenuDropdownIngredientListMenuDropdownIngredientBinding
import com.pasuh.masterchef.model.erd.Ingredient

class MenuDropdownIngredientListAdapter(private val onItemClicked: (Ingredient) -> Unit) :
    ListAdapter<Ingredient, MenuDropdownIngredientListAdapter.DropdownViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropdownViewHolder {
        return DropdownViewHolder(
            MenuDropdownIngredientListMenuDropdownIngredientBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: DropdownViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class DropdownViewHolder(private var binding: MenuDropdownIngredientListMenuDropdownIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            binding.recipeName = ingredient.recipeName
            binding.recipeId = ingredient.id.toString()
            binding.recipeStock = ingredient.quantityInStock.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldDropdown: Ingredient, newDropdown: Ingredient): Boolean {
                return oldDropdown === newDropdown
            }

            override fun areContentsTheSame(oldDropdown: Ingredient, newDropdown: Ingredient): Boolean {
                return oldDropdown.recipeName == newDropdown.recipeName
            }
        }
    }
}