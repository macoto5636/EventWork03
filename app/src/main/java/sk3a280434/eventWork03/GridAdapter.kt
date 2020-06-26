package sk3a280434.eventWork03

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView


class GridAdapter : BaseAdapter{

    var context : Context? = null
    var bmpList: ArrayList<String> = arrayListOf()
    var idList: ArrayList<Int> = arrayListOf()

    constructor(context: Context, bmpList : ArrayList<String>, idList: ArrayList<Int>) : super(){
        this.context = context
        this.bmpList = bmpList
        this.idList = idList
    }

    override fun getCount(): Int {
        return bmpList.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    fun getId(position: Int): Int{
        return idList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        /* 画像表示用のImageView */
        val imageView: ImageView
        if (convertView == null) {
            // imageViewの新規生成
            imageView = ImageView(context)
            imageView.setLayoutParams(AbsListView.LayoutParams(300, 420))
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        } else {
            imageView = convertView as ImageView
        }

        // ImageViewに画像ファイルを設定(Picassoを使って画像を表示)
        val imageNames = "/data/data/sk3a280434.eventWork03/files/" + bmpList[position]
        val bmp = BitmapFactory.decodeFile(imageNames)
        imageView.setImageBitmap(bmp)
        return imageView
    }

}