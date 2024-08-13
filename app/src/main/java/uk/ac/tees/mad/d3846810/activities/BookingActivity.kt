package uk.ac.tees.mad.d3846810.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3846810.model.BookingModel
import uk.ac.tees.mad.d3846810.model.UserModel
import uk.ac.tees.mad.d3846810.preference.SharedPref
import uk.ac.tees.mad.d3846810.screens.CreateBookingScreen
import uk.ac.tees.mad.d3846810.utils.Constants
import uk.ac.tees.mad.d3846810.utils.Utils

class BookingActivity : AppCompatActivity() {
    //    private val binding by lazy { ActivityBookingBinding.inflate(layoutInflater) }
    private lateinit var database: FirebaseDatabase
    private var options = ""
    var user: UserModel = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
        SharedPref.getUserData(this@BookingActivity)?.let {
            user = it
            setComposeContent()
        }


        database = FirebaseDatabase.getInstance()

//        binding.bookOption.setOnCheckedChangeListener { _, checkedId ->
//            val selectedRadioButton = findViewById<RadioButton>(checkedId)
//            options = selectedRadioButton.text.toString()
//
//        }

//        binding.apply {


//            etEmail.setText(user?.email)
//            val name = "${user?.firstName} ${user?.lastName}"
//            etName.setText(name)
//            etPhoneNumber.setText(user?.phoneNumber)

//            btBack.setOnClickListener {
//                startActivity(Intent(this@BookingActivity, HomeMainActivity::class.java))
//            }

//            btBookNow.setOnClickListener {
//                validateBooking()
//            }
//        }
    }

    private fun setComposeContent() {
        setContent {
            CreateBookingScreen(userModel = user, onBackPress = {
                startActivity(Intent(this@BookingActivity, HomeMainActivity::class.java))
                finish()
            }, onBookNow = {
                if (it.reason.isEmpty()) {
                    Utils.showMessage(this@BookingActivity, "Please Select an option")
                    return@CreateBookingScreen
                }
                uploadBooking(it)
            })
        }
    }

//    private fun validateBooking() {
//        binding.apply {
//            val error = "Empty"
//            if (etName.text.toString().isEmpty()) {
//                etName.requestFocus()
//                etName.error = error
//            } else if (etEmail.text.toString().isEmpty()) {
//                etEmail.requestFocus()
//                etEmail.error = error
//            } else if (etPhoneNumber.text.toString()
//                    .isEmpty() || etPhoneNumber.text.toString().length != 10
//            ) {
//                etPhoneNumber.requestFocus()
//                etPhoneNumber.error = "Check"
//            } else if (etMessage.text.toString().isEmpty()) {
//                etMessage.requestFocus()
//                etMessage.error = error
//            } else if (options == "") {
//                Utils.showMessage(this@BookingActivity, "Please Select an option")
//            } else {
//                uploadBooking(it)
//            }
//        }
//    }

    private fun uploadBooking(bookingModel: BookingModel) {
        val userId = Firebase.auth.currentUser!!.uid
        val reference = database.getReference(Constants.BOOKING_REF).child(userId)
        val bookingId = database.reference.push().key!!
        val booking = bookingModel.copy(
            bookingId = bookingId,
            productId = intent.getStringExtra(Constants.PRODUCTS_REF)!!,
            userId = userId,
//            binding.etName.text.toString(),
//            binding.etEmail.text.toString(),
//            binding.etPhoneNumber.text.toString(),
//            binding.etMessage.text.toString(),
//            options,
            timestamp = Utils.getCurrentTime(),
            status = "Pending"
        )
        reference.child(bookingId).setValue(booking).addOnSuccessListener {
//                binding.etName.text = null
//                binding.etEmail.text = null
//                binding.etPhoneNumber.text = null
//                binding.etMessage.text = null
//                binding.etMessage.clearFocus()
//                options = ""
            Utils.showMessage(this@BookingActivity, "Booking Created Successfully")
            startActivity(Intent(this@BookingActivity, HomeMainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Utils.showMessage(this@BookingActivity, "Something went wrong")
        }
    }

}