package br.com.vaniala.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.vaniala.orgs.databinding.ActivityMainBinding
import br.com.vaniala.orgs.model.Produto
import br.com.vaniala.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaProdutosAdapter(
            context = this, produtos = listOf(
                Produto("teste1", "desc1", BigDecimal("9.99")),
                Produto("teste2", "desc2", BigDecimal("19.99")),
                Produto("teste3", "desc3", BigDecimal("39.99")),
                Produto("teste4", "desc4", BigDecimal("39.99")),
                Produto("teste5", "desc5", BigDecimal("49.99")),
                Produto("teste6", "desc6", BigDecimal("59.99")),
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        setContentView(binding.root)


    }
}