package com.github.vinizaan.vehiclevaultsystem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.github.vinizaan.vehiclevaultsystem.data.Carro
import com.github.vinizaan.vehiclevaultsystem.databinding.CarroCelulaBinding

class CarroAdapter: RecyclerView.Adapter<CarroAdapter.CarroViewHolder>(),
    Filterable {

    var listener: CarroListener?=null

    var carrosLista = ArrayList<Carro>()
    var carrosListaFilterable = ArrayList<Carro>()

    private lateinit var binding: CarroCelulaBinding

    fun updateList(newList: List<Carro> ){
        carrosLista = newList as ArrayList<Carro>
        carrosListaFilterable = carrosLista
        notifyDataSetChanged()
    }

    fun setClickListener(listener: CarroListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarroViewHolder {

        binding = CarroCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  CarroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarroViewHolder, position: Int) {
        holder.nomeVH.text = carrosListaFilterable[position].modelo
        holder.foneVH.text = carrosListaFilterable[position].preco
    }

    override fun getItemCount(): Int {
        return carrosListaFilterable.size
    }

    inner class CarroViewHolder(view: CarroCelulaBinding): RecyclerView.ViewHolder(view.root)
    {
        val nomeVH = view.nome
        val foneVH = view.fone

        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    interface CarroListener
    {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    carrosListaFilterable = carrosLista
                else
                {
                    val resultList = ArrayList<Carro>()
                    for (row in carrosLista)
                        if (row.modelo.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    carrosListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = carrosListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                carrosListaFilterable = p1?.values as ArrayList<Carro>
                notifyDataSetChanged()
            }

        }
    }

}