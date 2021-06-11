package styleList


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import styleList.data.StyleType
import styleList.ui.main.MainFragment
import java.R
import java.util.ArrayList

class StyleActivity : AppCompatActivity() {

    private lateinit var mdata //데이터 모델
            : ArrayList<StyleType>
    private lateinit var style :String

    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager2:ViewPager2
    private lateinit var viewPagerAdapter:styleFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stylelist_layout)


        tabLayout = findViewById(R.id.stylelist_tab)
        viewPager2 = findViewById(R.id.stylelist_viewpager)



        mdata = ArrayList<StyleType>()

        mdata.add(StyleType(1,"casual"))
        mdata.add(StyleType(2,"sporty"))
        mdata.add(StyleType(3,"formal"))
        mdata.add(StyleType(4,"feminine"))


        viewPagerAdapter = styleFragmentAdapter(this)//뷰페이저 어뎁터 생성

        viewPager2.adapter= viewPagerAdapter
//        viewPager2!!.setAdapter(viewPagerAdapter) //어뎁터 연결

        viewPager2!!.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL) //스크롤방향


        val tabLayoutMediator = TabLayoutMediator(this!!.tabLayout!!, this!!.viewPager2!!, true, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            // 텝 레이아웃이랑 연결
            when (position) {
                0 -> {
                    tab.text = "Casual" //텝레이아웃 상단 타이틀 제목 설정
                }
                1 -> {
                    tab.text = "Sporty"
                }
                2 -> {
                    tab.text = "Formal"
                }
                3 -> {
                    tab.text = "Feminine"
                }
            }
        })

        tabLayoutMediator.attach()

        /*
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            //뷰페이저 해당 포지션 위치
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        style="Casual"
                    }
                    1 -> {
                        style="Sporty"
                    }
                    2 -> {
                        style="Formal"
                    }
                    3 -> {
                        style="Feminine"
                    }
                }
            }
        })*/

    }

    inner class styleFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            when(position){
                0 ->{MainFragment.style="Casual"
                    return MainFragment()}
                1 ->{MainFragment.style="Sporty"
                    return MainFragment()}
                2 ->{MainFragment.style="Formal"
                    return MainFragment()}
                else->{MainFragment.style="Feminine"
                    return MainFragment()}
            }

        }
    }




}
