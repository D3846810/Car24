package uk.ac.tees.mad.d3846810.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.screens.CategoryScreen
import uk.ac.tees.mad.d3846810.utils.Constants
import uk.ac.tees.mad.d3846810.utils.Utils

class CategoryActivity : AppCompatActivity(){
    //    private val binding by lazy { ActivityCategoryBinding.inflate(layoutInflater) }
    private lateinit var database: FirebaseDatabase

    //    private lateinit var productAdapter: ProductAdapter

    val productList = mutableStateListOf<ProductModel>()
    val favoriteList = mutableStateListOf<ProductModel>()

    val loading = mutableStateOf("Loading...")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
        val category = intent.getStringExtra(Constants.CATEGORY_REF)!!

        setContent {
            CategoryScreen(loading = loading.value,
                categoryName = category,
                productList = productList,
                favoriteList = favoriteList,
                onProductClick = {
                    onProductDetails(it)
                },
                onFavorite = {
                    onFavorite(it)
                },
                onBackPress = {
                    startActivity(Intent(this@CategoryActivity, HomeMainActivity::class.java))
                    finish()
                })
        }

//        binding.tvCategoryName.text = category

//        binding.btBack.setOnClickListener {
//            startActivity(Intent(this@CategoryActivity, HomeMainActivity::class.java))
//            finish()
//        }

        database = FirebaseDatabase.getInstance()
        loadFavorites()
//        productAdapter = ProductAdapter(favoriteList, this@CategoryActivity, this@CategoryActivity)
        getProducts(category)
    }

    private fun onProductDetails(product: ProductModel) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCTS_REF, product.productId)
        startActivity(intent)
    }

    private fun onFavorite(product: ProductModel) {
        val databaseReference =
            database.reference.child(Constants.FAVORITE_REF).child(Firebase.auth.currentUser!!.uid)
                .child(product.productId)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    databaseReference.removeValue().addOnSuccessListener {
                        productList.remove(product)
                        favoriteList.remove(product)
                    }.addOnFailureListener {
                        Utils.showMessage(this@CategoryActivity, "Something went wrong")
                    }
                } else {
                    databaseReference.setValue(product).addOnSuccessListener {
                        productList.add(product)
                        favoriteList.add(product)
                    }.addOnFailureListener {
                        Utils.showMessage(this@CategoryActivity, "Something went wrong")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
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

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Utils.showMessage(this@CategoryActivity, "Failed")
                }
            })
    }

    private fun getProducts(category: String) {
        val productRef = database.getReference(Constants.PRODUCTS_REF)
        val query = productRef.orderByChild("selectCategory").equalTo(category)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = ArrayList<ProductModel>()

                for (productSnapshot in dataSnapshot.children) {
                    val productData = productSnapshot.getValue(ProductModel::class.java)
                    productData?.let {
                        list.add(it)
                    }
                }
                productList.clear()
                if (list.isEmpty()) {
                    loading.value = "No Products found"
                } else {
                    productList.addAll(list)
                }

//                if (productList.isNotEmpty()) {
//                    binding.tvStatus.visibility = View.GONE
//                    binding.productsRV.visibility = View.VISIBLE
//                    productAdapter.submitList(productList)
//                    binding.productsRV.adapter = productAdapter
//                } else {
//                    binding.tvStatus.visibility = View.VISIBLE
//                    binding.productsRV.visibility = View.GONE
//                    val status = "No Products found"
//                    binding.tvStatus.text = status
//                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Error fetching data: ${databaseError.message}")
            }
        })
    }

}