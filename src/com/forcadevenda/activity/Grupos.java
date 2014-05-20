package com.forcadevenda.activity;

import com.forcadevenda.uteis.G;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.forcadevenda.bd.*;
import com.forcadevenda.classes.*;

public class Grupos extends GListActivity<Grupo> {
    
    public static void call(Activity act, int idChamada){
        try{
            Intent i = new Intent(act, Grupos.class);            
            
            i.putExtra(G.EXTRASELECAO, true);                
            
            act.startActivityForResult(i, idChamada);
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.errolistaexibir), e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle icicle) {        
        super.onCreate(icicle, R.layout.lista);                        
    }
    
    @Override
    protected void inicializar(){
        tabela = new TbGrupos(this);  
        setFiltro("");
        setOrdem(Grupo.colDescricao);
        
        super.inicializar();
    }  
    
    @Override
    public void longItemClick(int pos, View v){        
        selecionar();
    }          
    
    @Override
    protected void incluir() {
        
    }    
    
    @Override
    protected void editar() {
        
    }

    @Override
    protected void deletar() {
        
    }

    @Override
    protected void ver() {
        
    }       
    
    @Override
    protected void selecionar() {
        if (!isSelected()){
            G.alertar(this, getString(R.string.nadaselecionado));
            return;
        }             
        
        try{        
            setResult(RESULT_OK, new Intent().putExtra(G.EXTRASELECAO, lista.get(getPosicao())));	                    
            finish();                    
        }catch(Exception e){
            G.msgErro(this, G.getString(this, R.string.errolistaselecionar), e.getMessage());
        }        
    }

    @Override
    protected void filtrar() {
        GrupoFiltro.call(this, G.IDFILTRAR, getCtvFiltro());
    }    
    
    @Override
    protected void classificar() {
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);        
        miSelecionar.setVisible(true);        
        miFiltrar.setVisible(true);
        return true;
    }
    
    @Override
    protected void onActivityResult(int idChamada, int retorno, Intent it) {
        super.onActivityResult(idChamada, retorno, it);

        if (retorno == RESULT_OK) {
            
            if (idChamada == G.IDFILTRAR){                
                setCtvFiltro((ContentValues)it.getParcelableExtra(G.EXTRACTVFILTRO));
                String f = it.getStringExtra(G.EXTRASTRFILTRO);
                
                if (f != null){
                    setFiltro(f);
                    popularTela();
                }
            }
            
            G.alertar(this, getString(R.string.sucesso));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    protected GAdapter adapter() {
        return new GruposAdapter(this, lista);
    }
    
}
