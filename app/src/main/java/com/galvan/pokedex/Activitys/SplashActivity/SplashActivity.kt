package com.galvan.pokedex.Activitys.SplashActivity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.galvan.pokedex.Activitys.MainActivity.MainActivity
import com.galvan.pokedex.Activitys.MainActivity.MainActivityViewModel
import com.galvan.pokedex.Database.Web.Events.AllPokemons
import com.galvan.pokedex.R
import com.galvan.pokedex.Widgets.Anim.MaterialUI
import com.galvan.pokedex.databinding.ActivityMainBinding
import com.galvan.pokedex.databinding.ActivitySplashBinding
import com.galvan.pokedex.databinding.DialogErrorBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        var Splash = false
    }

    private val viewModel: SplashActivityViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
    }


    private fun initComponent(){
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashLayout()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Observe()
    }

    private fun Observe(){
        lifecycleScope.launch {
            viewModel.state.collect { result ->
                when(result){

                    AllPokemons.Failed.otherError -> dialogError()
                    AllPokemons.Failed.serverError -> dialogError()
                    AllPokemons.Failed.timeOut -> dialogError()
                    AllPokemons.IDle -> { }
                    AllPokemons.Loading -> {
                        binding.processView.text = getString(R.string.download_label)
                        mainLayout()
                    }
                    is AllPokemons.Succesful -> {

                    }
                    AllPokemons.readyLocall -> {
                        startActivity(Intent(baseContext, MainActivity::class.java))
                        finish()
                    }
                    AllPokemons.savedatabaseLocal -> binding.processView.text = getString(R.string.database_label)
                }
            }
        }
    }

    private fun dialogError(){
        val builder = AlertDialog.Builder(this)
        val binding = DialogErrorBinding.inflate(LayoutInflater.from(this))
        builder.setView(binding.root)
        builder.setCancelable(false)
        val dialog = builder.create()
        binding.btnAcept.setOnClickListener {
            finish()
            return@setOnClickListener
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun mainLayout(){
        MaterialUI.animationMaterial(binding.splashLayout,binding.loadingLayout,binding.main)
    }


    private fun splashLayout(){
        binding.splashLayout.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE
    }
}