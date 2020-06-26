package sk3a280434.eventWork03

import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*
import sk3a280434.eventWork03.topFragment.Companion.EXTRA_MESSAGE
import java.lang.Exception

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //値受け取り
        //val intent:Intent = Intent()
        val Id: Int? = intent.getIntExtra("EXTRA_MESSAGE",0)

        //戻るボタン
        backButton.setOnClickListener {
            onBackPressed()
        }

        var name:String = "" //イベント名
        var user:String = ""//開催者名
        var genre_id:String = "" //ジャンル
        var date:String = ""  //開催期間
        var place:String = "" //場所
        var url:String = ""  //URL
        var image_id:String = ""  //雰囲気
        var poster:String = ""  //path
        var detail:String = ""  //詳細
        var favorite:Int = 0 //お気に入り

        //詳細を取り出して表示

        try{
            val dbHelper = PostDbHelper(this)
            val database = dbHelper.readableDatabase
            val sql = "SELECT name,user,genre_id,begin_date,end_date,place,url,image_id,poster,detail,favorite from events where id = " + Id


            val cursor = database.rawQuery(sql, null)
            if (cursor.count > 0) {
                cursor.moveToFirst()
                name = cursor.getString(0)
                user = cursor.getString(1)
                genre_id = cursor.getString(2)
                date = cursor.getString(3) + "～" + cursor.getString(4)
                place = cursor.getString(5)
                url = cursor.getString(6)
                image_id = cursor.getString(7)
                poster = cursor.getString(8)
                detail = cursor.getString(9)
                favorite = cursor.getInt(10)
            }

            nameText.text = name
            userText.text = user
            ganreText.text = genre_id
            dateText.text = date
            placeText.text = place
            urlText.text = url
            detailText.text = detail
            val imageNames = "/data/data/sk3a280434.eventWork03/files/" + poster
            val bmp = BitmapFactory.decodeFile(imageNames)
            imageView.setImageBitmap(bmp)

            database.close()
        }catch (e: Exception){
            e.printStackTrace()
        }

        if(favorite == 1){
            favoriteButton.text = "☑お気に入り登録済"
        }

        //お気に入り登録
        favoriteButton.setOnClickListener {
            if(favorite == 0){
                changeFavorite(1, Id)
                favoriteButton.text = "☑お気に入り登録済"
            }else{
                changeFavorite(0, Id)
                favoriteButton.text = "□お気に入り登録"
            }
        }




    }

    fun changeFavorite(favorite :Int, id :Int?){
        try{
            val dbHelper = PostDbHelper(this)
            val database = dbHelper.writableDatabase
            var value = ContentValues()
            value.put("favorite",favorite)

            var whereClause = "id = " + id

            database.update("events", value, whereClause,null)
        }catch(e:Exception){
            e.printStackTrace()
        }


    }

    //前のアクテビティに戻る
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
