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

package com.pasuh.masterchef.view.v_ingredient_inventory.floor1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pasuh.masterchef.databinding.IngredientListIngredientBinding
import com.pasuh.masterchef.model.erd.Ingredient

class IngredientListAdapter(private val onItemClicked: (Ingredient) -> Unit) :
    ListAdapter<Ingredient, IngredientListAdapter.RecipeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            IngredientListIngredientBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class RecipeViewHolder(private var binding: IngredientListIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            binding.resepName = ingredient.recipeName
            binding.resepQuantity = ingredient.quantityInStock.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldIngredient: Ingredient, newIngredient: Ingredient): Boolean {
                return oldIngredient === newIngredient
            }

            override fun areContentsTheSame(oldIngredient: Ingredient, newIngredient: Ingredient): Boolean {
                return oldIngredient.recipeName == newIngredient.recipeName
            }
        }
    }
}