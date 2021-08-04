package com.vk59.wegotrip_kt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk59.wegotrip_kt.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        initToolbar()
    }


    private fun initToolbar() {
//        mainActivityBinding.toolbar.subtitle = "Аудио-экскурсия"
    }
}