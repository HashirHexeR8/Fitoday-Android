package com.active.fitoday.ui.BodyProportionsFragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitoday.R
import com.active.fitoday.databinding.LayoutIntroViewPagerFragmentBinding
import com.active.fitoday.ui.util.Enum
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class IntroViewPagerFragment(pageType: Enum.IntroPagerEnum) : Fragment() {

    private lateinit var binding: LayoutIntroViewPagerFragmentBinding

    val mPageType: Enum.IntroPagerEnum = pageType

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutIntroViewPagerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (mPageType) {
            Enum.IntroPagerEnum.enjoy -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.tvIntroInfoText.text = Html.fromHtml(getString(R.string.intro_enjoy_string), Html.FROM_HTML_MODE_LEGACY)
                }
                else {
                    binding.tvIntroInfoText.text = Html.fromHtml(getString(R.string.intro_enjoy_string))
                }
                Glide.with(this).load(resources.getDrawable(R.drawable.intro_image_enjoy)).placeholder(R.drawable.intro_image_enjoy).fitCenter().into(binding.ivIntroInfoImage)
            }
            Enum.IntroPagerEnum.idk -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.tvIntroInfoText.text = Html.fromHtml(getString(R.string.intro_idk_string), Html.FROM_HTML_MODE_LEGACY)
                }
                else {
                    binding.tvIntroInfoText.text = Html.fromHtml(getString(R.string.intro_idk_string))
                }
                Glide.with(this).load(resources.getDrawable(R.drawable.intro_image_idk)).placeholder(R.drawable.intro_image_idk).fitCenter().into(binding.ivIntroInfoImage)
            }
            Enum.IntroPagerEnum.run -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.tvIntroInfoText.text = Html.fromHtml(getString(R.string.intro_run_string), Html.FROM_HTML_MODE_LEGACY)
                }
                else {
                    binding.tvIntroInfoText.text = Html.fromHtml(getString(R.string.intro_run_string))
                }
                Glide.with(this).load(resources.getDrawable(R.drawable.intro_image_run)).placeholder(R.drawable.intro_image_run).fitCenter().into(binding.ivIntroInfoImage)
            }
        }
    }
}