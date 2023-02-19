package com.example.identity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.identity.databinding.CreateIdentityScreenBinding

class CreateIdentity : Fragment() {
    private var _binding: CreateIdentityScreenBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = CreateIdentityScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            // Pass user inputted name back to Home
            val name = binding.identityNameEditText.text.toString();
            val email = "testemail@simplelogin.com"
            val phoneNumber = "(800)-400-500"
            var bundle: Bundle? = null

            if(name.isNotBlank()){
                bundle = bundleOf("identityCreated" to true, "name" to name, "email" to email, "phoneNumber" to phoneNumber)
            }

            findNavController().navigate(R.id.action_CreateIdentity_to_Home, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}