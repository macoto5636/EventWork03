package sk3a280434.eventWork03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //タブの設定
        pager.adapter = TabAdapter(supportFragmentManager, this)
        tab_layout.setupWithViewPager(pager)

        //追加ボタン
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            if(it.itemId == R.id.action_add){
                val intent = Intent(application, activity_add::class.java)
                startActivity(intent)
            }
            true
        }

    }

}
