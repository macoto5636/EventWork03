package sk3a280434.eventWork03

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm:FragmentManager, private  val context: Context): FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> { return topFragment()}
            else -> { return favoriteFrangment()}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> { return "List"}
            else -> { return "Favorite" }
        }
    }

    override fun getCount(): Int {
        return 2
    }
}