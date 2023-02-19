package com.example.identity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.identity.databinding.HomeScreenBinding

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

    interface Identity {
        val name: String
        val email: String
        val phoneNumber: String
    }

     private fun generateIdentity(name: String, email: String, phoneNumber: String) {
         class IdentityImpl(override val name: String, override val email: String, override val phoneNumber: String) : Identity
         val newIdentity = IdentityImpl(name, email, phoneNumber)
         
         binding.identityCardName.text = name
         binding.identityCardEmail.text = email
         binding.identityCardPhoneNumber.text = phoneNumber
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hasNewIdentityBeenCreated = arguments?.getBoolean("identityCreated")

        if(hasNewIdentityBeenCreated == true){
            val name = arguments?.getString("name")
            val email = arguments?.getString("email")
            val phoneNumber = arguments?.getString("phoneNumber")
            if (name != null && email != null && phoneNumber != null) {
                generateIdentity(name, email, phoneNumber)
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
