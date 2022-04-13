package com.br.tf_lista_myo.view.viewholder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.br.tf_lista_myo.service.constants.GuestConstants
import com.br.tf_lista_myo.service.model.GuestModel
import com.br.tf_lista_myo.service.repo.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int) {

        if (filter == GuestConstants.FILTER.EMPTY) {
            mGuestList.value = mGuestRepository.getAll()
        } else if (filter == GuestConstants.FILTER.PRESENT) {
            mGuestList.value = mGuestRepository.getPresent()
        } else {
            mGuestList.value = mGuestRepository.getAbsent()
        }
    }

    fun delete(id: Int) {
        mGuestRepository.delete(id)
    }

}