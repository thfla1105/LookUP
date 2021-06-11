package styleList.ui.main

//import com.styleList.androiddata.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import styleList.LOG_TAG
import styleList.data.Stylelist
import styleList.ui.shared.SharedViewModel
import styleList.utilities.PrefsHelper
import java.R

class MainFragment() : Fragment(),
        MainRecyclerAdapter.StylelistItemListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var navController: NavController
    private lateinit var adapter: MainRecyclerAdapter





    fun newInstance(style: String):MainFragment? {
        val fragment = MainFragment()
        val args = Bundle()
        Companion.style=style
        args.putString("style", style)
        fragment.setArguments(args)
        return fragment
    }


    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)

        }




        val view = inflater.inflate(R.layout.stylelist_ui_fragment, container, false)
        setHasOptionsMenu(true) //0606 원래 true 였음ㅡ디폴트 옵션바

        recyclerView = view.findViewById(R.id.recyclerView)
        val layoutStyle = PrefsHelper.getItemType(requireContext())
        recyclerView.layoutManager =
                if (layoutStyle == "grid") {
                    GridLayoutManager(requireContext(), 2)
                } else {
                    LinearLayoutManager(requireContext())
                }

        navController = Navigation.findNavController(
                requireActivity(), R.id.nav_host
        )

        swipeLayout = view.findViewById(R.id.swipeLayout)
        swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        viewModel.stylelistData.observe(this, Observer
        {

            adapter = MainRecyclerAdapter(requireContext(), it,this, Companion.style.toString())
            recyclerView.adapter = adapter
            swipeLayout.isRefreshing = false

        })
        viewModel.activityTitle.observe(this, Observer {
            requireActivity().title = it
        })

        return view
    }

    override fun onMonsterItemClick(stylelist: Stylelist) {
        Log.i(LOG_TAG, "Selected stylelist: ${stylelist.imageFile}")
        viewModel.selectedStylelist.value = stylelist
        //this.navController.navigate(R.id.action_nav_detail)



        if (findNavController().currentDestination?.id == R.id.mainFragment) {
            findNavController().navigate(R.id.action_nav_detail)
        } //0606 아이템 클릭하면 옵션바 바뀌는 거 ㅡ 이거 없애니까 첫옵션바 유지됨 == 클릭안됨
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    } //0606 이거 없애면 그리드, 리스트 아이콘 안뜸. 몬스터 클릭하면 왼쪽 뒤로가기 화살표는 뜸


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_view_grid -> {
                PrefsHelper.setItemType(requireContext(), "grid")
                recyclerView.layoutManager =
                        GridLayoutManager(requireContext(), 2)
                recyclerView.adapter = adapter
            }
            R.id.action_view_list -> {
                PrefsHelper.setItemType(requireContext(), "list")
                recyclerView.layoutManager =
                        LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
            }

            R.id.action_settings -> {
                //navController.navigate(R.id.nextActivity)
                navController.navigate(R.id.moveActivity)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateActivityTitle()
    }

    companion object {
        var style:String?=null
    }

}
