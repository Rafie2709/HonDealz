package com.capstone.project.hondealz.view.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.capstone.project.hondealz.R
import com.capstone.project.hondealz.databinding.ActivityWelcomeBinding
import com.capstone.project.hondealz.view.adapter.ImageSliderAdapter
import com.capstone.project.hondealz.view.login.LoginActivity
import com.capstone.project.hondealz.view.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var sliderAdapter: ImageSliderAdapter
    private lateinit var sliderHandler: Handler
    private lateinit var sliderRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupImageSlider()
        playAnimation()
    }

    private fun setupImageSlider() {
        val images = listOf(
            R.drawable.ic_image_placeholder,
            R.drawable.ic_image_placeholder2,
            R.drawable.ic_image_placeholder3
        )

        sliderAdapter = ImageSliderAdapter(images)
        binding.imageSlider.adapter = sliderAdapter
        binding.indicator.setViewPager(binding.imageSlider)
        binding.imageSlider.isUserInputEnabled = true

        // Otomatis slide
        setupAutoSlide(images)
    }

    private fun setupAutoSlide(images: List<Int>) {
        sliderHandler = Handler(Looper.getMainLooper())
        sliderRunnable = object : Runnable {
            override fun run() {
                val nextItem = (binding.imageSlider.currentItem + 1) % images.size
                binding.imageSlider.setCurrentItem(nextItem, true)
                sliderHandler.postDelayed(this, 3000)
            }
        }
        sliderHandler.postDelayed(sliderRunnable, 3000)

        // Hentikan auto slide saat pengguna menyentuh slider
        binding.imageSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun playAnimation() {
        val title =
            ObjectAnimator.ofFloat(binding.tvTitleWelcome, View.ALPHA, 0f, 1f).setDuration(500)
        val subtitle =
            ObjectAnimator.ofFloat(binding.tvSubtitleWelcome, View.ALPHA, 0f, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 0f, 1f).setDuration(500)
        val register =
            ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 0f, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(login, register)
        }

        AnimatorSet().apply {
            playSequentially(title, subtitle, together)
            start()
        }
    }
}