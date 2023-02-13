package br.com.vaniala.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import br.com.vaniala.orgs.R
import br.com.vaniala.orgs.database.AppDatabase
import br.com.vaniala.orgs.databinding.ActivityListaProdutosBinding
import br.com.vaniala.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */

class ListaProdutosActivity : UsuarioBaseActivity() {
    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        lifecycleScope.launch {
            launch {
                usuario
                    .filterNotNull()
                    .collect {
                        buscaProdutosUsuario()
                    }
            }
        }
    }

    private suspend fun buscaProdutosUsuario() {
        val produtos = produtoDao.buscaTodos()
        produtos.collect {
            adapter.atualiza(it)
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
                    produtoDao.buscaTodosOrdenadorPorNomeDesc()
                }
                R.id.menu_lista_produtos_nome_asc -> {
                    produtoDao.buscaTodosOrdenadorPorNomeAsc()
                }
                R.id.menu_lista_produtos_descricao_desc -> {
                    produtoDao.buscaTodosOrdenadorPorDescricaoDesc()
                }
                R.id.menu_lista_produtos_descricao_asc -> {
                    produtoDao.buscaTodosOrdenadorPorDescricaoAsc()
                }
                R.id.menu_lista_produtos_valor_desc -> {
                    produtoDao.buscaTodosOrdenadorPorValorDesc()
                }
                R.id.menu_lista_produtos_valor_asc -> {
                    produtoDao.buscaTodosOrdenadorPorValorAsc()
                }
                R.id.menu_lista_produtos_sem_ordem -> {
                    produtoDao.buscaTodos()
                }
                R.id.menu_lista_produtos_sair -> {
                    deslogaUsuario()
                    null
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
                produtoDao.deleta(it)
            }
        }
    }
}
