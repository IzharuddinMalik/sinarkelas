package com.sinarkelas.ui.dashboardhome.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sinarkelas.R

class FragInformasiMahasiswa : Fragment() {
    companion object{
        fun newInstance() : FragInformasiMahasiswa {
            val fragment = FragInformasiMahasiswa()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.frag_informasi_mahasiswa, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}