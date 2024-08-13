package uk.ac.tees.mad.d3846810.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3846810.activities.AuthActivity
import uk.ac.tees.mad.d3846810.activities.EditProfileActivity
import uk.ac.tees.mad.d3846810.activities.PolicyActivity
import uk.ac.tees.mad.d3846810.activities.ViewBookingsActivity
import uk.ac.tees.mad.d3846810.databinding.DialogDesignBinding
import uk.ac.tees.mad.d3846810.model.UserModel
import uk.ac.tees.mad.d3846810.preference.SharedPref
import uk.ac.tees.mad.d3846810.screens.ProfileScreen
import uk.ac.tees.mad.d3846810.utils.Constants
import uk.ac.tees.mad.d3846810.utils.Utils


class ProfileFragment : Fragment() {
    private lateinit var composeView: ComposeView

    //    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private var userId: String = ""
    private lateinit var user: UserModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
//        return binding.root
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setComposeContent()
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        userId = auth.currentUser!!.uid
        user = SharedPref.getUserData(requireContext())

        getUserData()

//        binding.tvDeleteAccount.setOnClickListener {
//            deleteAccount()
//        }

//        binding.layEdit.setOnClickListener {
//            if(user != null) {
//                val intent = Intent(requireActivity(), EditProfileActivity::class.java)
//                startActivity(intent)
//            } else {
//                Utils.showMessage(requireContext(), "Something went wrong")
//            }
//        }

//        binding.layBooking.setOnClickListener {
//            startActivity(Intent(requireActivity(), ViewBookingsActivity::class.java))
//        }

//        binding.layContact.setOnClickListener {
//            Utils.openEmailApp(requireContext(), "appsupport@gmail.com")
//        }


//        binding.layPrivacy.setOnClickListener {
//            startActivity(Intent(requireActivity(), PolicyActivity::class.java))
//        }
//
//        binding.btSignOut.setOnClickListener {
//            logout()
//        }
//
//        binding.layShareApp.setOnClickListener {
//            onShareApp()
//        }
//
//        binding.layAbout.setOnClickListener {
//            about()
//        }
    }

    private fun about() {
        val description = Constants.ABOUT_US
        val buttonText = "Close"
        val title = "About Us"
        Utils.showDialog(requireContext(), title, description, buttonText) {
            val dialogDesign = DialogDesignBinding.inflate(layoutInflater)
            dialogDesign.btConfirm.visibility = View.GONE
        }
    }

    private fun onShareApp() {
        val app =
            "Download now https://play.google.com/store/apps/details?id=${requireActivity().packageName}"
        Utils.shareText(requireContext(), app)
    }

    private fun setComposeContent() {
        composeView.setContent {
            ProfileScreen(userModel = user, onEditProfile = {
                onEditProfile()
            }, onBookingHistory = {
                bookingHistory()
            }, onShareApp = {
                onShareApp()
            }, onPrivacyPolicy = {
                privacyPolicy()
            }, onContactUs = {
                contactUs()
            }, onAboutUs = {
                about()
            }, onLogout = {
                logout()
            }, onDelete = {
                deleteAccount()
            })
        }
    }

    private fun privacyPolicy() {
        startActivity(Intent(requireActivity(), PolicyActivity::class.java))
    }

    private fun contactUs() {
        Utils.openEmailApp(requireContext(), "appsupport@gmail.com")
    }

    private fun bookingHistory() {
        startActivity(Intent(requireActivity(), ViewBookingsActivity::class.java))
    }

    private fun onEditProfile() {
        if (user != null) {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            startActivity(intent)
        } else {
            Utils.showMessage(requireContext(), "Something went wrong")
        }
    }


    private fun logout() {
        if (auth.currentUser != null) {
            val title = "Logout Your Account"
            val description =
                "Do you really want to logout, We will miss you... if you still want to cancel this process else you can click logout to proceed"
            val confirmBtnText = "Logout"
            if (auth.currentUser != null) {
                Utils.showDialog(
                    requireContext(), title, description, confirmBtnText
                ) {
                    auth.signOut()
                    SharedPref.clearData(requireContext())
                    Toast.makeText(
                        requireContext(), "Account Logout Successfully", Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                }
            }
        } else {
            Toast.makeText(requireContext(), "User Not Login", Toast.LENGTH_SHORT).show()

        }
    }

    private fun deleteAccount() {
        val title = "Delete Your Account"
        val description =
            "Are you sure to Delete Car store account? Once you delete, you can't retrieve your action."
        val confirmBtnText = "Delete"
        if (Firebase.auth.currentUser != null) {
            Utils.showDialog(requireContext(), title, description, confirmBtnText) {
                val user = auth.currentUser
                user?.delete()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val databaseReference =
                            database.getReference(Constants.USER_REF).child(userId)
                        databaseReference.removeValue().addOnSuccessListener {
                            SharedPref.clearData(requireContext())
                            Utils.showMessage(requireContext(), "User deleted successfully")
                            startActivity(Intent(requireContext(), AuthActivity::class.java))
                        }.addOnFailureListener {
                            Utils.showMessage(requireContext(), "Failed to delete user data")
                        }
                    } else {
                        Utils.showMessage(
                            requireContext(),
                            "Failed to delete user account: ${task.exception?.message}"
                        )
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "User Not Logged In", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserData() {
        if (user != null) {
//            binding.tvFirstName.text = "${user!!.firstName} ${user!!.lastName}"
            val address = "${user.phoneNumber}\n${user.email}"
//            binding.tvAddress.text = address
            val profileName = "${user.firstName.first()}${user.lastName.first()}"
//            binding.tvProfile.text = profileName.uppercase()
        } else {
            Utils.showMessage(requireContext(), "Something went wrong")
        }

    }
}