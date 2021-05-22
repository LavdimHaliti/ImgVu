package com.example.imgvu.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imgvu.R
import com.example.imgvu.databinding.FragmentDetailBinding
import com.example.imgvu.viemodels.ImgVuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailImgFragment : Fragment(R.layout.fragment_detail) {

    private val args by navArgs<DetailImgFragmentArgs>()

    private val viewModel by viewModels<ImgVuViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view)

        binding.imageButton.setOnClickListener {
            binding.optionsNavView.isVisible = true
        }

        binding.apply {
            val photo = args.photo

            Glide.with(this@DetailImgFragment)
                .load(photo.urls.full)
                .error(R.drawable.ic_img_not_found)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        tvCreator.isVisible = true
                        tvDescription.isVisible = photo.description != null
                        return false
                    }
                })
                .into(imageView)

            tvDescription.text = photo.description
            tvCreator.text = photo.user.name

            Glide.with(this@DetailImgFragment)
                .load(photo.user.profile_image.large)
                .error(R.drawable.ic_img_not_found)
                .into(profileImg)

            val uri = Uri.parse(photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            if (photo.user.name == null) {
                tvUnsplashName.isVisible = false
            } else {
                tvUnsplashName.apply {
                    text = photo.user.username
                    setOnClickListener {
                        context.startActivity(intent)
                    }
                    paint.isUnderlineText = true
                }
            }

            if (photo.user.instagram_username == null) {
                tvInstaProfile.isVisible = false
            } else {
                tvInstaProfile.apply {
                    text = photo.user.instagram_username
                    setOnClickListener {
                        context.startActivity(intent)
                    }
                    paint.isUnderlineText = true
                }
            }

            if (photo.user.twitter_username == null) {
                tvTwitterProfile.isVisible = false
            } else {
                tvTwitterProfile.apply {
                    text = photo.user.twitter_username
                    setOnClickListener {
                        context.startActivity(intent)
                    }
                    paint.isUnderlineText = true
                }
            }


            optionsNavView.setOnNavigationItemSelectedListener {
                viewModel.insertImage(photo)
                Toast.makeText(requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_detailImgFragment_to_favoriteImgFragment)
                true
            }
        }

    }

}