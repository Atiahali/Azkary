package com.misbahah.azkarlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.misbahah.databinding.FragmentAzkarListBinding

class AzkarListFragment : Fragment() {

    private lateinit var binding: FragmentAzkarListBinding

    private val args by navArgs<AzkarListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAzkarListBinding.inflate(inflater, container, false)

        Log.i("TAG", "onCreateView: id ${args.categoryId}")
        Log.i("TAG", "onCreateView: name ${args.categoryName}")

        return binding.root
    }
}