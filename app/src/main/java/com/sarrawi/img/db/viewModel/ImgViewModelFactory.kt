package com.sarrawi.img.db.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sarrawi.img.db.repository.ImgRepository

class ViewModelFactory constructor(private val repository: ImgRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(Imgs_ViewModel::class.java)) {
            Imgs_ViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}