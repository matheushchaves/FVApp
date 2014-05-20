package com.forcadevenda.activity;

import com.forcadevenda.uteis.G;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.forcadevenda.bd.*;
import com.forcadevenda.classes.*;
import com.forcadevenda.uteis.GSQL;

public class OrcamentosItens extends GListActivity<OrcamentoItem> {
    
    TextView tvValorTotal;
    
    Long idOrcamento;
    boolean editar;
    
    public static void call(Activity act, int idChamada, long idOrcamento, boolean editar){
        try{
            Intent i = new Intent(act, OrcamentosItens.class);            
            
            i.putExtra(G.EXTRAID, idOrcamento);                
            i.putExtra(G.EXTRAEDITAR, editar);
            
            act.startActivityForResult(i, idChamada);
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.errolistaexibir), e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle icicle) {        
        super.onCreate(icicle, R.layout.orcamentositens);                        
    }
    
    @Override
    protected void inicializar(){
        tabela = new TbOrcamentosItens(this);  
        
        idOrcamento = null;
        editar = false;
        Bundle extras = getIntent().getExtras();        
        if (extras != null){            
            idOrcamento = extras.getLong(G.EXTRAID);                                    
            editar = extras.getBoolean(G.EXTRAEDITAR);
        }
        
        setFiltro(GSQL.filtroId(OrcamentoItem.colOrcamento, idOrcamento));
        setOrdem(OrcamentoItem.colId);
        
        tvValorTotal = (TextView) findViewById(R.id.tvValorTotal);
        
        super.inicializar();
    }

    @Override
    protected void popularTela() {
        super.popularTela();
        tvValorTotal.setText(G.getString(this, R.string.moeda) + G.formatDouble((new TbOrcamentosItens(this)).totalizar(idOrcamento)));
    }
    
    @Override
    public void longItemClick(int pos, View v){        
        editar();
    }          
    
    @Override
    protected void incluir() {
        OrcamentoItemForm.call(this, G.IDINCLUIR, null, idOrcamento);
    }    
    
    @Override
    protected void editar() {
        if (!isSelected()){
            G.alertar(this, getString(R.string.nadaselecionado));
            return;
        }        

	OrcamentoItemForm.call(this, G.IDEDITAR, lista.get(getPosicao()).getId(), idOrcamento);
    }

    @Override
    protected void deletar() {
        if (!isSelected()){
            G.alertar(this, getString(R.string.nadaselecionado));
            return;
        }        
        
        try{
            G.pergunta(this, getString(R.string.perguntadeletar), 
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {                                            
                                    // Ação ---
                                    if (tabela.deletar(lista.get(getPosicao()).getId()))
                                        popularTela();                                                        
                                }
                            }, 
                            G.listenerdimiss);  
        }catch(Exception e){
            G.msgErro(this, G.getString(this, R.string.errolistaexcluir), e.getMessage());
        }
    }

    @Override
    protected void ver() {
        
    }       
    
    @Override
    protected void selecionar() {

    }

    @Override
    protected void filtrar() {
        
    }    
    
    @Override
    protected void classificar() {
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);        
        miIncluir.setVisible(editar);
        miEditar.setVisible(editar);
        miDeletar.setVisible(editar);        
        miOk.setVisible(true);    
        return true;
    }
    
    @Override
    protected void onActivityResult(int idChamada, int retorno, Intent it) {
        super.onActivityResult(idChamada, retorno, it);

       if (retorno == RESULT_OK) {
            if ((idChamada == G.IDEDITAR)||(idChamada == G.IDINCLUIR)){
                popularTela();   
                openOptionsMenu();
            }
            
            G.alertar(this, getString(R.string.sucesso));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    protected GAdapter adapter() {
        return new OrcamentosItensAdapter(this, lista);
    }
    
}
