package com.github.vinizaan.vehiclevaultsystem.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.vinizaan.vehiclevaultsystem.R
import com.github.vinizaan.vehiclevaultsystem.adapter.CarroAdapter
import com.github.vinizaan.vehiclevaultsystem.databinding.FragmentListaCarrosBinding
import com.github.vinizaan.vehiclevaultsystem.viewModel.CarroViewModel

class ListaCarrosFragment : Fragment(){

    private var _binding: FragmentListaCarrosBinding? = null

    private val binding get() = _binding!!

    lateinit var carroAdapter: CarroAdapter

    lateinit var viewModel: CarroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaCarrosBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_listaCarrosFragment_to_cadastroFragment) }

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_main, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        carroAdapter.filter.filter(p0)
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    private fun configureRecyclerView()
    {

        viewModel = ViewModelProvider(this).get(CarroViewModel::class.java)

        viewModel.allCars.observe(viewLifecycleOwner) { list ->
            list?.let {
                carroAdapter.updateList(list)
            }
        }

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        carroAdapter = CarroAdapter()
        recyclerView.adapter = carroAdapter

        val listener = object : CarroAdapter.CarroListener {
            override fun onItemClick(pos: Int) {
                val c = carroAdapter.carrosListaFilterable[pos]

                val bundle = Bundle()
                bundle.putInt("idCarro", c.id)

                findNavController().navigate(
                    R.id.action_listaCarrosFragment_to_detalheFragment,
                    bundle
                )

            }
        }
        carroAdapter.setClickListener(listener)
    }

}

