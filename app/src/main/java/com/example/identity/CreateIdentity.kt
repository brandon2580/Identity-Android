package com.example.identity
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.identity.databinding.CreateIdentityScreenBinding
import okhttp3.*
import okio.IOException
import org.json.JSONObject
import java.util.*

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
            // Get SimpleLogin API key from assets/config.properties
            val inputStream = context?.assets?.open("config.properties")
            val properties = Properties()
            properties.load(inputStream)
            val apiKey = properties.getProperty("simplelogin_api_key")

            val client = OkHttpClient()
            val body = FormBody.Builder().build()
            val request = Request.Builder()
                .url("https://app.simplelogin.io/api/alias/random/new")
                .addHeader("Authentication", apiKey)
                .post(body)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Failed: $e")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        val jsonResponse = JSONObject(responseBody)
                        val name = binding.identityNameEditText.text.toString();
                        val email = jsonResponse.getString("alias")
                        val phoneNumber = "(800)-400-500"
                        var bundle: Bundle? = null
                        if(name.isNotBlank()){
                            bundle = bundleOf("identityCreated" to true, "name" to name, "email" to email, "phoneNumber" to phoneNumber)
                        }
                        println("Success!")
                        requireActivity().runOnUiThread{
                            findNavController().navigate(R.id.action_CreateIdentity_to_Home, bundle)
                        }
                    } else {
                        println("Failed")
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}