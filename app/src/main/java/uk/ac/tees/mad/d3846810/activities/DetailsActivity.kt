package uk.ac.tees.mad.d3846810.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import coil.load
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.FavoriteModel
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.screens.DetailsScreen
import uk.ac.tees.mad.d3846810.utils.Constants
import uk.ac.tees.mad.d3846810.utils.Utils

class DetailsActivity : AppCompatActivity() {
//    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
//    private lateinit var adapter: ProductImageAdapter
//    private lateinit var similarAdapter: SimilarProductAdapter
    private lateinit var database: FirebaseDatabase
    private var productData: ProductModel = ProductModel()
    var favoriteList= mutableStateListOf<ProductModel>()
    val similarProductList = mutableStateListOf<ProductModel>()
    val favWithSimilarList = mutableStateListOf<FavoriteModel>()
    var isProductFavorite by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
        setComposeContent()
        val product = intent.getStringExtra(Constants.PRODUCTS_REF)!!
        database = FirebaseDatabase.getInstance()
//        adapter = ProductImageAdapter(this)
//        similarAdapter = SimilarProductAdapter(favoriteList, this@DetailsActivity)

        getProductData(product)


//        binding.apply {
//            btDetaisBack.setOnClickListener {
//                startActivity(Intent(this@DetailsActivity, HomeMainActivity::class.java))
//            }

//            btDetailFavorite.setOnClickListener {
//                if(productData != null) {
//                    manageFavorite(productData!!, favoriteList, btDetailFavorite)
//                } else {
//                    Utils.showMessage(this@DetailsActivity, "Something went wrong")
//                }
//            }

//            btShare.setOnClickListener {
//                if(productData != null) {
//                    val details = " ${productData!!.title} at price 0f ${productData!!.price}\nI found this product on ${getString(R.string.app_name)}"
//                    Utils.shareText(this@DetailsActivity, details)
//                } else {
//                    Utils.showMessage(this@DetailsActivity, "Something went wrong")
//                }
//
//            }

//            btBook.setOnClickListener {
//                if(productData != null) {
//                    val intent = Intent(this@DetailsActivity, BookingActivity::class.java)
//                    intent.putExtra(Constants.PRODUCTS_REF, productData!!.productId)
//                    startActivity(intent)
//                } else {
//                    Utils.showMessage(this@DetailsActivity, "Something went wrong")
//                }
//
//            }
//        }

    }

    private fun setComposeContent() {
        setContent {
            DetailsScreen(productModel = productData,
                isProductFav = isProductFavorite,
                similarProductList = similarProductList,
                favWithSimilarList = favWithSimilarList,
                onFavorite = {
                    manageFavorite(it)
                },
                onBookTestDrive = {
                    shareProduct(it)
                },
                onShare = {
                    val details = " ${it.title} at price 0f ${it.price}\nI found this product on ${
                        getString(R.string.app_name)
                    }"
                    Utils.shareText(this@DetailsActivity, details)
                },
                onBackPress = {
                    finish()
                })
        }
    }

    private fun shareProduct(productModel: ProductModel) {
        val intent = Intent(this@DetailsActivity, BookingActivity::class.java)
        intent.putExtra(Constants.PRODUCTS_REF, productModel.productId)
        startActivity(intent)
    }

    private fun loadFavorites() {
        database.reference.child(Constants.FAVORITE_REF).child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    favoriteList.clear()
                    for (snapshot in dataSnapshot.children) {
                        val fav = snapshot.getValue(ProductModel::class.java)
                        if (fav != null) {
                            favoriteList.add(fav)
                        } else {
                            Log.e("Error", "Failed to convert data: ")
                        }
                    }
                    isProductFavorite = favoriteList.any { it.productId == productData.productId }
                    setComposeContent()
//                    binding.btDetailFavorite.setImageResource(if (isProductFavorite) R.drawable.ic_fav_filled_red else R.drawable.ic_fav_outline)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Utils.showMessage(this@DetailsActivity, "Failed")
                }
            })
    }


    //    private fun manageFavorite(
