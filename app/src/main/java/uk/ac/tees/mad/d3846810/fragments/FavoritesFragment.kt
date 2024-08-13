package uk.ac.tees.mad.d3846810.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3846810.activities.DetailsActivity
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.screens.FavoriteScreen
import uk.ac.tees.mad.d3846810.utils.Constants
import uk.ac.tees.mad.d3846810.utils.Utils


class FavoritesFragment : Fragment() {
    //    private val binding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }
    private lateinit var database: FirebaseDatabase
    private lateinit var favoriteList: ArrayList<ProductModel>

    //    private lateinit var adapter: ProductAdapter
    private var userId = ""
    private lateinit var composeView: ComposeView
    private var loadingTxt = "Loading..."

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
        database = FirebaseDatabase.getInstance()
        favoriteList = ArrayList()
//        adapter = ProductAdapter(favoriteList, this@FavoritesFragment, requireContext())
        userId = Firebase.auth.currentUser!!.uid

        loadFavorites()

    }

    private fun setComposeContent() {
        composeView.setContent {
            FavoriteScreen(modifier = Modifier,
                loading = loadingTxt,
                favoriteList = favoriteList,
                onProductClick = {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra(Constants.PRODUCTS_REF, it.productId)
                    startActivity(intent)
                },
                onFavorite = {
                    manageFavorite(it)
                })
        }
    }

    private fun loadFavorites() {
        database.reference.child(Constants.FAVORITE_REF).child(userId)
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

                    if (favoriteList.isEmpty()) {
                        loadingTxt = "No Favorites"
                    }
                    setComposeContent()

//                    if (favoriteList.isNotEmpty()) {
//                        binding.favoriteRv.visibility = View.VISIBLE
//                        binding.tvFavStatus.visibility = View.GONE
//                        adapter.submitList(favoriteList)
//                        binding.favoriteRv.adapter = adapter
//                    } else {
//                        binding.favoriteRv.visibility = View.GONE
//                        binding.tvFavStatus.visibility = View.VISIBLE
//                        val status = "No Favorites"
//                        binding.tvFavStatus.text = status
//                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Utils.showMessage(requireContext(), "Failed")
                }
            })
    }

    //    override fun onItemClick(
//        productModel: ProductModel, favBtn: ImageView, favList: ArrayList<ProductModel>
//    ) {
//        manageFavorite(productModel, favoriteList, favBtn)
//    }
    private fun manageFavorite(
        product: ProductModel
    ) {
        val databaseReference =
            database.reference.child(Constants.FAVORITE_REF).child(userId).child(product.productId)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    databaseReference.removeValue().addOnSuccessListener {
                        favoriteList.remove(product)
                        setComposeContent()
                    }.addOnFailureListener {
                        Utils.showMessage(requireContext(), "Something went wrong")
                    }
                } else {
                    databaseReference.setValue(product).addOnSuccessListener {
                        setComposeContent()
                    }.addOnFailureListener {
                        Utils.showMessage(requireContext(), "Something went wrong")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
//    private fun manageFavorite(
//        product: ProductModel, favList: ArrayList<ProductModel>, favBtn: ImageView
//    ) {
//        val databaseReference =
//            database.reference.child(Constants.FAVORITE_REF).child(userId).child(product.productId)
//
//
//        val matchFound = favList.any { it.productId == product.productId }
//        favBtn.setImageResource(if (matchFound) R.drawable.ic_fav_filled_red else R.drawable.ic_fav_outline)
//
//        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    databaseReference.removeValue().addOnSuccessListener {
//                        favBtn.setImageResource(R.drawable.ic_fav_outline)
//
//                    }.addOnFailureListener {
//                        Utils.showMessage(requireContext(), "Something went wrong")
//                    }
//                } else {
//                    databaseReference.setValue(product).addOnSuccessListener {
//                        favBtn.setImageResource(R.drawable.ic_fav_filled_red)
//
//                    }.addOnFailureListener {
//                        Utils.showMessage(requireContext(), "Something went wrong")
//                    }
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }


}