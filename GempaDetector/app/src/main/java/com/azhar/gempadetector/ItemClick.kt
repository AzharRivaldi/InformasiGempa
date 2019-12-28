package com.azhar.gempadetector

import com.azhar.gempadetector.model.DataGempa

interface ItemClick {
    fun OnItemClickRecycler(gempa: DataGempa.Gempa)
}