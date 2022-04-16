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
import com.pasuh.masterchef.databinding.RestockListFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.viewmodel.vm_restock_history.floor1.RestockListViewModel
import com.pasuh.masterchef.viewmodel.vm_restock_history.floor1.RestockListViewModelFactory

class RestockListFragment : Fragment() {

    private var _binding: RestockListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RestockListViewModel by activityViewModels {
        RestockListViewModelFactory(
            (activity?.application as ItemApplication).database.restockListDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.restock_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = RestockListAdapter {
            val action = RestockListFragmentDirections.actionRestockListFragmentToRestockDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModel.allRestocks.observe(this.viewLifecycleOwner) { restocks ->
            if(restocks!=null) {
                restocks.let {
                    adapter.submitList(it)
                }
                if (restocks.count() == 0) {
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