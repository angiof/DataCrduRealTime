package com.example.datacrdurealtime.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ViewModelsCrud(application: Application) : AndroidViewModel(application) {

    val repositorio: Repositorio = Repositorio(application)

    val lista = repositorio.getLista()

}