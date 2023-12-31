package com.sarrawi.img

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarrawi.img.Api.ApiService
import com.sarrawi.img.adapter.ImgAdapter
import com.sarrawi.img.adapter.TypesAdapter_T
import com.sarrawi.img.databinding.FragmentSecondBinding
import com.sarrawi.img.databinding.FragmentThirdBinding
import com.sarrawi.img.db.repository.ImgRepository
import com.sarrawi.img.db.viewModel.ImgTypes_ViewModel
import com.sarrawi.img.db.viewModel.Imgs_ViewModel
import com.sarrawi.img.db.viewModel.ViewModelFactory
import kotlinx.coroutines.launch
import android.os.Handler
import android.os.Looper

class ThirdFragment : Fragment() {

    private lateinit var _binding: FragmentThirdBinding

    private val binding get() = _binding

    private val retrofitService = ApiService.provideRetrofitInstance()

    private val mainRepository by lazy {  ImgRepository(retrofitService) }

    private val imgsViewmodel: Imgs_ViewModel by viewModels {
        ViewModelFactory(mainRepository)
    }



    private val imgAdapter by lazy {
        ImgAdapter(requireActivity())
    }

    private var ID_Type_id = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ID_Type_id = ThirdFragmentArgs.fromBundle(requireArguments()).id
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()

        imgsViewmodel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // عرض ProgressBar إذا كان التحميل قيد التقدم
            } else {
                binding.progressBar.visibility = View.GONE // إخفاء ProgressBar إذا انتهى التحميل
            }
        }


    }



    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun setUpRv() = imgsViewmodel.viewModelScope.launch {
        imgsViewmodel.getAllImgsViewModel(ID_Type_id).observe(requireActivity()) { imgs ->
            imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

            if (imgs != null) {
                imgAdapter.img_list = imgs
                if (binding.rvImgCont.adapter == null) {
                    binding.rvImgCont.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.rvImgCont.adapter = imgAdapter
                    imgAdapter.notifyDataSetChanged()
                } else {
                    imgAdapter.notifyDataSetChanged()
                }
            } else {
                // imgs هي قائمة فارغة أو null، يمكنك اتخاذ الإجراء المناسب هنا
            }

            val handler = Handler(Looper.getMainLooper()) // تعريف handler هنا

            handler.postDelayed({
                hideprogressdialog()
            }, 5000)
        }
    }


    fun hideprogressdialog() {
        binding.progressBar.visibility = View.GONE
    }


}