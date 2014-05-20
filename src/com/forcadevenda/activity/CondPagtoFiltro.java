package com.forcadevenda.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import com.forcadevenda.classes.CondPagto;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GApi;
import com.forcadevenda.uteis.GSQL;

public class CondPagtoFiltro extends GFiltroActivity {
    
    private EditText etNome;  
    
    public static void call(Activity act, int idChamada, ContentValues filtro){
        try{    
            Intent i = new Intent(act, CondPagtoFiltro.class);            
            
            i.putExtra(G.EXTRACTVFILTRO, filtro);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.errofiltroexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle, R.layout.filtro);
    }
    
    @Override
    protected void inicializar(){
        GApi.setBtFala(this, (ImageButton) findViewById(R.id.btFala));        
        
        etNome = (EditText) findViewById(R.id.etNome);        
        etNome.requestFocus();
        
        super.inicializar();            
    }
    
    @Override
    protected void popularTela(){
        etNome.setText(filtro.getAsString(CondPagto.colDescricao));
    }
    
    @Override
    protected void popularObjeto(){
        filtro.put(CondPagto.colDescricao, etNome.getText().toString());
    }
    
    @Override
    protected void limpar() {
        etNome.setText("");
	filtrar();
    }

    @Override
    public String toString() {
        String f = "";
        f = GSQL.filtroAnd(f, GSQL.filtroLike(CondPagto.colDescricao, filtro.getAsString(CondPagto.colDescricao)));
        
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == G.IDGETVOZ){
                final String[] itens = GApi.getResultFala(data);
                G.msgLista(this, itens, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            etNome.setText(itens[which]);
                            openOptionsMenu();
                        }
                    });                
            }
        }
    }      
    
}
