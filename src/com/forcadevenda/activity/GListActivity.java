package com.forcadevenda.activity;

import com.forcadevenda.uteis.G;
import android.app.ListActivity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.forcadevenda.bd.*;
import com.forcadevenda.classes.GRegistro;
import com.forcadevenda.uteis.GApi;
import java.util.List;

public abstract class GListActivity<O extends GRegistro> extends ListActivity {

    protected GTabela<O> tabela;
    
    private int posicao = G.INTNULL;
    private View view = null;
    private String filtro;  
    private ContentValues ctvFiltro;
    private String ordem;
    
    protected boolean isSelecao;
    
    protected List<O> lista;        
    
    protected MenuItem miIncluir;
    protected MenuItem miEditar;
    protected MenuItem miDeletar;
    protected MenuItem miVer;
    protected MenuItem miSelecionar;
    protected MenuItem miFiltrar;
    protected MenuItem miOrdenar;
    protected MenuItem miOk;
    
    public void onCreate(Bundle icicle, int layout) {
        super.onCreate(icicle);
        setContentView(layout);
        
        inicializar();              
        
        popularTela();
    }    
    
    protected void inicializar(){    
        isSelecao = false;
        Bundle extras = getIntent().getExtras();        
        if (extras != null)
            isSelecao = extras.getBoolean(G.EXTRASELECAO);                                    
        
        ctvFiltro = new ContentValues();
        
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {                        
                        GApi.vibrar(GListActivity.this);
                        setPosicao(pos, v);
                        longItemClick(pos, v);
                        return false;
                    }
        });          
    }
    
    protected void popularTela(){
        lista = tabela.listar(filtro, ordem);
        setListAdapter(adapter());                        
        
        setPosicao(G.INTNULL, null);
    }    
    
    protected void setPosicao(int p, View v) {
        if (view != null)
            view.setBackgroundColor(G.getColor(this, R.color.fundo));
        
        posicao = p;
        view = v;
        
        if (view != null)
            view.setBackgroundColor(G.getColor(this, R.color.linhaselecionada));        
    }    
    
    protected int getPosicao(){
        return posicao;
    }
    
    protected void setFiltro(String filtro){
        this.filtro = filtro;
    }        
    
    protected String getFiltro(){
        return filtro;
    }

    public ContentValues getCtvFiltro() {
        return ctvFiltro;
    }

    public void setCtvFiltro(ContentValues ctvFiltro) {
        this.ctvFiltro = ctvFiltro;
    }
    
    protected void setOrdem(String ordem){
        this.ordem = ordem;
    }    
    
    protected String getOrdem(){
        return ordem;
    }    
    
    protected boolean isSelected(){
        return (getPosicao() != G.INTNULL);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int posicao, long id) {
	super.onListItemClick(l, v, posicao, id);        
        setPosicao(posicao, v);                        
        openOptionsMenu();
    }            
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
	miIncluir = menu.add(0, G.IDINCLUIR, 0, R.string.menuincluir).setIcon(R.drawable.incluir).setVisible(false);
	miEditar = menu.add(0, G.IDEDITAR, 0, R.string.menueditar).setIcon(R.drawable.editar).setVisible(false);
        miDeletar = menu.add(0, G.IDDELETAR, 0, R.string.menudeletar).setIcon(R.drawable.deletar).setVisible(false);
        miVer = menu.add(0, G.IDVER, 0, R.string.menuver).setIcon(R.drawable.ver).setVisible(false);
        miSelecionar = menu.add(0, G.IDSELECIONAR, 0, R.string.menuselecionar).setIcon(R.drawable.selecionar).setVisible(false);
        miFiltrar = menu.add(0, G.IDFILTRAR, 0, R.string.menufiltrar).setIcon(R.drawable.filtrar).setVisible(false);
        miOrdenar = menu.add(0, G.IDORDENAR, 0, R.string.menuordenar).setIcon(R.drawable.ordenar).setVisible(false);
        miOk = menu.add(0, G.IDOK, 0, R.string.menuok).setIcon(R.drawable.selecionar).setVisible(false);
        
	return true;
    }       
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {    
        switch (item.getItemId()) {
            case G.IDINCLUIR: incluir(); break;
            case G.IDEDITAR: editar(); break;
            case G.IDDELETAR: deletar(); break;
            case G.IDVER: ver(); break;
            case G.IDSELECIONAR: selecionar(); break;
            case G.IDFILTRAR: filtrar(); break;
            case G.IDORDENAR: classificar(); break;
            case G.IDOK: finish(); break;
        }
        return true;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        tabela.close();
    }    
   
    protected abstract void incluir();
    protected abstract void editar();
    protected abstract void deletar();
    protected abstract void ver();    
    protected abstract void selecionar();
    protected abstract void filtrar();
    protected abstract void classificar();
    
    protected abstract void longItemClick(int pos, View v);    
    
    @SuppressWarnings("rawtypes")
	protected abstract GAdapter adapter();    
}
