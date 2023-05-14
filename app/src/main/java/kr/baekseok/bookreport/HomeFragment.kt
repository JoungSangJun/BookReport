package kr.baekseok.bookreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.baekseok.bookreport.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var hBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        hBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return hBinding.root
    }
}