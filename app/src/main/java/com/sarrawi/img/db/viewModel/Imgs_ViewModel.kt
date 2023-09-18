package com.sarrawi.img.db.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.db.repository.ImgRepository
import com.sarrawi.img.model.Imgs_Model

class Imgs_ViewModel constructor(private val imgsRepo:ImgRepository): ViewModel() {

    private val retrofitService = ApiService.provideRetrofitInstance()

    private val _response = MutableLiveData<List<Imgs_Model>>()

    private val _responseWithTitle= MutableLiveData<List<Imgs_Model>>()

    suspend fun getAllImgs_ViewModel(ID_Type_id:Int) :MutableLiveData<List<Imgs_Model>> {

        imgsRepo.getImgs_Repo(ID_Type_id).let { response ->

            if (response.isSuccessful) {
                _response.postValue(response.body()?.results)
                Log.i("TestRoom", "getAllImgs: posts ${response.body()?.results}")
            } else {
                Log.i("TestRoom", "getAllImgs: data corrupted")
                Log.d("tag", "getAll Error: ${response.code()}")
                Log.d("tag", "getAll: ${response.body()}")
            }
        }
        return _response
    }

}