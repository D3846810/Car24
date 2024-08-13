package uk.ac.tees.mad.d3846810.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import uk.ac.tees.mad.d3846810.screens.PrivacyPolicyScreen

class PolicyActivity : AppCompatActivity() {
    private val url =
        "https://www.app-privacy-policy.com/live.php?token=C4jTR9wDjYdYogikIJ23JCf1ljdgtaja"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrivacyPolicyScreen(url = url) {
                startActivity(Intent(this, HomeMainActivity::class.java))
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startActivity(Intent(this@PolicyActivity, HomeMainActivity::class.java))
            }

        })
    }
}