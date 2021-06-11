package ColorSpuit

import ColorSpuit.listeners.ColorListener
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.spuit_example_color_mixing.*
import kotlinx.android.synthetic.main.spuit_example_color_mixing.*
import kotlinx.android.synthetic.main.spuit_multi_color_picker_view_example.*
import kotlinx.android.synthetic.main.spuit_multi_color_picker_view_example.multiColorPickerView
import java.io.FileNotFoundException
import java.R


/**
 * Created by skydoves on 2017-10-21.
 * Copyright (c) 2017 skydoves rights reserved.
 */

class ExampleColorMixing : AppCompatActivity() {

    var flag: Boolean = true
    val RESULT_LOAD_IMG = 1000



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spuit_example_color_mixing)

        multiColorPickerView.addSelector(ContextCompat.getDrawable(this, R.drawable.wheel), colorListener0)
//        multiColorPickerView.addSelector(ContextCompat.getDrawable(this, R.drawable.wheel), colorListener1)
        multiColorPickerView.setFlagView(CustomFlag(this, R.layout.spuit_layout_flag))
        multiColorPickerView.setFlagMode(MultiColorPickerView.FlagMode.LAST)
        multiColorPickerView.setFlagFlipable(false)
//        palette.setOnClickListener {
//            when(flag) {
//                true -> {
//                    flag = false
//                    multiColorPickerView.setPaletteDrawable(ContextCompat.getDrawable(this, R.drawable.watercolor)!!)
//                }
//                false -> {
//                    flag = true
//                    multiColorPickerView.setPaletteDrawable(ContextCompat.getDrawable(this, R.drawable.palette)!!)
//                }
//            }
//        }
//
        gallery.setOnClickListener {
            val  photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
        }



        rgb_fin.setOnClickListener{
            Log.v("R :",  ""+multiColorPickerView.colorEnvelope.rgb[0].toString())
            Log.v("G :",  ""+multiColorPickerView.colorEnvelope.rgb[1].toString())
            Log.v("B :",  ""+multiColorPickerView.colorEnvelope.rgb[2].toString())
        }

//        mixing.setOnClickListener { mixed.setBackgroundColor(multiColorPickerView.getMixedColor(0.6f)) }
    }

    private val colorListener0 = object: ColorListener {
        override fun onColorSelected(envelope: ColorEnvelope) {
            color1.setBackgroundColor(envelope.color)

        }
    }

//    private val colorListener1 = object: ColorListener {
//        override fun onColorSelected(envelope: ColorEnvelope) {
//            color2.setBackgroundColor(envelope.color)
//        }
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri = data!!.data
                val imageStream = imageUri?.let { contentResolver.openInputStream(it) }
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                val drawable = BitmapDrawable(resources, selectedImage)
                multiColorPickerView.setPaletteDrawable(drawable)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }





}
