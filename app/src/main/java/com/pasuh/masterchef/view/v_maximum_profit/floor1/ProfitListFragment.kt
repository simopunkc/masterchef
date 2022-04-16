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

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import com.google.gson.Gson
import com.pasuh.masterchef.R
import com.pasuh.masterchef.databinding.ProfitListFragmentBinding
import com.pasuh.masterchef.model.application.ItemApplication
import com.pasuh.masterchef.model.orm.MaxProfit
import com.pasuh.masterchef.model.util.TAG_OUTPUT
import com.pasuh.masterchef.model.worker.Notification
import com.pasuh.masterchef.viewmodel.vm_maximum_profit.floor1.ProfitListViewModel
import com.pasuh.masterchef.viewmodel.vm_maximum_profit.floor1.ProfitListViewModelFactory
import java.util.*

class ProfitListFragment : Fragment() {

    private var _binding: ProfitListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfitListViewModel by activityViewModels {
        ProfitListViewModelFactory(
            (activity?.application as ItemApplication).database.profitListDao(),
            (activity?.application as ItemApplication)
        )
    }

    private val gson = Gson()
    private var maxC: Int = 1
    private var maximumProfit: Double = 0.0
    private var keyHash = HashMap<Int, MaxProfit>()
    private var currRunning = HashMap<UUID, Boolean>()
    private var currFinished = HashMap<UUID, Boolean>()
    private var minC: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.profit_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = ProfitListAdapter{}
        binding.recyclerView.adapter = adapter
        viewModel.allProfits.observe(this.viewLifecycleOwner) { profits ->
            profits?.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        viewModel.cancelWork()
        hideKeyboard()
        viewModel.clearAllProfits()
        viewModel.getTotalItem().observe(this.viewLifecycleOwner) { totalItem ->
            if(totalItem!=null) {
                maxC = totalItem.totalCount
                if(maxC>0) {
                    binding.minCombination.setText("1", TextView.BufferType.SPANNABLE)
                    binding.maxCombination.setText(
                        totalItem.totalCount.toString(),
                        TextView.BufferType.SPANNABLE
                    )
                    binding.empty.isEnabled = false
                    binding.empty.visibility = View.GONE
                    binding.scrollviewProfitList.isEnabled = true
                    binding.scrollviewProfitList.visibility = View.VISIBLE
                }else{
                    binding.empty.isEnabled = true
                    binding.empty.visibility = View.VISIBLE
                }
            }
        }
        showWorkFinished()

        // worker
        binding.goButton.setOnClickListener {
            minC = binding.minCombination.text.toString().toInt()
            if (viewModel.isNumberValid(minC) && minC >= 1 && minC <= maxC) {
                binding.goButton.isEnabled = false
                hideKeyboard()
                viewModel.clearAllProfits()
                viewModel.getListIngredient()
                    .observe(this.viewLifecycleOwner) { listIngredients ->
                        if(listIngredients!=null) {
                            viewModel.applyBlur(maxC, minC, gson.toJson(listIngredients))
                        }
                    }
            }else{
                Toast.makeText((activity?.application as ItemApplication), "Input can only be in the range of 1 to $maxC", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.outputWorkInfos.observe(this.viewLifecycleOwner, workInfosObserver())
        binding.cancelButton.setOnClickListener {
            binding.cancelButton.isEnabled = false
            viewModel.cancelWork()
            viewModel.clearAllProfits()
            binding.cancelButton.isEnabled = true
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        currRunning.clear()
        currFinished.clear()
        maximumProfit = 0.0
        keyHash.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelWork()
        viewModel.clearAllProfits()
        hideKeyboard()
        _binding = null
    }

    private fun showWorkInProgress() {
        with(binding) {
            progressBar.visibility = View.VISIBLE

            cancelButton.isEnabled = true
            cancelButton.visibility = View.VISIBLE

            goButton.isEnabled = false
            goButton.visibility = View.GONE
        }
    }

    private fun showWorkFinished() {
        with(binding) {
            progressBar.visibility = View.GONE

            cancelButton.isEnabled = false
            cancelButton.visibility = View.GONE

            goButton.isEnabled = true
            goButton.visibility = View.VISIBLE
        }
    }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }
            listOfWorkInfo.forEach { workInfo ->
                if (workInfo.state == WorkInfo.State.RUNNING
                    && !currRunning.containsKey(workInfo.id)
                    && !currFinished.containsKey(workInfo.id)
                ) {
                    currRunning[workInfo.id] = true
                    currFinished[workInfo.id] = false
                }
            }
            if (listOfWorkInfo.count()>0 && listOfWorkInfo[0].state.isFinished){
                if(listOfWorkInfo[0].state==WorkInfo.State.SUCCEEDED && currRunning.count()>(maxC-minC)) {
                    var count = 0
                    listOfWorkInfo.forEach { workInfo ->
                        if (workInfo.state.isFinished
                            && workInfo.state == WorkInfo.State.SUCCEEDED
                            && currRunning.containsKey(workInfo.id)
                        ) {
                            if (!currFinished.getValue(workInfo.id)) {
                                val result = workInfo.outputData.getString(TAG_OUTPUT)
                                if (!result.isNullOrEmpty()) {
                                    val obj = gson.fromJson(result, MaxProfit::class.java)
                                    currFinished[workInfo.id] = true
                                    if (obj.profit > maximumProfit) {
                                        maximumProfit = obj.profit
                                        keyHash[0]=obj
                                        viewModel.addAllProfits(obj)
                                    }
                                }
                            }
                            count += 1
                        }
                    }
                    if (currFinished.count() == count) {
                        if(keyHash.count()>0) {
                            Notification().makeStatusNotification(
                                "List menu: ${keyHash.getValue(0).menu}, Max profit: ${
                                    keyHash.getValue(
                                        0
                                    ).profit
                                }", (activity?.application as ItemApplication)
                            )
                            hideKeyboard()
                        }else{
                            Toast.makeText((activity?.application as ItemApplication), "No result, check your stock of ingredients", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                showWorkFinished()
            } else {
                showWorkInProgress()
            }
        }
    }
}