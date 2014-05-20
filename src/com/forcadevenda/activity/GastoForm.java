package com.forcadevenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import com.forcadevenda.bd.TbGastos;
import com.forcadevenda.classes.Gasto;
import com.forcadevenda.uteis.G;

public class GastoForm extends GFormActivity<Gasto,TbGastos> {
    
    private AutoCompleteTextView etDescricao;    
    private EditText etQtde;
    private EditText etValor;
    private EditText etValorTotal;
    
    public static void call(Activity act, int idChamada, Long id){
        try{    
            Intent i = new Intent(act, GastoForm.class);            
            
            if (id != null)
                i.putExtra(G.EXTRAID, id);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.erroformexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle, R.layout.gastoform);        
    }
    
    @Override
    public void inicializar(){
        tabela = new TbGastos(this);
        
        etDescricao = (AutoCompleteTextView) findViewById(R.id.etDescricao);        
        etQtde = (EditText) findViewById(R.id.etQtde);        
        etValor = (EditText) findViewById(R.id.etValor);        
        etValorTotal = (EditText) findViewById(R.id.etValorTotal); 
        
        TextWatcher tw = new TextWatcher() {  
            public void afterTextChanged(Editable s) {  
                etValorTotal.setText(G.formatDouble(G.getDoubleForEditText(etQtde) * G.getDoubleForEditText(etValor)));                  
            }  
  
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {    
            }  
  
            public void onTextChanged(CharSequence s, int start, int before, int count) {    
            }  
        };
        
        etQtde.addTextChangedListener(tw);         
        etValor.addTextChangedListener(tw);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, (new TbGastos(this).listarDescricoes()));
        etDescricao.setAdapter(adapter);
                
        super.inicializar();
    }
    
    @Override
    protected void recuperar() {
        cadastro = (Gasto)tabela.get(id);            
    }    
    
    @Override
    protected void criar() {
        cadastro = new Gasto("");        
    }
    
    @Override
    protected void popularTela(){        
        etDescricao.setText(cadastro.getDescricao());    
        etQtde.setText(cadastro.getStrQtde().replace(",", "."));
        etValor.setText(cadastro.getStrValor().replace(",", "."));
        
        etDescricao.setSelection(etDescricao.getText().length());
    }
    
    @Override
    protected void popularObjeto(){
        cadastro.setDescricao(etDescricao.getText().toString());
        cadastro.setQtde(G.getDoubleForEditText(etQtde));
        cadastro.setValor(G.getDoubleForEditText(etValor));
    }
    
}
