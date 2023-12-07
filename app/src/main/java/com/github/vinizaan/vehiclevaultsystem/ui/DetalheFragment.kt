package com.github.vinizaan.vehiclevaultsystem.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.vinizaan.vehiclevaultsystem.R
import com.github.vinizaan.vehiclevaultsystem.data.Carro
import com.github.vinizaan.vehiclevaultsystem.databinding.FragmentDetalheBinding
import com.github.vinizaan.vehiclevaultsystem.viewModel.CarroViewModel
import com.google.android.material.snackbar.Snackbar


class DetalheFragment : Fragment() {
    private var _binding: FragmentDetalheBinding? = null
    private val binding get() = _binding!!

    lateinit var carro: Carro

    lateinit  var modeloEditText: EditText
    lateinit var precoEditText: EditText
    lateinit var anoEditText: EditText
    lateinit var kmEditText: EditText

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
        _binding = FragmentDetalheBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modeloEditText = binding.commonLayout.editTextModelo
        precoEditText = binding.commonLayout.editTextPreco
        anoEditText = binding.commonLayout.editTextAno
        kmEditText = binding.commonLayout.editTextKm

        val idCarro = requireArguments().getInt("idCarro")

        viewModel.getCarById(idCarro)

        viewModel.carro.observe(viewLifecycleOwner) { result ->
            result?.let {
                carro = result
                modeloEditText.setText(carro.modelo)
                precoEditText.setText(carro.preco)
                anoEditText.setText(carro.ano)
                kmEditText.setText(carro.kmRodados)
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.detalhe_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_alterarCarro -> {

                        carro.modelo = modeloEditText.text.toString()
                        carro.preco = precoEditText.text.toString()
                        carro.ano = anoEditText.text.toString()
                        carro.kmRodados = kmEditText.text.toString()

                        viewModel.update(carro)

                        Snackbar.make(binding.root, "Carro alterado", Snackbar.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                        true
                    }
                    R.id.action_excluirCarro ->{
                        viewModel.delete(carro)

                        Snackbar.make(binding.root, "Carro apagado", Snackbar.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}