package uk.ac.tees.mad.d3846810.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3846810.activities.CategoryActivity
import uk.ac.tees.mad.d3846810.activities.DetailsActivity
import uk.ac.tees.mad.d3846810.activities.EditProfileActivity
import uk.ac.tees.mad.d3846810.activities.PolicyActivity
import uk.ac.tees.mad.d3846810.activities.ViewBookingsActivity
import uk.ac.tees.mad.d3846810.databinding.DialogExitBinding
import uk.ac.tees.mad.d3846810.model.CategoryModel
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.model.SliderModel
import uk.ac.tees.mad.d3846810.model.UserModel
import uk.ac.tees.mad.d3846810.preference.SharedPref
import uk.ac.tees.mad.d3846810.screens.NavigationDrawer
import uk.ac.tees.mad.d3846810.utils.Constants
import uk.ac.tees.mad.d3846810.utils.Utils


class HomeFragment : Fragment() {
    private lateinit var composeView: ComposeView

    //    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var database: FirebaseDatabase

    //    private lateinit var viewPager2: ViewPager2
//    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    val sliderList = mutableStateListOf<SliderModel>()
    val categoryList = mutableStateListOf<CategoryModel>()
    val productList = mutableStateListOf<ProductModel>()
    val favoriteList = mutableStateListOf<ProductModel>()

    val tempProductList = ArrayList<ProductModel>()

    //    private lateinit var categoryAdapter: CategoryAdapter
//    private lateinit var productAdapter: ProductAdapter
//    private lateinit var drawerToggle: ActionBarDrawerToggle
    private var userId = ""

//    private val params = LinearLayout.LayoutParams(
//        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
//    ).apply {
//        setMargins(0, 0, 8, 0)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

//        return binding.root
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    var user: UserModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = SharedPref.getUserData(requireContext())

        setComposeContent()

        bottomSheet = BottomSheetDialog(requireContext())

//        viewPager2 = binding.viewPager2
        database = FirebaseDatabase.getInstance()
//        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {}
//        categoryAdapter = CategoryAdapter(this@HomeFragment)
//        productAdapter = ProductAdapter(favoriteList, this@HomeFragment, requireContext())
//        drawerToggle = ActionBarDrawerToggle(
//            requireActivity(),
//            binding.drawerLayout,
//            R.string.open,
//            R.string.close
//        )
        userId = Firebase.auth.currentUser!!.uid

//        binding.drawerLayout.addDrawerListener(drawerToggle)
//        drawerToggle.syncState()

//        val navigation = NavigationLayoutBinding.inflate(layoutInflater)
//        binding.navDrawer.addView(navigation.root)

//        binding.btNavDrawer.setOnClickListener {
//            binding.drawerLayout.open()
//        }

//        navigation.apply {
//            navHome.setOnClickListener {
//                closeDrawer()
//            }
//
//            navContactUs.setOnClickListener {
//                Utils.openEmailApp(requireContext(), "appsupport@gmail.com")
//            }
//
//            navRate.setOnClickListener {
//                closeDrawer()
//            }
//
//            btEditProfile.setOnClickListener {
//                closeDrawer()
//                startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
//            }
//
//            navPolicy.setOnClickListener {
//                closeDrawer()
//                startActivity(Intent(requireActivity(), PolicyActivity::class.java))
//            }
//
//            navShareApp.setOnClickListener {
//                closeDrawer()
//                val app =
//                    "Download now https://play.google.com/store/apps/details?id=${requireActivity().packageName}"
//                Utils.shareText(requireContext(), app)
//            }
//
//            btBookingsView.setOnClickListener {
//                closeDrawer()
//                startActivity(Intent(requireActivity(), ViewBookingsActivity::class.java))
//            }
//
//            btAboutUs.setOnClickListener {
//                closeDrawer()
//                val description = Constants.ABOUT_US
//                val buttonText = "Close"
//                val title = "About Us"
//                Utils.showDialog(requireContext(), title, description, buttonText) {
//                    val dialogDesign = DialogDesignBinding.inflate(layoutInflater)
//                    dialogDesign.btConfirm.visibility = View.GONE
//                }
//            }
//
//
//        }


//        binding.apply {
//            val userName = "Hello ${user?.firstName} ${user?.lastName},"
//            tvUserName.text = userName
//
//        }

