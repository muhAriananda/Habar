package id.aria.habar.utils

import id.aria.habar.R
import id.aria.habar.ui.news.*

object DataDummy {

    val listFragment = listOf(
        TodayFragment(),
        BusinessFragment(),
        EntertainmentFragment(),
        HealthFragment(),
        ScienceFragment(),
        SportsFragment(),
        TechnologyFragment()
    )

    var listTitle = listOf(
        R.string.today,
        R.string.business,
        R.string.entertainment,
        R.string.health,
        R.string.science,
        R.string.sports,
        R.string.technology
    )
}