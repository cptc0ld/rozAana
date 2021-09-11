package com.application.rozaana.ui.home.childFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.application.rozaana.R
import com.application.rozaana.databinding.FragmentMediaBinding
import com.application.rozaana.model.PexelData
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class MediaFragment : Fragment(){

    companion object {
        const val PAGE_BUNDLE = "pageBundle"

        fun newInstance(pageBundle: MediaPageBundle): MediaFragment {
            val bundle = Bundle()
            bundle.putParcelable(PAGE_BUNDLE, pageBundle)
            val fragment = MediaFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var name: String = ""
    private var url: String = ""
    private var type: String = ""

    lateinit var binding: FragmentMediaBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        getPageArguments()
        initView()
    }

    private fun getPageArguments() {
        arguments?.let { bundle ->
            bundle.getParcelable<MediaPageBundle>(PAGE_BUNDLE)?.let {
                name = it.name ?: ""
                url = it.url
                type = it.desc
            }
        }
    }

    private var player: SimpleExoPlayer? = null
    private fun initializePlayer() {
        player = this.context?.let {
            SimpleExoPlayer.Builder(it)
                .build()
                .also { exoPlayer ->
                    binding.videoView.player = exoPlayer
                    val mediaItem = MediaItem.fromUri("https://player.vimeo.com/external/199433617.m3u8?s=115ec8875069ea6203ddca51dae78cebd934b86e&oauth2_token_id=57447761")
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                }
        }
    }

    fun pauseVideo() {
        if(type == "Video")
            player?.pause()
    }

    fun playVideo() {
        if(type == "Video")
            player?.play()
    }

    private fun initView(){
        binding.label.text = "$name \n $type"

        if(type == "Photo") {
            binding.imageView.visibility = View.VISIBLE
            context?.let { Glide.with(it).load(url).into(binding.imageView) }
        }else if(type == "Video"){
            binding.videoView.visibility = View.VISIBLE
            initializePlayer()
        }
    }
}