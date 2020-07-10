package id.aria.habar.utils.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var listFragment: MutableList<Fragment> = arrayListOf()

    fun addFragment(listFragment: List<Fragment>) {
        this.listFragment.addAll(listFragment)
    }

    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}