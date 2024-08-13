package uk.ac.tees.mad.d3846810.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.R.string
import uk.ac.tees.mad.d3846810.model.UserModel
import uk.ac.tees.mad.d3846810.preference.SharedPref
import uk.ac.tees.mad.d3846810.screens.auth_screens.ForgetPasswordScreen
import uk.ac.tees.mad.d3846810.screens.auth_screens.LoginScreen
import uk.ac.tees.mad.d3846810.screens.auth_screens.SignUpScreen
import uk.ac.tees.mad.d3846810.screens.auth_screens.WelcomeScreen
import uk.ac.tees.mad.d3846810.screens.dialogs.ProgressDialog
import uk.ac.tees.mad.d3846810.screens.navigation.NavItem
import uk.ac.tees.mad.d3846810.theme.c60
import uk.ac.tees.mad.d3846810.utils.Constants
import uk.ac.tees.mad.d3846810.utils.Utils

class AuthActivity : AppCompatActivity() {
    //    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }
    private lateinit var navController: NavHostController
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
//    private lateinit var progress: AlertDialog
    private lateinit var googleSignInClient: GoogleSignInClient
    val welcome = NavItem(title = "Welcome")
    val login = NavItem(title = "Login")
    val signUp = NavItem(title = "SignUp")
    val forget = NavItem(title = "Forget")
    var showProgress by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setContent {
            navController = rememberNavController()

            Surface(
                modifier = Modifier.fillMaxSize(), color = c60
            ) {
                Scaffold { paddingValues ->
                    NavHost(navController = navController, startDestination = welcome.title) {
                        composable(welcome.title) {
                            Box(modifier = Modifier.padding(paddingValues)) {
                                WelcomeScreen(onLogin = {
                                    navController.navigate(login.title)
                                }, onSignUp = {
                                    navController.navigate(signUp.title)
                                })
                                if (showProgress) {
                                    ProgressDialog(Modifier.align(Alignment.Center))
                                }
                            }
                        }
                        composable(login.title) {
                            Box(modifier = Modifier.padding(paddingValues)) {
                                LoginScreen(buttonLoginTxt = "Login", onLogin = { email, pass ->
                                    login(email, pass)
                                }, onLoginWithGoogle = {
                                    signInWithGoogle()
                                }, onCreateAccount = {
                                    navController.navigate(signUp.title)
                                }, onForgetPassword = {
                                    navController.navigate(forget.title)
                                })
                                if (showProgress) {
                                    ProgressDialog(Modifier.align(Alignment.Center))
                                }
                            }
                        }
                        composable(signUp.title) {
                            Box(modifier = Modifier.padding(paddingValues)) {
                                SignUpScreen(onCreateAccount = { userModel, pass, confirmPass ->
                                    registerNewUser(userModel, pass, confirmPass)
                                }, onLogin = {
                                    navController.navigate(login.title)
                                }, onBackPress = {
                                    navController.navigateUp()
                                })
                                if (showProgress) {
                                    ProgressDialog(Modifier.align(Alignment.Center))
                                }
                            }
                        }
                        composable(forget.title) {
                            Box(modifier = Modifier.padding(paddingValues)) {
                                ForgetPasswordScreen(onConfirmEmail = {
                                    if (it.isEmpty()) {
                                        Utils.showMessage(
                                            this@AuthActivity, "Please Enter Recovery Email"
                                        )
                                    } else {
                                        isEmailRegistered(it)
                                    }
                                }, onBackPress = {
                                    navController.navigateUp()
                                })
                                if (showProgress) {
                                    ProgressDialog(Modifier.align(Alignment.Center))
                                }
                            }
                        }
                    }
                }
            }

        }
        database = FirebaseDatabase.getInstance()
        askNotificationPermission()
    }

    private fun isEmailRegistered(email: String) {
//        progress.show()
        showProgress= true
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val signInMethods = task.result?.signInMethods
                val isRegistered = !signInMethods.isNullOrEmpty()
                if (isRegistered) {
                    sendPasswordResetEmail(email)
                } else {
//                    progress.dismiss()
                    showProgress= false

                    Utils.showMessage(this, "Gmail Not Registered")
                }
            } else {
//                progress.dismiss()
                showProgress= false
                Utils.showMessage(this, "Something went wrong")
            }
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
//                progress.dismiss()
                showProgress= false
                Utils.showMessage(this, "Check Email")
                navController.navigate(login.title)
            } else {
                showProgress= false
//                progress.dismiss()
                Utils.showMessage(this, "Something went wrong")
            }
        }

    }

    private fun initialize() {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
//        progress = Utils.showLoading(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(string.web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun registerNewUser(userModel: UserModel, pass: String, confirmPass: String) {
        if (pass != confirmPass) {
            Utils.showMessage(this, "Password Not Match")
        } else {
            createUser(userModel, pass, confirmPass)
        }
    }

    private fun createUser(
        userModel: UserModel, pass: String, confirmPass: String
    ) {
//        progress.show()
        showProgress= true

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            val token = task.result
            auth.createUserWithEmailAndPassword(userModel.email, confirmPass).addOnSuccessListener {
                val userId = it.user!!.uid
                val user = userModel.copy(userId = userId, token = token)
                val usersReference = database.getReference(Constants.USER_REF)
                usersReference.child(userId).setValue(user).addOnSuccessListener {
                    SharedPref.saveUserData(this, user)
//                    progress.dismiss()
                    showProgress= false
                    startActivity(Intent(this, HomeMainActivity::class.java))
                }.addOnFailureListener {
                    showProgress= false
//                    progress.dismiss()
                    Utils.showMessage(this, "Something went wrong")
                }


            }.addOnFailureListener {
//                progress.dismiss()
                showProgress= false
                Utils.showMessage(this, "Account Creation Failed")
            }
        })

    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        } else {
            Toast.makeText(this@AuthActivity, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {

            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credentials).addOnCompleteListener {

            if (it.isSuccessful) {
                val currentUser: FirebaseUser? = auth.currentUser
                if (currentUser != null) {
                    val intent = Intent(this, HomeMainActivity::class.java)
                    intent.putExtra("google", true)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String) {
//        progress.show()
        showProgress= true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                fetchData()
            }
        }.addOnFailureListener {
//            progress.dismiss()
            showProgress= false
            Utils.showMessage(this, "Please Check Email and Password")
        }
    }


    private fun fetchData() {
        val usersRef =
            database.getReference(Constants.USER_REF).child(Firebase.auth.currentUser!!.uid)
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val userModel: UserModel? = dataSnapshot.getValue(UserModel::class.java)
                    if (userModel != null) {
                        SharedPref.saveUserData(this@AuthActivity, userModel)
//                        progress.dismiss()
                        showProgress= false
                        startActivity(Intent(this@AuthActivity, HomeMainActivity::class.java))
                        finish()
                    } else {
                        showProgress= false
//                        progress.dismiss()
                        Utils.showMessage(this@AuthActivity, "Failed to fetch user data")
                    }

                } else {
                    showProgress= false
//                    progress.dismiss()
                    Utils.showMessage(this@AuthActivity, "User data not found")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Fetch data cancelled. Error: ${databaseError.message}")
            }
        })
    }


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->

    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}