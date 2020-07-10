package id.aria.habar.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.tabs.TabLayoutMediator
import id.aria.habar.R
import id.aria.habar.utils.DataDummy.listFragment
import id.aria.habar.utils.DataDummy.listTitle
import id.aria.habar.utils.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_base.*

class BaseFragment : Fragment(R.layout.fragment_base) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val actionBar = (requireActivity() as AppCompatActivity)
        actionBar.setSupportActionBar(toolbar)
        actionBar.supportActionBar?.setDisplayShowTitleEnabled(false)

        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(listFragment)

        viewpager.adapter = adapter
        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
            tab.text = resources.getString(listTitle[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
                || super.onOptionsItemSelected(item)
    }
}