package kr.baekseok.bookreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookreport.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    private lateinit var aBinding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        aBinding = FragmentAccountBinding.inflate(inflater, container, false)


        return aBinding.root


    }
}