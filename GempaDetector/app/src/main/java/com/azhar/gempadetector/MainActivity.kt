package com.azhar.gempadetector

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.azhar.gempadetector.adapter.GempaAdapter
import com.azhar.gempadetector.api.ApiInstance
import com.azhar.gempadetector.model.DataGempa
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        website.setOnClickListener(this)

        val getData = ApiInstance.create().getData()
        getData.enqueue(object : Callback<DataGempa.Infogempa> {
            override fun onFailure(call: Call<DataGempa.Infogempa>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(
                call: Call<DataGempa.Infogempa>,
                response: Response<DataGempa.Infogempa>
            ) {
                val layoutManager = LinearLayoutManager(this@MainActivity)
                val offside = ItemOffsetDecoration(20)
                val adapter = GempaAdapter(response.body()!!.gempa!!, itemClick)
                list_gempa.apply {
                    setLayoutManager(layoutManager)
                    addItemDecoration(offside)
                    setAdapter(adapter)
                }
                progress_loader.visibility = View.GONE
            }
        })

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.website -> {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://github.com/AzharRivaldi")
                }
                startActivity(intent)
            }
        }
    }

    private val itemClick = object : ItemClick {
        override fun OnItemClickRecycler(gempa: DataGempa.Gempa) {
            val snackbar = Snackbar.make(
                main_layout,
                "Dirasakan (skala MMI): ${gempa.dirasakan}",
                Snackbar.LENGTH_INDEFINITE
            )
            val snackView = snackbar.view
            val textMsg = snackView.findViewById<TextView>(R.id.snackbar_text)
            textMsg.maxLines = 20
            snackbar.setAction("Tutup") {
                snackbar.dismiss()
            }
            snackbar.show()
        }
    }

    private fun onError(it: Throwable?) {
        Log.d("Gagal mendapatkan data!", it!!.message)
    }
}
