package com.androiddevs.shoppinglisttestingyt.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androiddevs.shoppinglisttestingyt.R
import com.androiddevs.shoppinglisttestingyt.databinding.FragmentShoppingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment: Fragment(R.layout.fragment_shopping) {

    val viewModel: ShoppingViewModel by viewModels()
    private lateinit var binding: FragmentShoppingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShoppingBinding.bind(view)

        binding.apply {
            fabAddShoppingItem.setOnClickListener {
                findNavController().navigate(
                    ShoppingFragmentDirections
                        .actionShoppingFragmentToAddShoppingItemFragment()
                )
            }
        }


    }
}