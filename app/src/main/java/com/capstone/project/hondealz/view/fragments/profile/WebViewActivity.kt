package com.capstone.project.hondealz.view.fragments.profile

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.capstone.project.hondealz.R

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val webView: WebView = findViewById(R.id.webViewS)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true // Aktif JavaScript
        // webView.loadUrl(PDF_URL) // Load URL langsung di sini

        val pdfUrl = intent.getStringExtra(EXTRA_PDF_URL)
        if (pdfUrl != null) {
            webView.loadUrl(pdfUrl) // Load URL dari intent
        }
    }

    companion object {
        const val EXTRA_PDF_URL = "extra_pdf_url"
         private const val PDF_URL = "https://drive.google.com/file/d/12vUeulo2NfY3n8VgvQhfZtAhypEFZ-Qa/view?usp=sharing" // URL PDF Anda
    }
}