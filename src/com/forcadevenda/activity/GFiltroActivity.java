package com.forcadevenda.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.forcadevenda.uteis.G;


public abstract class GFiltroActivity extends Activity{
    
    protected ContentValues filtro;
    
    protected MenuItem miFiltrar;
    protected MenuItem miCancelar;
    protected MenuItem miLimpar;    
    
    public void onCreate(Bundle icicle, int layout) {
        super.onCreate(icicle);       
        
        setContentView(layout);
        
        inicializar();              
        
        popularTela();
    }     
    
    protected void inicializar(){
        filtro = null;
        Bundle extras = getIntent().getExtras();        
        if (extras != null)
            filtro = (ContentValues)extras.getParcelable(G.EXTRACTVFILTRO);
        
        if (filtro == null)
            filtro = new ContentValues();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
	miFiltrar = menu.add(0, G.IDFILTRAR,  0, R.string.menufiltrar).setIcon(R.drawable.filtrar);
	miCancelar = menu.add(0, G.IDCANCELAR, 0, R.string.menucancelar).setIcon(R.drawable.cancelar);
        miLimpar = menu.add(0, G.IDLIMPAR, 0, R.string.menulimpar).setIcon(R.drawable.limpar);
	return true;
    }     
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {    
        switch (item.getItemId()) {
            case G.IDFILTRAR: filtrar(); break;
            case G.IDCANCELAR: cancelar(); break;            
            case G.IDLIMPAR: limpar(); break;            
        }
        return true;
    }
    
    protected void filtrar() {	
	popularObjeto();

        Intent i = new Intent();
        
        i.putExtra(G.EXTRASTRFILTRO, toString());
        i.putExtra(G.EXTRACTVFILTRO, filtro);        
        
        setResult(RESULT_OK, i);	                    
        finish();                    
    }

    protected void cancelar() {
	setResult(RESULT_CANCELED);	
        finish();
    }
    
    protected abstract void limpar();
    
    protected abstract void popularTela();
    protected abstract void popularObjeto();

}
