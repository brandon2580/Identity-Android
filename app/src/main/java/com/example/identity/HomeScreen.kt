package com.example.identity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.identity.databinding.HomeScreenBinding
import okhttp3.*
import okio.IOException

interface Identity {
    val name: String
    val email: String
    val phoneNumber: String
}

var identitiesArray = mutableListOf<Identity>()

class HomeScreen : Fragment() {
    private var _binding: HomeScreenBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = HomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun generateIdentity(name: String, email: String, phoneNumber: String) {
        class IdentityImpl(override val name: String, override val email: String, override val phoneNumber: String) : Identity
        val newIdentity = IdentityImpl(name, email, phoneNumber)
        identitiesArray.add(newIdentity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hasNewIdentityBeenCreated = arguments?.getBoolean("identityCreated")
        val inflater = LayoutInflater.from(context)

        if(hasNewIdentityBeenCreated == true){
            val name = arguments?.getString("name")
            val email = arguments?.getString("email")
            val phoneNumber = arguments?.getString("phoneNumber")
            if (name != null && email != null && phoneNumber != null) {
                generateIdentity(name, email, phoneNumber)
            }
        }

// Render each Identity "card" individually to display on the user's home screen
        for (i in identitiesArray.indices) {
            val identity = identitiesArray[i]
            val cardView = inflater.inflate(R.layout.identity_cards, binding.homeScreen, false)
            cardView.findViewById<TextView>(R.id.identity_card_name).text = identity.name
            cardView.findViewById<TextView>(R.id.identity_card_email).text = identity.email
            cardView.findViewById<TextView>(R.id.identity_card_phone_number).text = identity.phoneNumber
            binding.homeScreen.addView(cardView)

            val card = cardView.findViewById<LinearLayout>(R.id.identity_card)
            card.tag = i // Set the position of the card as its tag
            card.setOnClickListener {
                val clickedPosition = it.tag as Int // Get the position of the clicked card from its tag
                val clickedIdentity = identitiesArray[clickedPosition] // Get the clicked identity from the array
                println("Clicked on ${clickedIdentity.name}!")
                findNavController().navigate(R.id.action_Home_to_ViewIdentity)
            }
        }


        binding.buttonCreateIdentity.setOnClickListener {
            findNavController().navigate(R.id.action_Home_to_CreateIdentity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
