package com.forcadevenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import com.forcadevenda.bd.TbRegioes;
import com.forcadevenda.uteis.G;
import com.forcadevenda.classes.Regiao;

public class RegiaoForm extends GFormActivity<Regiao,TbRegioes> {
    
    private EditText etNome;
    
    public static void call(Activity act, int idChamada, Long id){
        try{    
            Intent i = new Intent(act, RegiaoForm.class);            
            
            if (id != null)
                i.putExtra(G.EXTRAID, id);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.erroformexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle, R.layout.regiaoform);        
    }
    
    @Override
    public void inicializar(){
        tabela = new TbRegioes(this);
        
        etNome = (EditText) findViewById(R.id.etNome);        
        etNome.requestFocus();        
        
        super.inicializar();
    }
    
    @Override
    protected void recuperar() {
        cadastro = (Regiao)tabela.get(id);            
    }    
    
    @Override
    protected void criar() {
        cadastro = new Regiao("");
    }
    
    @Override
    protected void popularTela(){
        etNome.setText(cadastro.getNome());
    }
    
    @Override
    protected void popularObjeto(){
        cadastro.setNome(etNome.getText().toString());        
    }
    
}
