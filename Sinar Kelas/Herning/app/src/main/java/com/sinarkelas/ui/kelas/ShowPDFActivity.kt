package com.sinarkelas.ui.kelas

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.github.barteksc.pdfviewer.PDFView
import com.sinarkelas.R

class ShowPDFActivity : AppCompatActivity() {

    var pdfFile : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_p_d_f)

        val pdfView = findViewById<PDFView>(R.id.pdfViewer)

        pdfFile = intent.getStringExtra("pdfFile")

        Log.i("TAGURI", " === " + pdfFile)
        var uriPdf = Uri.parse("https://sinarcenter.id/fileuploadapps/" + pdfFile)

        pdfView.fromUri(uriPdf).load()
    }
}