package com.androiddevs.shoppinglisttestingyt.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androiddevs.shoppinglisttestingyt.R
import com.androiddevs.shoppinglisttestingyt.databinding.FragmentAddShoppingItemBinding

class AddShoppingItemFragment: Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel: ShoppingViewModel
    private lateinit var binding: FragmentAddShoppingItemBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddShoppingItemBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        binding.apply {
            ivShoppingImage.setOnClickListener {
                findNavController().navigate(
                    AddShoppingItemFragmentDirections
                        .actionAddShoppingItemFragmentToImagePickFragment()
                )
            }
        }

        val callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}