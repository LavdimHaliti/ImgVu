package com.example.imgvu.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.imgvu.R
import com.example.imgvu.adapters.FavImgVuAdapter
import com.example.imgvu.adapters.ImgVuAdapter
import com.example.imgvu.data.ImageDetail
import com.example.imgvu.databinding.FavImagesFragmentBinding
import com.example.imgvu.viemodels.ImgVuViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteImgFragment : Fragment(R.layout.fav_images_fragment) {

    private var _binding: FavImagesFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ImgVuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavImagesFragmentBinding.bind(view)

        val adapter = FavImgVuAdapter()

        binding.apply {
            favImgRv.adapter = adapter
            favImgRv.setHasFixedSize(true)
        }
        viewModel.getAllImages().observe(viewLifecycleOwner) {
            adapter.differ.submitList(it)
        }

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val image = adapter.differ.currentList[position]
                viewModel.deleteImage(image)

                view?.let {
                    Snackbar.make(it, "Image successfully deleted!", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            viewModel.insertImage(image)
                        }
                        show()
                    }
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.favImgRv)
        }

    }

}