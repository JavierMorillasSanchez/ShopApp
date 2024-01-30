package com.example.shopapp.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.shopapp.R
import com.example.shopapp.data.User
import com.example.shopapp.databinding.FragmentRegisterBinding
import com.example.shopapp.utils.Resource
import com.example.shopapp.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment: Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentRegisterBinding.inflate(inflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.apply {
            buttonRegister.setOnClickListener {

                val user = User(
                    editextFirstNameRegister.text.toString().trim(),
                    edittextLastNameRegister.text.toString().trim(),
                    editextEmailRegister.text.toString().trim()
                    )

                val password = editextPasswordRegister.text.toString()

                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when(it){
                    is Resource.Loading -> {
                        binding.buttonRegister.startAnimation()
                    }

                    is Resource.Success -> {
                        Log.d("test","Success: "+it.data.toString())
                        binding.buttonRegister.stopAnimation()
                    }

                    is Resource.Error ->{
                        Log.e("test","Error: "+it.message.toString())
                        binding.buttonRegister.stopAnimation()
                    }

                    else -> Unit
                }
            }
        }




    }


}