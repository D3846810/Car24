package uk.ac.tees.mad.d3846810.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import uk.ac.tees.mad.d3846810.screens.SplashScreen

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
        FirebaseMessaging.getInstance().token.addOnCompleteListener{
            Log.d("TAG_TOKEN", "onCreate: ${it.result.toString()}")
        }
        setContent {
            SplashScreen(modifier = Modifier)
        }

        if (Firebase.auth.currentUser != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashActivity, HomeMainActivity::class.java))
                finish()
            }, 2000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                finish()
            }, 2000)
        }
    }
}