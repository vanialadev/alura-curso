package br.com.vaniala.orgs.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.vaniala.orgs.dao.ProdutoDao
import br.com.vaniala.orgs.databinding.ActivityListaProdutosBinding
import br.com.vaniala.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */
class ListaProdutosActivity : AppCompatActivity() {

    private val dao = ProdutoDao()
    private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())


    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
        configuraFab()
    }

    private fun configuraFab() {
        binding.activityListaProdutosFab.setOnClickListener {
            val intent = Intent(this, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configuraRecyclerView() {
        binding.activityListaProdutosRecyclerView.adapter = adapter
        binding.activityListaProdutosRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}