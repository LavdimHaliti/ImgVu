package com.example.imgvu.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.imgvu.R
import com.example.imgvu.adapters.ImgLoadStateAdapter
import com.example.imgvu.adapters.ImgVuAdapter
import com.example.imgvu.data.ImageDetail
import com.example.imgvu.databinding.FragmentSearchImgBinding
import com.example.imgvu.viemodels.ImgVuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImgSearchFragment : Fragment(R.layout.fragment_search_img),
    ImgVuAdapter.OnItemClickListener {

    private val viewModel by viewModels<ImgVuViewModel>()

    private var _binding: FragmentSearchImgBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchImgBinding.bind(view)

        val adapter = ImgVuAdapter(this)


        binding.searchNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.favorite_menu -> findNavController().navigate(R.id.action_imgSearchFragment_to_favoriteImgFragment)
            }
            true
        }

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ImgLoadStateAdapter { adapter.retry() },
                footer = ImgLoadStateAdapter { adapter.retry() }
            )
            btnRetry.setOnClickListener { adapter.retry() }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                searchProgressbar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvConnectionError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    tvNoResultsFound.isVisible = true
                } else {
                    tvNoResultsFound.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onItemClicked(photo: ImageDetail) {
        val action = ImgSearchFragmentDirections.actionImgSearchFragmentToDetailImgFragment(photo)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}