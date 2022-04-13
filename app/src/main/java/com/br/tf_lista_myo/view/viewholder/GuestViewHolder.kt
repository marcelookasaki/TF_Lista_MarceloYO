package com.br.tf_lista_myo.view.viewholder

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.br.tf_lista_myo.R
import com.br.tf_lista_myo.databinding.RowGuestBinding
import com.br.tf_lista_myo.service.model.GuestModel
import com.br.tf_lista_myo.view.listener.GuestListener

class GuestViewHolder(
    private val itemBinding: RowGuestBinding, private val listener: GuestListener
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(guest: GuestModel) {

        // Atribui valores
        itemBinding.textName.text = guest.name

        // Atribui eventos
        itemBinding.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        // Atribui eventos
        itemBinding.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_convidado)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover) { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()

            true
        }
    }
}