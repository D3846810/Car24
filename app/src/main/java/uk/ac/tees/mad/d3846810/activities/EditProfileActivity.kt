package uk.ac.tees.mad.d3846810.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3846810.model.UserModel
import uk.ac.tees.mad.d3846810.preference.SharedPref
import uk.ac.tees.mad.d3846810.screens.EditProfileScreen
import uk.ac.tees.mad.d3846810.utils.Constants
import uk.ac.tees.mad.d3846810.utils.Utils

class EditProfileActivity : AppCompatActivity() {
    //    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }
    private lateinit var database: FirebaseDatabase
    private var userData: UserModel? = UserModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)

        userData = SharedPref.getUserData(this@EditProfileActivity)
        database = FirebaseDatabase.getInstance()
//        setUserData(userData)
        setComposeContent()

//        binding.apply {
//            btBack.setOnClickListener {
//                startActivity(Intent(this@EditProfileActivity, HomeMainActivity::class.java))
//            }
//
//            btUpdate.setOnClickListener {
//                val firstName = binding.etFirstName.text.toString().trim()
//                val lastName = binding.etLastName.text.toString().trim()
//                val email = binding.etEmailId.text.toString().trim()
//                val phoneNumber = binding.etPhoneNumber.text.toString().trim()
//
//                validateUserInput(firstName, lastName, email, phoneNumber)
//            }
//
//        }

    }

    private fun setComposeContent() {
        setContent {
            EditProfileScreen(userModel = userData!!, onBackPress = {
                startActivity(Intent(this@EditProfileActivity, HomeMainActivity::class.java))
            }, onUpdateAccount = {
                validateUserInput(it)
            })
        }
    }


    private fun validateUserInput(
        userModel: UserModel
    ) {
//        if (firstName.isEmpty()) {
//            binding.etFirstName.requestFocus()
//            binding.etFirstName.error = "Empty"
//        } else if (lastName.isEmpty()) {
//            binding.etLastName.requestFocus()
//            binding.etLastName.error = "Empty"
//        } else if (email.isEmpty()) {
//            binding.etEmailId.requestFocus()
//            binding.etEmailId.error = "Empty"
//        } else if (phoneNumber.isEmpty() || phoneNumber.length != 10) {
//            binding.etPhoneNumber.requestFocus()
//            binding.etPhoneNumber.error = "Error"
//        }  else {
//            updateUser(firstName, lastName, email, phoneNumber)
//        }
        updateUser(userModel)

    }

    //    private fun updateUser(firstName: String, lastName: String, email: String, phoneNumber: String) {
    private fun updateUser(userData: UserModel) {
        val userId = Firebase.auth.currentUser!!.uid
        val usersReference = database.getReference(Constants.USER_REF)

        usersReference.child(userId).setValue(userData).addOnSuccessListener {
            SharedPref.saveUserData(this@EditProfileActivity, userData)
            Utils.showMessage(this@EditProfileActivity, "Profile Updated")
            startActivity(Intent(this@EditProfileActivity, HomeMainActivity::class.java))
        }.addOnFailureListener {
            Utils.showMessage(this@EditProfileActivity, "Something went wrong")
        }


    }


//    private fun setUserData(userData: UserModel?) {
//        this.userData = userData
//        if (userData != null) {

//            binding.apply {
//                etEmailId.setText(userData.email)
//                etFirstName.setText(userData.firstName)
//                etLastName.setText(userData.lastName)
//                etPhoneNumber.setText(userData.phoneNumber)
//            }
//        } else {
//            Utils.showMessage(this@EditProfileActivity, "Something went wrong")
//        }
//
//    }
}