package com.br.tf_lista_myo.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.tf_lista_myo.databinding.FragmentAllBinding
import com.br.tf_lista_myo.service.constants.GuestConstants
import com.br.tf_lista_myo.view.adapter.GuestAdapter
import com.br.tf_lista_myo.view.listener.GuestListener
import com.br.tf_lista_myo.view.viewholder.GuestsViewModel
import com.br.tf_lista_myo.viewmodel.HomeViewModel

class AllGuestsFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private val mAdapter: GuestAdapter = GuestAdapter()
    private lateinit var mListener: GuestListener
    private var _binding: FragmentAllBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        mViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Elemento de interface - RecyclerView
        // Não é possível deixar o Kotlin fazer o mapeamento, pois a fragment ainda não está totalmente criada
        // Assim, precisamos buscar o elemento através de findViewById
        val recycler = binding.recyclerAllGuests

        // Atribui um layout que diz como a RecyclerView se comporta
        recycler.layoutManager = LinearLayoutManager(context)

        // Defini um adapater - Faz a ligação da RecyclerView com a listagem de itens
        recycler.adapter = mAdapter

        mListener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUEST.ID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.EMPTY)
            }
        }

        // Cria os observadores
        observe()

        mAdapter.attachListener(mListener)
        return root
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.EMPTY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Cria os observadores
     */
    private fun observe() {
        mViewModel.guestList.observe(viewLifecycleOwner, {
            mAdapter.updateGuests(it)
        })
    }
}