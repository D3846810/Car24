package uk.ac.tees.mad.d3846810.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3846810.model.BookingModel
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.screens.BookingScreen
import uk.ac.tees.mad.d3846810.utils.Constants

class ViewBookingsActivity : AppCompatActivity() {
    //    private val binding by lazy { ActivityViewBookingsBinding.inflate(layoutInflater) }
    private lateinit var database: FirebaseDatabase
    private var loading = "Loading..."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)

        setComposeContent()

        database = FirebaseDatabase.getInstance()

//        binding.bookingRv.layoutManager = LinearLayoutManager(this)

        getAvailableBookings()

//        binding.btBack.setOnClickListener {
//            startActivity(Intent(this@ViewBookingsActivity, HomeMainActivity::class.java))
//        }
    }

    private fun setComposeContent() {
        setContent {
            BookingScreen(
                loadingText = loading,
                bookingList = bookingList,
                productList = productList,
                onBackPressed = {
                    startActivity(Intent(this@ViewBookingsActivity, HomeMainActivity::class.java))
                }) {

            }
        }
    }

    val bookingList = ArrayList<BookingModel>()
    val productList = ArrayList<ProductModel>()

    private fun getAvailableBookings() {
        database.getReference(Constants.BOOKING_REF).child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    bookingList.clear()
                    for (snapshots in snapshot.children) {
                        val booking = snapshots.getValue(BookingModel::class.java)
                        bookingList.add(booking!!)
                    }

                    if (bookingList.isEmpty()) {
                        loading = "No Booking Found"
                        setComposeContent()
                    } else {
                        for (booking: BookingModel in bookingList) {
                            getProductData(booking.productId)
                        }
                    }
//                    if (bookingList.isEmpty()) {
//                        binding.tvStatus.visibility = View.VISIBLE
//                        binding.tvStatus.text = "No Bookings Found"
//                        binding.bookingRv.visibility = View.GONE
//                    } else {
//                        binding.tvStatus.visibility = View.GONE
//                        binding.bookingRv.visibility = View.VISIBLE
//                        val bookingAdapter = BookingProductAdapter(this@ViewBookingsActivity)
//                        bookingAdapter.submitList(bookingList)
//                        binding.bookingRv.adapter = bookingAdapter
//                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }

    private fun getProductData(productId: String) {
        val productRef = Firebase.database.getReference(Constants.PRODUCTS_REF).child(productId)
        productRef.get().addOnSuccessListener {
            val productModel = it.getValue(ProductModel::class.java)
            if (productModel != null) {
                productList.add(productModel)
                setComposeContent()
            }
        }
    }
}