//        product: ProductModel, favList: ArrayList<ProductModel>, favBtn: ImageView
//    ) {
    private fun manageFavorite(
        product: ProductModel
    ) {
        val databaseReference =
            database.reference.child(Constants.FAVORITE_REF).child(Firebase.auth.currentUser!!.uid)
                .child(product.productId)
//        favBtn.setImageResource(if (matchFound) R.drawable.ic_fav_filled_red else R.drawable.ic_fav_outline)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    databaseReference.removeValue().addOnSuccessListener {
//                        favBtn.setImageResource(R.drawable.ic_fav_outline)
                        setComposeContent()
                    }.addOnFailureListener {
                        Utils.showMessage(this@DetailsActivity, "Something went wrong")
                    }
                } else {
                    databaseReference.setValue(product).addOnSuccessListener {
//                        favBtn.setImageResource(R.drawable.ic_fav_filled_red)
                    }.addOnFailureListener {
                        Utils.showMessage(this@DetailsActivity, "Something went wrong")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun getProductSimilar(category: String) {
        val productRef = database.getReference(Constants.PRODUCTS_REF)
        val query = productRef.orderByChild("selectCategory").equalTo(category)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (productSnapshot in dataSnapshot.children) {
                    val productData = productSnapshot.getValue(ProductModel::class.java)
                    productData?.let { product ->
                        similarProductList.add(product)
                        val matchFound = favoriteList.any { it.productId == product.productId }
                        favWithSimilarList.add(FavoriteModel(product.productId, matchFound))
                    }
                }
                setComposeContent()
//                if (productList.isNotEmpty()) {
//                    similarAdapter.submitList(productList)
//                    binding.similarProductRv.adapter = similarAdapter
//                }

                Log.d("product listss", "getProductSimilar: $similarProductList")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Error fetching data: ${databaseError.message}")
            }
        })
    }


    private fun getProductData(productId: String) {
        val productRef = database.getReference(Constants.PRODUCTS_REF).child(productId)
        productRef.get().addOnSuccessListener {
            productData = it.getValue(ProductModel::class.java)!!
//            setProductDetails(productData)
            loadFavorites()
            getProductSimilar(productData.selectCategory)
        }

    }

    private fun setProductDetails(product: ProductModel) {
//        val subTitle = "${product.runKm} km • ${product.engineType} • ${product.gearType}"
//        val carTitle = "${product.year} ${product.title}"

//        binding.imageSlider.load(product.carImages[0].imageUrl) {
//            placeholder(R.drawable.placeholder)
//            error(R.drawable.placeholder)
//        }
//        binding.tvCarTitle.text = carTitle
//        binding.tvYear.text = product.year
//        binding.tvRating.text = product.rating
//        binding.tvCarSub.text = subTitle
//        binding.tvCarBrand.text = product.brand
//        binding.tvTopLoction.text = product.location
//        binding.tvDescription.text = product.description
//        binding.tvCarPrice.text = product.price
//        binding.tvInsurance.text = product.insurance
//        binding.tvKm.text = product.runKm
//        binding.tvGear.text = product.gearType
//        binding.tvEngine.text = product.engineType
//        binding.tvRto.text = product.rtoNum.uppercase()
//        binding.tvCarRto.text = product.rtoNum.uppercase()
//        binding.tvCarYear.text = product.year
//        binding.tvAvailability.text = product.available
//        val seats = "${product.seating} Seats"
//        binding.tvSeats.text = seats
//        binding.tvBrand.text = product.brand
//
//        adapter.submitList(product.carImages)
//        binding.productImageRv.adapter = adapter

//        binding.btShare.setOnClickListener {
//            val text =
//                "${product.title}\nThis car at great Price\nDownload the Car store app on PlayStore\nhttps://play.google.com/store/apps/details?id=$packageName"
//            Utils.shareText(this@DetailsActivity, text)
//        }
    }
}