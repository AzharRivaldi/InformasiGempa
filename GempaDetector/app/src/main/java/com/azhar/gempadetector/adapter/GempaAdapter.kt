package com.azhar.gempadetector.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhar.gempadetector.ItemClick
import com.azhar.gempadetector.R
import com.azhar.gempadetector.model.DataGempa
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.list_item_gempa.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GempaAdapter(var dataList: ArrayList<DataGempa.Gempa>,
                   var itemClick: ItemClick
) : RecyclerView.Adapter<GempaAdapter.Holder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_item_gempa, p0, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val dataGempa = dataList[p1]

        var patternDate = ""
        var patternFormat = ""

        when {
            dataGempa.tanggal!!.contains("WIB") -> {
                patternDate = "dd/MM/yyyy-HH:mm:ss 'WIB'"
                patternFormat = "EEE dd MMM yyyy / HH:mm:ss 'WIB'"
            }
            dataGempa.tanggal!!.contains("WIT") -> {
                patternDate = "dd/MM/yyyy-HH:mm:ss 'WIT'"
                patternFormat = "EEE dd MMM yyyy / HH:mm:ss 'WIT'"
            }
            dataGempa.tanggal!!.contains("WITA") -> {
                patternDate = "dd/MM/yyyy-HH:mm:ss 'WITA'"
                patternFormat = "EEE dd MMM yyyy / HH:mm:ss 'WITA'"
            }
        }

        val parseDateFormat = SimpleDateFormat(patternDate)
        val dateFormat = SimpleDateFormat(patternFormat, Locale("id"))
        val date = parseDateFormat.parse(dataGempa.tanggal)

        p0.itemView.tanggal.text = dateFormat.format(date)
        p0.itemView.posisi.text = "Koordinat : ${dataGempa.posisi}"
        p0.itemView.magnitude.text = "M ${dataGempa.magnitude}"
        p0.itemView.kedalaman.text = "Kedalaman : ${dataGempa.kedalaman}"
        p0.itemView.keterangan.text = dataGempa.keterangan

        p0.setLocation(dataGempa)
        p0.itemView.setOnClickListener {
            itemClick.OnItemClickRecycler(dataGempa)
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), OnMapReadyCallback {
        private var mapView: MapView = itemView.findViewById(R.id.map_item)
        var gMap: GoogleMap? = null

        lateinit var mapData: DataGempa.Gempa

        init {
            mapView.onCreate(null)
            mapView.getMapAsync(this)
        }

        override fun onMapReady(p0: GoogleMap?) {
            MapsInitializer.initialize(itemView.context)
            gMap = p0!!
            gMap!!.uiSettings.isMapToolbarEnabled = false
            updateLocation()
        }

        fun setLocation(mapLoc: DataGempa.Gempa) {
            mapData = mapLoc

            if (gMap != null) {
                updateLocation()
            }
        }

        private fun updateLocation() {
            gMap!!.clear()
            val stringLatlng = mapData.point?.coordinates?.split(",")
            val lat = stringLatlng?.get(0)?.toDouble()
            val lng = stringLatlng?.get(1)?.toDouble()
            val marker = LatLng(lat!!, lng!!)
            gMap!!.addMarker(MarkerOptions().position(marker).flat(true))
            gMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 5f))
        }
    }

}