        loadFavorites()
        fetchSliderData()
        loadCategories()
        getProducts()
//        searchProduct()

//        requireActivity().onBackPressedDispatcher.addCallback(
//            viewLifecycleOwner,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
////                    if (binding.drawerLayout.isOpen) {
////                        closeDrawer()
////                    } else {
//                    showExitDialog()
////                    }
//                }
//
//            })

    }

    private fun setComposeContent() {
        composeView.setContent {
            NavigationDrawer(userModel = user,
                sliderList = sliderList,
                categoryList = categoryList,
                productList = productList,
                favoriteList = favoriteList,
                onQuery = {
                    onQuery(it)
                },
                onCategory = {
                    onItemClick(categoryModel = it)
                },
                onProduct = {
                    onProduct(it)
                },
                onFavorite = {
                    manageFavorite(it)
                },
                onViewBooking = {
                    startActivity(Intent(requireActivity(), ViewBookingsActivity::class.java))
                },
                onEditProfile = {
                    startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
                },
                onPrivacy = {
                    startActivity(Intent(requireActivity(), PolicyActivity::class.java))
                },
                onContactUs = {
                    Utils.openEmailApp(requireContext(), "appsupport@gmail.com")
                },
                onShare = {
                    val app =
                        "Download now https://play.google.com/store/apps/details?id=${requireActivity().packageName}"
                    Utils.shareText(requireContext(), app)
                },
                onRateUs = {
                    Utils.rateUs(requireContext())
                })
        }
    }

    private fun onQuery(queryText: String) {
        searchProduct(queryText)
    }

    private fun onProduct(product: ProductModel) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCTS_REF, product.productId)
        requireActivity().startActivity(intent)
    }

    private fun searchProduct(newText: String) {
//        binding.searchView.setOnQueryTextListener(object :
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
        if (tempProductList.isNotEmpty()) {
            filteredList(newText)
        } else {
            Utils.showMessage(requireContext(), "Products Empty")
        }
//                return true
//            }
//
//        })

    }

    private fun filteredList(newText: String) {
        if (newText.isEmpty()) {
            productList.clear()
            productList.addAll(tempProductList)
            return
        }
        val filteredList = ArrayList<ProductModel>()
        for (product in tempProductList) {
            if (product.title.contains(
                    newText, ignoreCase = true
                ) || product.description.contains(
                    newText, ignoreCase = true
                ) || product.selectCategory.contains(newText, ignoreCase = true)
            ) filteredList.add(product)
        }
        productList.clear()
        productList.addAll(filteredList)
//        productAdapter.submitList(filteredList)
//        binding.productRv.adapter = productAdapter

    }

//    private fun closeDrawer() {
//        binding.drawerLayout.closeDrawer(GravityCompat.START)
//    }

    private fun loadCategories() {
        database.reference.child(Constants.CATEGORY_REF)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    categoryList.clear()
                    for (categorySnapshot in dataSnapshot.children) {
                        val category = categorySnapshot.getValue(CategoryModel::class.java)
                        if (category != null) {
                            categoryList.add(category)
                        } else {
                            Log.e("Error", "Failed to convert data: $categorySnapshot")
                        }
                    }
//                    categoryAdapter.submitList(categoryList)
//                    binding.categoryRv.adapter = categoryAdapter

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Utils.showMessage(requireContext(), "Failed")
                }
            })
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
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Utils.showMessage(requireContext(), "Failed")
                }
            })
    }


    private fun getProducts() {
        val carRef = database.getReference(Constants.PRODUCTS_REF)
        carRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                productList.clear()
                for (carSnapshot in dataSnapshot.children) {
                    val product = carSnapshot.getValue(ProductModel::class.java)
                    if (product != null) {
                        productList.add(product)
                        tempProductList.add(product)
                    } else {
                        // Handle the case where data couldn't be converted

                    }
                }

//                productAdapter.submitList(productList)
//                binding.productRv.adapter = productAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Utils.showMessage(requireContext(), "Failed")
            }
        })
    }


    private fun fetchSliderData() {
        database.getReference(Constants.SLIDER_DOCUMENT).orderByChild("timestamp")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    sliderList.clear()
                    for (childSnapshot in dataSnapshot.children.reversed()) {
                        val sliders = childSnapshot.getValue(SliderModel::class.java)
                        if (sliders != null) {
                            sliderList.add(sliders)
                        }
                    }
//                    if (sliderList.isNotEmpty()) {
//                        createSlider()
//                    } else {
//                        binding.card.visibility = View.GONE
//                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
    }

//    private fun createSlider() {
//        val sliderAdapter = SliderAdapter(this@HomeFragment)
//        viewPager2.adapter = sliderAdapter

//        val dotImage = Array(sliderList.size) { ImageView(requireContext()) }

//        binding.linearLay.removeAllViews()

//        binding.card.visibility = View.VISIBLE
//        dotImage.forEach {
//            it.setImageResource(R.drawable.slide_unselected)
//            binding.linearLay.addView(it, params)
//        }

//        dotImage[0].setImageResource(R.drawable.slide_selected)
//        sliderAdapter.submitList(sliderList)

//        val pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                dotImage.mapIndexed { index, imageView ->
//                    imageView.setImageResource(
//                        if (position == index) R.drawable.slide_selected else R.drawable.slide_unselected
//                    )
//                }
//                super.onPageSelected(position)
//            }
//        }
//        viewPager2.registerOnPageChangeCallback(pageChangeListener)

//        val handler = Handler(Looper.getMainLooper())


//        val runnable = object : Runnable {
//            override fun run() {
//                val currentItem = viewPager2.currentItem
//                val nextItem = if (currentItem < sliderList.size - 1) currentItem + 1 else 0
//                viewPager2.setCurrentItem(nextItem, true)
//                handler.postDelayed(this, 3500)
//            }
//        }
//        handler.removeCallbacks(runnable)
//        handler.postDelayed(runnable, 3500)
//    }

    private fun showExitDialog() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val layout = DialogExitBinding.inflate(layoutInflater)
        bottomSheet.setContentView(layout.root)
        bottomSheet.setCanceledOnTouchOutside(true)
        layout.btExit.setOnClickListener {
            bottomSheet.dismiss()
            requireActivity().finish()
        }
        layout.btCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        bottomSheet.show()
    }


    fun onItemClick(categoryModel: CategoryModel) {
        val intent = Intent(requireActivity(), CategoryActivity::class.java)
        intent.putExtra(Constants.CATEGORY_REF, categoryModel.categoryName)
        startActivity(intent)
    }

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
                    }.addOnFailureListener {
                        Utils.showMessage(requireContext(), "Something went wrong")
                    }
                } else {
                    databaseReference.setValue(product).addOnSuccessListener {}
                        .addOnFailureListener {
                            Utils.showMessage(requireContext(), "Something went wrong")
                        }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}