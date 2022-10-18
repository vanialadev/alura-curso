package br.com.vaniala.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.vaniala.orgs.databinding.ActivityMainBinding
import br.com.vaniala.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaProdutosAdapter()
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerView.adapter = adapter
        setContentView(binding.root)


    }
}