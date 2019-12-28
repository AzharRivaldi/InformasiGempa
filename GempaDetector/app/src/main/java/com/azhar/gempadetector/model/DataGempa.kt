package com.azhar.gempadetector.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

class DataGempa {

    @Root(name = "point")
    class Point {
        @field:Element(name = "coordinates")
        var coordinates: String? = null
    }

    @Root(name = "Gempa", strict = false)
    class Gempa {
        @field:Element(name = "Dirasakan")
        var dirasakan: String? = null

        @field:Element(name = "point")
        var point: Point? = null

        @field:Element(name = "Magnitude")
        var magnitude: String? = null

        @field:Element(name = "Tanggal")
        var tanggal: String? = null

        @field:Element(name = "Posisi")
        var posisi: String? = null

        @field:Element(name = "Kedalaman")
        var kedalaman: String? = null

        @field:Element(name = "Keterangan")
        var keterangan: String? = null

        @field:Element(name = "_symbol")
        var symbol: String? = null
    }

    @Root
    class Infogempa {
        @field:ElementList(name = "Gempa", inline = true)
        var gempa: ArrayList<Gempa>? = null
    }

}