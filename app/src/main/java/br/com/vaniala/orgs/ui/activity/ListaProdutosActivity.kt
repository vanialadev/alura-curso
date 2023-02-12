package br.com.vaniala.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.vaniala.orgs.R
import br.com.vaniala.orgs.database.AppDatabase
import br.com.vaniala.orgs.databinding.ActivityListaProdutosBinding
import br.com.vaniala.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Vânia Almeida (Github: @vanialadev)
 * on 19/10/22.
 *
 */
const val TAG: String = "Lista de Produtos"
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
    }

    override fun onResume() {
        super.onResume()
        val scope = MainScope()
        scope.launch {
            val produtos = withContext(Dispatchers.IO) {
                dao.buscaTodos()
            }
            adapter.atualiza(produtos)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_lista_produtos_nome_desc -> {
                adapter.atualiza(dao.buscaTodosOrdenadorPorNomeDesc())
            }
            R.id.menu_lista_produtos_nome_asc -> {
                adapter.atualiza(dao.buscaTodosOrdenadorPorNomeAsc())
            }
            R.id.menu_lista_produtos_descricao_desc -> {
                adapter.atualiza(dao.buscaTodosOrdenadorPorDescricaoDesc())
            }
            R.id.menu_lista_produtos_descricao_asc -> {
                adapter.atualiza(dao.buscaTodosOrdenadorPorDescricaoAsc())
            }
            R.id.menu_lista_produtos_valor_desc -> {
                adapter.atualiza(dao.buscaTodosOrdenadorPorValorDesc())
            }
            R.id.menu_lista_produtos_valor_asc -> {
                adapter.atualiza(dao.buscaTodosOrdenadorPorValorAsc())
            }
            R.id.menu_lista_produtos_sem_ordem -> {
                adapter.atualiza(dao.buscaTodos())
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
    }
}
