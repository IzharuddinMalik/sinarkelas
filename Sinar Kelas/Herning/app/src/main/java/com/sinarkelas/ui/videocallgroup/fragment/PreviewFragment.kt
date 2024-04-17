package com.sinarkelas.ui.videocallgroup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sinarkelas.R
import com.sinarkelas.ui.videocallgroup.videoutils.getDimen

private const val PREVIEW_IMAGE = "preview_image"

class PreviewFragment : Fragment() {

    companion object {
        fun newInstance(imageResourceId: Int): Fragment {
            val previewFragment = PreviewFragment()
            val bundle = Bundle()
            bundle.putInt(PREVIEW_IMAGE, imageResourceId)
            previewFragment.arguments = bundle
            return previewFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_screen_share, container, false)
        Glide.with(requireActivity())
            .load(arguments?.getInt(PREVIEW_IMAGE))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(getDimen(R.dimen.pager_image_width), getDimen(R.dimen.pager_image_height))
            .into(view.findViewById(R.id.image_preview) as ImageView)
        return view
    }
}