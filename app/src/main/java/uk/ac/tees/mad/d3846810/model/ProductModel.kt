package uk.ac.tees.mad.d3846810.model

import android.os.Parcelable
import uk.ac.tees.mad.d3846810.model.ProductImageUrlModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class ProductModel (
    var productId: String = "",
    var coverImg: String = "",
    var title: String = "",
    var year: String = "",
    var rating: String = "",
    var description: String = "",
    var price: String = "",
    var location: String = "",
    var brand: String = "",
    var insurance: String = "",
    var runKm: String = "",
    var engineType: String = "",
    var gearType: String = "",
    var rtoNum: String = "",
    var selectCategory: String = "",
    var seating: String = "",
    var available: String = "",
    var tag: String = "",
    @field:JvmField
    var carImages: @RawValue ArrayList<ProductImageUrlModel> = ArrayList()
): Parcelable
