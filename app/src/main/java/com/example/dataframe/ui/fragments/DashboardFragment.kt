package com.example.dataframe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dataframe.R
import com.example.dataframe.data.models.DashboardItem
import com.example.dataframe.databinding.FragmentDashboardBinding
import com.example.dataframe.ui.adapters.DashboardAdapter
import com.example.dataframe.utils.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment(), ItemClickListener<DashboardItem> {
    private var binding: FragmentDashboardBinding? = null
    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var dashboardItemsList: List<DashboardItem>
    private lateinit var navController: NavController
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        navController = findNavController()
        dashboardItemsList = getDashboardItemsList()
        dashboardAdapter = DashboardAdapter(dashboardItemsList, this)
        gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding!!.dashboardRecyclerView.layoutManager = gridLayoutManager
        binding!!.dashboardRecyclerView.adapter = dashboardAdapter

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(item: DashboardItem) {
        when (item.id) {
            0 -> {
                navController.navigate(R.id.action_dashboardFragment_to_studentFragment)
            }
            1 -> {
                navController.navigate(R.id.action_dashboardFragment_to_schoolCharacteristicsFragment)
            }
            2 -> {
                navController.navigate(R.id.action_dashboardFragment_to_teacherFragment)
            }
            3 -> {
                navController.navigate(R.id.action_dashboardFragment_to_nonTeachingStaffFragment)
            }
            4 -> {
                navController.navigate(R.id.action_dashboardFragment_to_infrastructureFragment)
            }
            5 -> {
                navController.navigate(R.id.action_dashboardFragment_to_teachingMaterialsFragment)
            }
            6 -> {
                navController.navigate(R.id.action_dashboardFragment_to_HIVFragment)
            }
            7 -> {
                navController.navigate(R.id.action_dashboardFragment_to_sportsFragment)
            }
        }
    }

    private fun getDashboardItemsList(): List<DashboardItem> {
        val list = mutableListOf<DashboardItem>()
        list.add(
            DashboardItem(
                id = 0,
                drawable = R.drawable.ic_student,
                title = "Pupils & Students"
            )
        )

        list.add(
            DashboardItem(
                id = 1,
                drawable = R.drawable.ic_school,
                title = "School Characteristics"
            )
        )

        list.add(
            DashboardItem(
                id = 2,
                drawable = R.drawable.ic_teacher,
                title = "Teachers"
            )
        )

        list.add(
            DashboardItem(
                id = 3,
                drawable = R.drawable.ic_staff,
                title = "Non-teaching Staff"
            )
        )

        list.add(
            DashboardItem(
                id = 4,
                drawable = R.drawable.ic_infrastructure,
                title = "Infrastructure"
            )
        )

        list.add(
            DashboardItem(
                id = 5,
                drawable = R.drawable.ic_teaching_materials,
                title = "Teaching Materials"
            )
        )

        list.add(
            DashboardItem(
                id = 6,
                drawable = R.drawable.ic_hiv,
                title = "HIV & AIDs"
            )
        )

        list.add(
            DashboardItem(
                id = 7,
                drawable = R.drawable.ic_sports,
                title = "Sports & P.E"
            )
        )
        return list
    }
}