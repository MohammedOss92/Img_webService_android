package com.sarrawi.img

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarrawi.img.adapter.ImgAdapter
import com.sarrawi.img.adapter.TypesAdapter_T
import com.sarrawi.img.databinding.FragmentSecondBinding
import com.sarrawi.img.databinding.FragmentThirdBinding
import com.sarrawi.img.db.viewModel.ImgTypes_ViewModel
import com.sarrawi.img.db.viewModel.Imgs_ViewModel
import kotlinx.coroutines.launch

class ThirdFragment : Fragment() {

    private lateinit var _binding: FragmentThirdBinding

    private val binding get() = _binding

    private val imgsViewmodel: Imgs_ViewModel by lazy {
        ViewModelProvider(requireActivity(), ImgTypes_ViewModel.ImgTypesViewModelFactory(requireActivity().application)).get(
            Imgs_ViewModel::class.java)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()
    }



    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun setUpRv() = imgsViewmodel.viewModelScope.launch {
        imgsViewmodel.getAllImgs_ViewModel(ID_Type_id).observe(requireActivity()){imgs->
            imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
            imgAdapter.img_list = imgs
            if(binding.rvImgCont.adapter == null){


                binding.rvImgCont.layoutManager = GridLayoutManager(requireContext(), 2) // هنا تعيين عدد الأعمدة إلى 2
                binding.rvImgCont.adapter = imgAdapter
                imgAdapter.notifyDataSetChanged()
            }else{
                imgAdapter.notifyDataSetChanged()
            }
        }
    }

}