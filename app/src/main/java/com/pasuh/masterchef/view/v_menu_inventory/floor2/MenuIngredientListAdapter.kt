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

package com.pasuh.masterchef.view.v_menu_inventory.floor2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pasuh.masterchef.databinding.MenuIngredientListMenuIngredientBinding
import com.pasuh.masterchef.model.orm.MenuIngredientSingle

class MenuIngredientListAdapter(private val onItemClicked: (MenuIngredientSingle) -> Unit) :
    ListAdapter<MenuIngredientSingle, MenuIngredientListAdapter.IngredientViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            MenuIngredientListMenuIngredientBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class IngredientViewHolder(private var binding: MenuIngredientListMenuIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menuIngredient: MenuIngredientSingle) {
            binding.recipeName = menuIngredient.recipeName
            binding.recipeStock = menuIngredient.recipeStock.toString()
            binding.recipeQuantity = menuIngredient.recipeQuantity.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MenuIngredientSingle>() {
            override fun areItemsTheSame(oldMenuIngredient: MenuIngredientSingle, newMenuIngredient: MenuIngredientSingle): Boolean {
                return oldMenuIngredient === newMenuIngredient
            }

            override fun areContentsTheSame(oldMenuIngredient: MenuIngredientSingle, newMenuIngredient: MenuIngredientSingle): Boolean {
                return oldMenuIngredient.recipeName == newMenuIngredient.recipeName
            }
        }
    }
}