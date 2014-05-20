package com.forcadevenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.forcadevenda.bd.GTabela;
import com.forcadevenda.classes.GRegistro;
import com.forcadevenda.uteis.G;

public abstract class GFormActivity<O extends GRegistro,T> extends Activity{
    
    @SuppressWarnings("rawtypes")
	protected GTabela tabela;
    
    protected int operacao = G.INTNULL;    
    
    protected O cadastro;
    
    protected Long id;
    
    protected MenuItem miSalvar;
    protected MenuItem miCancelar;
    
    public void onCreate(Bundle icicle, int layout) {
        super.onCreate(icicle);
        setContentView(layout);
        
        inicializar();              
        
        popularTela();
    }     
    
    protected void inicializar(){
        id = null;
        Bundle extras = getIntent().getExtras();        
        if (extras != null)
            id = extras.getLong(G.EXTRAID);                                    
        
        if ((id == null)||(id == 0)){
            operacao = G.IDINCLUIR;
            criar();            
        }
        else{
            operacao = G.IDEDITAR;
            recuperar();            
        }        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
	miSalvar = menu.add(0, G.IDSALVAR,  0, R.string.menusalvar).setIcon(R.drawable.salvar);
	miCancelar = menu.add(0, G.IDCANCELAR, 0, R.string.menucancelar).setIcon(R.drawable.cancelar);
	return true;
    }          
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {    
        switch (item.getItemId()) {
            case G.IDSALVAR: salvar(); break;
            case G.IDCANCELAR: cancelar(); break;            
        }
        return true;
    }    
    
    protected boolean isIncluindo(){
        return (operacao == G.IDINCLUIR);
    }
    
    @SuppressWarnings("unchecked")
	protected void salvar() {	
	popularObjeto();
        
        try{
            if (isIncluindo())
                tabela.incluir(cadastro);
            else
                tabela.editar(cadastro);
            
            setResult(RESULT_OK, new Intent());	                    
            finish();                    
        } catch (Exception e){
            G.msgErro(this, G.getString(this, R.string.erroformsalvar), e.getMessage());
        }
    }
    
    protected void cancelar() {
	setResult(RESULT_CANCELED);	
        finish();
    }    
    
    protected abstract void criar();
    protected abstract void recuperar();
    
    protected abstract void popularTela();
    protected abstract void popularObjeto();
    
}
