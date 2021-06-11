package ColorSpuit

import android.content.Context
import android.view.View
import android.widget.TextView

import ColorSpuit.ColorEnvelope
import ColorSpuit.FlagView
import java.R

/**
 * Developed by skydoves on 2017-10-24.
 * Copyright (c) 2017 skydoves rights reserved.
 */

class CustomFlag(context: Context, layout: Int) : FlagView(context, layout) {

    private val textView: TextView
    private val view: View

    init {
        this.textView = findViewById(R.id.flag_color_code)
        this.view = findViewById(R.id.flag_color_layout)
    }

    override fun onRefresh(envelope: ColorEnvelope) {
        // 원본 this.textView.text = "#" + envelope.htmlCode
        this.textView.text = envelope.rgb[0].toString()+"/"+envelope.rgb[1].toString()+"/"+envelope.rgb[2].toString()
        this.view.setBackgroundColor(envelope.color)
    }
}
