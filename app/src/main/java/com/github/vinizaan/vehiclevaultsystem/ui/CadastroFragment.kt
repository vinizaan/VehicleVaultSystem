package com.github.vinizaan.vehiclevaultsystem.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.vinizaan.vehiclevaultsystem.R
import com.github.vinizaan.vehiclevaultsystem.data.Carro
import com.github.vinizaan.vehiclevaultsystem.databinding.FragmentCadastroBinding

import com.github.vinizaan.vehiclevaultsystem.viewModel.CarroViewModel
import com.google.android.material.snackbar.Snackbar


class CadastroFragment : Fragment(){
    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: CarroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CarroViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.cadastro_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_salvarCarro -> {
                        val modelo = binding.commonLayout.editTextModelo.text.toString()
                        val preco = binding.commonLayout.editTextPreco.text.toString()
                        val ano = binding.commonLayout.editTextAno.text.toString()
                        val km = binding.commonLayout.editTextKm.text.toString()

                        val carro = Carro(0,modelo, preco, ano, km)

                        viewModel.insert(carro)

                        Snackbar.make(binding.root, "Carro inserido", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}