package kr.baekseok.bookreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.baekseok.bookreport.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var sBinding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        sBinding = FragmentSearchBinding.inflate(inflater, container, false)

        return sBinding.root


    }
}