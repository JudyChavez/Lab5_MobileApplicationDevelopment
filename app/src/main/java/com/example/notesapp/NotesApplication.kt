package com.example.notesapp

import android.app.Application
import com.example.notesapp.data.AppContainer
import com.example.notesapp.data.AppDataContainer

class NotesApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}