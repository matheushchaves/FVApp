package com.forcadevenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import com.forcadevenda.bd.TbProdutos;
import com.forcadevenda.classes.Produto;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GApi;

public class ProdutoForm extends GFormActivity<Produto,TbProdutos> {
    
    private EditText etDescricao;    
    private EditText etCodigoImport;
    private EditText etGrupo;    
    private EditText etCodBarras;
    private EditText etPrecoVenda;    
    private EditText etEstoque;
    private EditText etUn;    
    private EditText etUrl;
    private EditText etObs;
    
    private ImageButton btUrl;
    
    public static void call(Activity act, Long id){
        try{    
            Intent i = new Intent(act, ProdutoForm.class);            
            
            if (id != null)
                i.putExtra(G.EXTRAID, id);            
            
            act.startActivity(i);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.erroformexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle, R.layout.produtoform);        
    }
    
    @Override
    public void inicializar(){
        tabela = new TbProdutos(this);
        
        etDescricao = (EditText) findViewById(R.id.etDescricao);        
        etCodigoImport = (EditText) findViewById(R.id.etCodigoImport);
        etGrupo = (EditText) findViewById(R.id.etGrupo);    
        etCodBarras = (EditText) findViewById(R.id.etCodBarras);
        etPrecoVenda = (EditText) findViewById(R.id.etPrecoVenda);    
        etEstoque = (EditText) findViewById(R.id.etEstoque);
        etUn = (EditText) findViewById(R.id.etUn);    
        etUrl = (EditText) findViewById(R.id.etUrl);
        etObs = (EditText) findViewById(R.id.etObs);        
        
        btUrl = (ImageButton) findViewById(R.id.btUrl);
        
        btUrl.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                GApi.navegar(ProdutoForm.this, etUrl.getText().toString());
            }
        });
        
        etDescricao.requestFocus();        
        
        super.inicializar();
    }
    
    @Override
    protected void recuperar() {
        cadastro = (Produto)tabela.get(id);            
    }    
    
    @Override
    protected void criar() {
        
    }
    
    @Override
    protected void popularTela(){        
        etDescricao.setText(cadastro.getDescricao());    
        etCodigoImport.setText(cadastro.getCodigoImport());
        etGrupo.setText(cadastro.getGrupo().getDescricao());    
        etCodBarras.setText(cadastro.getCodBarras());
        etPrecoVenda.setText(cadastro.getStrPrecoVenda());    
        etEstoque.setText(cadastro.getStrEstoque());
        etUn.setText(cadastro.getUn());    
        etUrl.setText(cadastro.getUrl());
        etObs.setText(cadastro.getObs());        
    }
    
    @Override
    protected void popularObjeto(){
        
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);
        miSalvar.setVisible(false);
        miCancelar.setVisible(false);
        return true;
    }
    
}
