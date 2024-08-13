package uk.ac.tees.mad.d3846810.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uk.ac.tees.mad.d3846810.model.NotificationModel
import uk.ac.tees.mad.d3846810.screens.NotificationScreen
import uk.ac.tees.mad.d3846810.utils.Constants


class NotificationFragment : Fragment() {
    //    private val binding by lazy { FragmentNotificationBinding.inflate(layoutInflater) }
    private lateinit var database: FirebaseDatabase
//    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var composeView: ComposeView
    val notificationList = mutableStateListOf<NotificationModel>()
    var loadingTxt by mutableStateOf("Loading...")

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
//        notificationAdapter = NotificationAdapter(requireContext())
        getAvailableNotifications()
    }

    private fun setComposeContent() {
        composeView.setContent {
            NotificationScreen(loading = loadingTxt,
                notificationList = notificationList)
        }
    }

    private fun getAvailableNotifications() {
        database.getReference(Constants.NOTIFICATION_REF)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    notificationList.clear()
                    for (snapshot in dataSnapshot.children) {
                        val notification = snapshot.getValue(NotificationModel::class.java)
                        notification?.let {
                            notificationList.add(it)
                        }
                    }
                    if (notificationList.isEmpty()) {
                        loadingTxt = "No Notifications"
                    }

//                    if (notificationList.isNotEmpty()) {
//                        binding.tvNotificationStatus.visibility = View.GONE
//                        binding.notificationRv.visibility = View.VISIBLE
//                        notificationAdapter.submitList(notificationList)
//                        binding.notificationRv.adapter = notificationAdapter
//                    } else {
//                        binding.tvNotificationStatus.visibility = View.VISIBLE
//                        binding.notificationRv.visibility = View.GONE
//                        val status = "No Notifications"
//                        binding.tvNotificationStatus.text = status
//                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle onCancelled
                }
            })
    }


}