package kr.baekseok.bookreport

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.baekseok.adapter.ItemSpacingDecoration
import kr.baekseok.adapter.ReportRecyclerAdapter
import kr.baekseok.addreport.BookReportApplication
import kr.baekseok.addreport.ReportAddActivity
import kr.baekseok.bookreport.databinding.FragmentHomeBinding
import kr.baekseok.room.BookReportDao
import kr.baekseok.room.BookReportData
import kr.baekseok.viewmodel.HomeViewModel

// viewModel에 매개변수 전달을 위한 ViewModelFactory 생성
class HomeViewModelFactory(private val booksReportDao: BookReportDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(booksReportDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class HomeFragment : Fragment() {

    private lateinit var hBinding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel
    private lateinit var reportAdapter: ReportRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        hBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val homeViewModelFactory =
            HomeViewModelFactory(booksReportDao = (requireActivity().application as BookReportApplication).bookReportDatabase.bookReportDao())
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        val mAuth: FirebaseAuth = Firebase.auth
        val user: FirebaseUser = mAuth.currentUser!!

        user.getIdToken(true).addOnCompleteListener {//자동 로그인
            if (it.isSuccessful) {
                var idToken: String = it.getResult().token!!
                Toast.makeText(activity, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        setupRecyclerView()

        homeViewModel.reportUiState.observe(viewLifecycleOwner) { newData ->
            val diffResult = DiffUtil.calculateDiff(
                ReportRecyclerAdapter.ReportDiffCallback(
                    reportAdapter.data,
                    newData
                )
            )
            reportAdapter.data = newData
            diffResult.dispatchUpdatesTo(reportAdapter)
        }


        hBinding.fab.setOnClickListener {
            val context = requireContext()
            val intent = Intent(context, ReportAddActivity::class.java)
            startActivity(intent)
        }

        autoLogin()

        return hBinding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = hBinding.reportRecyclerView
        val itemSpacingDecoration = ItemSpacingDecoration(50)
        recyclerView.addItemDecoration(itemSpacingDecoration)
        reportAdapter =
            ReportRecyclerAdapter(
                requireContext(),
                homeViewModel.reportUiState.value ?: listOf(

                ),
                LayoutInflater.from(requireContext())
            )
        recyclerView.adapter = reportAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    fun autoLogin() {

    }

}