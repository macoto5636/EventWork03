package sk3a280434.eventWork03


import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_add.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class activity_add : AppCompatActivity() {

    //今日の日付
    val date = Calendar.getInstance()
    val todayYear = date.get(Calendar.YEAR)
    val todayMonth = date.get(Calendar.MONTH)
    val todayDATE = date.get(Calendar.DATE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        //開始日と終了日の初期値セット
        textStateDate.setText(todayYear.toString() + "/" + (todayMonth+1).toString() + "/" + todayDATE.toString())
        textEndDate.setText(todayYear.toString() + "/" + (todayMonth+1).toString() + "/" + (todayDATE+1).toString())


        //戻るボタン
        backButton.setOnClickListener {
            onBackPressed()
        }

        //開始日の日付選択ボタン
        buttonStateDate.setOnClickListener{
            showDatePickerDialog(textStateDate)
        }

        //終了日の日付選択ボタン
        buttonEndDate.setOnClickListener {
            showDatePickerDialog(textEndDate)
        }

        //画像取得ボタン押下時
        buttonAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, READ_REQUEST_CODE)

        }

        //登録ボタン押下時
        buttonAdd.setOnClickListener {
            onClickResist()
        }


    }

    companion object{
        private const val READ_REQUEST_CODE: Int = 42
    }

    //画像が選択されたとき、ImageViewに画像を表示
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK){
            return
        }
        when(requestCode){
            READ_REQUEST_CODE -> {
                try{
                    data?.data?.also{ uri ->
                        val inputStream = contentResolver?.openInputStream(uri)
                        val image = BitmapFactory.decodeStream(inputStream)
                        val imageView = findViewById<ImageView>(R.id.imageViewGet)
                        imageView.setImageBitmap(image)
                    }
                }catch (e:Exception){
                    Toast.makeText(this,"エラーが発生しました",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    //datePicker呼び出し
    fun showDatePickerDialog(id :TextView){
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener(){
                    view, year, month, day-> id.setText(("${year}/${month+1}/${day}"))
            },
            todayYear,
            todayMonth,
            todayDATE
        )
        datePickerDialog.show()
    }

    //前のアクテビティに戻る
    override fun onBackPressed() {
        super.onBackPressed()
    }

    //登録押されたときの処理
    fun onClickResist(){
        try {

            //余裕があったらここで入力チェック

            //画像保存
            val bmp :Bitmap = imageViewGet.drawable.toBitmap()
            val byteArrayOutputStream = ByteArrayOutputStream()
            val fineName =
                SimpleDateFormat("ddHHmmss", Locale.JAPAN).format(Date()).toString() + ".png"
            val outputStream = openFileOutput(fineName, Context.MODE_PRIVATE)
            bmp!!.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.write(byteArrayOutputStream.toByteArray())
            outputStream.close()

            //DB接続
            val dbHelper = PostDbHelper(applicationContext)
            val database = dbHelper.writableDatabase

            //Insert
            val values = ContentValues()
            values.put("name", editTitle.text.toString())
            values.put("user",editUser.text.toString())
            values.put("genre_id",spinnerGenre.selectedItem.toString())
            values.put("begin_date",textStateDate.text.toString())
            values.put("end_date",textEndDate.text.toString())
            values.put("place",editPlace.text.toString())
            values.put("url",editURL.text.toString())
            values.put("image_id",spinnerImage.selectedItem.toString())
            values.put("poster",fineName)
            values.put("detail",editDetail.text.toString())
            values.put("favorite", 0)

            var ret = 0
            try{
                var rett = database.insert("events", null, values)
                ret = rett.toInt()
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                database.close()
            }

            if(ret == -1){
                Toast.makeText(this,"失敗", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"成功", Toast.LENGTH_SHORT).show()
            }

            onBackPressed()

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}
