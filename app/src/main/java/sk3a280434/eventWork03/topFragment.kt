package sk3a280434.eventWork03

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_top.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class topFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    //ID
    private var arrayListId: ArrayList<Int> = arrayListOf()
    private var arrayListPath: ArrayList<String> = arrayListOf()
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    //ポスター情報読み取り
    private fun getData(){
        try {
            arrayListId.clear()
            arrayListPath.clear()
            count = 0

            val dbHelper = PostDbHelper(activity!!)
            val database = dbHelper.readableDatabase

            val sql = "SELECT id, poster from events"

            val cursor = database.rawQuery(sql, null)
            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    arrayListId.add(cursor.getInt(0))
                    arrayListPath.add(cursor.getString(1))
                    count++
                    cursor.moveToNext()

                }
            }

            database.close()

            //GirdViewに表示
            val adapter = GridAdapter(activity!!, arrayListPath, arrayListId)
            gridView.adapter = adapter

            gridView.setOnItemClickListener { adapterView, view, position, id ->
                val intent = Intent(activity!!,DetailActivity::class.java)
                var posisi = position.toInt() + 1
                intent.putExtra("EXTRA_MESSAGE",posisi)
                startActivity(intent)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        const val EXTRA_MESSAGE = "sk3a280434.eventWork03.MESSAGE"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment topFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            topFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //動かなった残骸置き場
    fun gridLayoutView(){
        //Gridviewに描写
        var count_img = 0
        for(i in 0..(count/2).toInt()){
            for(j in 0..1){
                //ImageViewの作成
                val imageView = ImageView(activity)
                var params = GridLayout.LayoutParams()
                params.width = 600
                params.height = 840
                //params.columnSpec = GridLayout.spec(i)
                params.rowSpec = GridLayout.spec(j)

                val imageNames = "/data/data/sk3a280434.eventWork03/files/" + arrayListPath[count_img]
                val bmp = BitmapFactory.decodeFile(imageNames)
                imageView.setImageBitmap(bmp)
                imageView.layoutParams = params
                //grid.addView(imageView)
                count_img++
            }
        }
    }
}
