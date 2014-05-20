package com.forcadevenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import com.forcadevenda.bd.TbOrcamentosItens;
import com.forcadevenda.bd.TbOrcamentos;
import com.forcadevenda.classes.OrcamentoItem;
import com.forcadevenda.uteis.G;
import com.forcadevenda.classes.Produto;

public class OrcamentoItemForm extends GFormActivity<OrcamentoItem,TbOrcamentosItens> {
    
    private EditText etProduto;    
    private EditText etQtde;
    private EditText etValorBruto;
    private EditText etPorcDesc;
    private EditText etValor;
    private EditText etValorTotal;
    
    private ImageButton btProcProduto;
    
    private Produto produto;
    
    private Long idOrcamento;
    private double porcDesc;
    
    private final int IDACHAPROD = 1;
    
    public static final String EXTRAIDORCAMENTO = "exIdOrcamento";
    
    public static void call(Activity act, int idChamada, Long id, Long idOrcamento){
        try{    
            Intent i = new Intent(act, OrcamentoItemForm.class);            
            
            i.putExtra(EXTRAIDORCAMENTO, idOrcamento);
            
            if (id != null)
                i.putExtra(G.EXTRAID, id);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.erroformexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        produto = new Produto("");
        
        super.onCreate(icicle, R.layout.orcamentoitemform);        
    }
    
    @Override
    public void inicializar(){
        tabela = new TbOrcamentosItens(this);
        
        idOrcamento = null;
        Bundle extras = getIntent().getExtras();        
        if (extras != null)
            idOrcamento = extras.getLong(EXTRAIDORCAMENTO);                                            
        
        porcDesc = (new TbOrcamentos(this)).get(idOrcamento).getCondPagto().getPorcDesc();        
        
        etProduto = (EditText) findViewById(R.id.etProduto);        
        etQtde = (EditText) findViewById(R.id.etQtde);        
        etValorBruto = (EditText) findViewById(R.id.etValorBruto);        
        etPorcDesc = (EditText) findViewById(R.id.etPorcDesc);        
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

        btProcProduto = (ImageButton) findViewById(R.id.btProcProduto);     
        
        btProcProduto.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Produtos.call(OrcamentoItemForm.this, IDACHAPROD, true);
            }
        }); 
                
        super.inicializar();
        
        btProcProduto.setEnabled(isIncluindo());
    }
    
    @Override
    protected void recuperar() {
        cadastro = (OrcamentoItem)tabela.get(id);            
    }    
    
    @Override
    protected void criar() {
        cadastro = new OrcamentoItem(idOrcamento);        
    }
    
    @Override
    protected void popularTela(){        
        etQtde.setText(cadastro.getStrQtde().replace(",", "."));
        etValorBruto.setText(cadastro.getStrValorBruto().replace(",", "."));        
        etPorcDesc.setText("%" + String.valueOf(porcDesc));        
        etValor.setText(cadastro.getStrValor().replace(",", "."));        
        
        setProduto(cadastro.getProduto());
    }
    
    @Override
    protected void popularObjeto(){
        cadastro.setQtde(G.getDoubleForEditText(etQtde));
        cadastro.setValorBruto(G.getDoubleForEditText(etValorBruto));
        cadastro.setValor(G.getDoubleForEditText(etValor));
        
        cadastro.setProduto(produto);
    }
    
    private void setProduto(Produto produto) {
        if (produto == null)
            produto = new Produto("");
        
        this.produto = produto;
        etProduto.setText(produto.getDescricao());
        etValorBruto.setText(produto.getStrPrecoVenda().replace(",", "."));
        etValor.setText(produto.getStrPrecoVenda(porcDesc).replace(",", "."));
        etValorTotal.setText(G.formatDouble(G.getDoubleForEditText(etQtde) * G.getDoubleForEditText(etValor)));
        
        etQtde.requestFocus();
        etQtde.selectAll();
    }         

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if ((requestCode == IDACHAPROD))
                setProduto((Produto)data.getSerializableExtra(G.EXTRASELECAO));           
        }
    }

    @Override
    protected void salvar() {
        if (consistir())
            super.salvar();
    }
    
    private boolean consistir(){
        if (produto.getId() == 0){
            G.msgInformacao(this, G.getString(this, R.string.consisteorcamentoitem));
            etProduto.requestFocus();
            return false;
        }
                
        return true;
    }    
    
}
