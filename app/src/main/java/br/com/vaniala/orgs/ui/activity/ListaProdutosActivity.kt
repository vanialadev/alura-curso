package br.com.vaniala.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.vaniala.orgs.R
import br.com.vaniala.orgs.database.AppDatabase
import br.com.vaniala.orgs.databinding.ActivityListaProdutosBinding
import br.com.vaniala.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.launch

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */

class ListaProdutosActivity : AppCompatActivity() {
    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        lifecycleScope.launch {
            val produtos = dao.buscaTodos()
            produtos.collect {
                adapter.atualiza(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch {
            when (item.itemId) {
                R.id.menu_lista_produtos_nome_desc -> {
                    dao.buscaTodosOrdenadorPorNomeDesc()
                }
                R.id.menu_lista_produtos_nome_asc -> {
                    dao.buscaTodosOrdenadorPorNomeAsc()
                }
                R.id.menu_lista_produtos_descricao_desc -> {
                    dao.buscaTodosOrdenadorPorDescricaoDesc()
                }
                R.id.menu_lista_produtos_descricao_asc -> {
                    dao.buscaTodosOrdenadorPorDescricaoAsc()
                }
                R.id.menu_lista_produtos_valor_desc -> {
                    dao.buscaTodosOrdenadorPorValorDesc()
                }
                R.id.menu_lista_produtos_valor_asc -> {
                    dao.buscaTodosOrdenadorPorValorAsc()
                }
                R.id.menu_lista_produtos_sem_ordem -> {
                    dao.buscaTodos()
                }
                else -> null
            }?.collect {
                adapter.atualiza(it)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configuraFab() {
        binding.activityListaProdutosExtendFab.setOnClickListener {
            vaiParaFormulario()
        }
    }

    private fun vaiParaFormulario() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        binding.activityListaProdutosRecyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java,
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }

        adapter.quandoClicaEmEditar = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java,
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
        adapter.quandoClicaEmRemover = {
            lifecycleScope.launch {
                dao.deleta(it)
            }
        }
    }
}
