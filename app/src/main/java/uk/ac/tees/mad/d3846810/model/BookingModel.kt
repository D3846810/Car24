package uk.ac.tees.mad.d3846810.model

import java.sql.Timestamp

data class BookingModel(
    val bookingId: String = "",
    val productId: String = "",
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val countryCode: String = "",
    val phoneNumber: String = "",
    val message: String = "",
    val reason: String = "",
    val timestamp: String = "",
    val status: String = ""
)
