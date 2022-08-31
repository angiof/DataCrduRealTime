package com.example.datacrdurealtime.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.datacrdurealtime.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModelsCrud
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val adapter :MyAdapter=MyAdapter()
        viewModel = ViewModelProvider(this)[ViewModelsCrud::class.java]
        Toast.makeText(this, viewModel.lista.toString(), Toast.LENGTH_SHORT).show()

    }
}