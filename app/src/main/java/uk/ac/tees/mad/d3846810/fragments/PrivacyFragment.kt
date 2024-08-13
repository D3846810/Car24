package uk.ac.tees.mad.d3846810.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import uk.ac.tees.mad.d3846810.activities.HomeMainActivity
import uk.ac.tees.mad.d3846810.screens.PrivacyPolicyScreen

class PrivacyFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private val url =
        "https://www.app-privacy-policy.com/live.php?token=C4jTR9wDjYdYogikIJ23JCf1ljdgtaja"

    //    private val binding by lazy { FragmentPrivacyBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

//        return binding.root
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    private fun setComposeContent() {
        composeView.setContent {
            PrivacyPolicyScreen(url = url) {
                startActivity(Intent(requireActivity(), HomeMainActivity::class.java))
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setComposeContent()

//        val webView = binding.web
//        webView.settings.javaScriptEnabled = true
//        webView.webViewClient = WebViewClient()

//        if (url.isNotBlank()) {
//            try {
//                webView.loadUrl(url)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        } else {
//            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//        }

//        binding.btPolicyBack.setOnClickListener {
//            startActivity(Intent(requireActivity(), HomeMainActivity::class.java))
//        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    startActivity(Intent(requireActivity(), HomeMainActivity::class.java))
                }

            })
    }

}