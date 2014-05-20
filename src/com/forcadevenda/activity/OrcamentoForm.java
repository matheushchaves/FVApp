package com.forcadevenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.forcadevenda.bd.TbOrcamentos;
import com.forcadevenda.bd.TbOrcamentosItens;
import com.forcadevenda.classes.Orcamento;
import com.forcadevenda.uteis.G;
import com.forcadevenda.classes.Cliente;
import com.forcadevenda.classes.CondPagto;

public class OrcamentoForm extends GFormActivity<Orcamento,TbOrcamentos> {
    
    private TextView tvNumero;
    private EditText etCliente;
    private EditText etCondPagto;
    private EditText etValorTotal;
    private EditText etObs;
    
    private ImageButton btProcCliente;    
    private ImageButton btProcCondPagto;    
    
    private Button btItens;
    private Button btConcluir;
    
    private Cliente cliente;
    private CondPagto condPagto;
    
    private final int IDACHACLIENTE = 1;    
    private final int IDACHACONDPAGTO = 2;    
    
    public static void call(Activity act, int idChamada, Long id){
        try{    
            Intent i = new Intent(act, OrcamentoForm.class);            
            
            if (id != null)
                i.putExtra(G.EXTRAID, id);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.erroformexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        cliente = new Cliente("");
        condPagto = new CondPagto("");
        
        super.onCreate(icicle, R.layout.orcamentoform);        
    }
    
    @Override
    public void inicializar(){
        tabela = new TbOrcamentos(this);
        
        tvNumero = (TextView) findViewById(R.id.tvNumero);        
        etCliente = (EditText) findViewById(R.id.etCliente);        
        etCondPagto = (EditText) findViewById(R.id.etCondPagto);        
        etValorTotal = (EditText) findViewById(R.id.etValorTotal); 
        etObs = (EditText) findViewById(R.id.etObs);        
        
        btProcCliente = (ImageButton) findViewById(R.id.btProcCliente);     
        
        btProcCliente.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Clientes.call(OrcamentoForm.this, IDACHACLIENTE, true);
            }
        });        
        
        btProcCondPagto = (ImageButton) findViewById(R.id.btProcCondPagto);     
        
        btProcCondPagto.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                CondPagtos.call(OrcamentoForm.this, IDACHACONDPAGTO);
            }
        });                
        
        btItens = (Button) findViewById(R.id.btItens);     
        
        btItens.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
			public void onClick(View arg0) {
                popularObjeto();
                
                if (isIncluindo()){
                    if (consistir(false)){
                        try{    
                            id = tabela.incluir(cadastro);    
                            
                            cadastro.setId(id);
                            operacao = G.IDEDITAR;                            
                        } catch (Exception e){
                            G.msgErro(OrcamentoForm.this, G.getString(OrcamentoForm.this, R.string.erroformsalvar), e.getMessage());
                        }                            
                    }
                    else
                        return;
                }
        
                OrcamentosItens.call(OrcamentoForm.this, G.IDEDITAR, id, cadastro.isAberto());                
            }
        });             
        
        btConcluir = (Button) findViewById(R.id.btConcluir);     
        
        btConcluir.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (!consistir(true))
                    return;
                
                G.pergunta(OrcamentoForm.this, G.getString(OrcamentoForm.this, R.string.perguntaconcluir), 
                            new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {                                            
                                    // Ação ---
                                    popularObjeto();                
                                    cadastro.setStatus(Orcamento.STCONCLUIDO);

                                    salvar();                                                
                                }
                            }, 
                            G.listenerdimiss);                
            }
        });         
        
        super.inicializar();
    }
    
    @Override
    protected void recuperar() {
        cadastro = (Orcamento)tabela.get(id);            
    }    
    
    @Override
    protected void criar() {
        cadastro = new Orcamento();        
    }

    @Override
    protected void salvar() {
        if (consistir(false))
            super.salvar();
    }
    
    @Override
    protected void popularTela(){ 
        if (!isIncluindo())
            tvNumero.setText(cadastro.getNumero() + " " + G.strEntreParenteses(cadastro.getStrStatus(this)));
        else
            tvNumero.setText(G.getString(this, R.string.novo) + " " + G.strEntreParenteses(cadastro.getStrStatus(this)));
        
        etCliente.setText(cadastro.getCliente().getNome());    
        etCondPagto.setText(cadastro.getCondPagto().getDescricao());
        etValorTotal.setText(cadastro.getStrValorTotal().replace(",", "."));
        etObs.setText(cadastro.getObs());
        
        setCliente(cadastro.getCliente());
        setCondPagto(cadastro.getCondPagto());
        
        etObs.setEnabled(cadastro.isAberto());
        btProcCliente.setEnabled(cadastro.isAberto());
        btProcCondPagto.setEnabled(cadastro.isAberto());
        btConcluir.setEnabled(cadastro.isAberto());
    }
    
    @Override
    protected void popularObjeto(){
        cadastro.setObs(etObs.getText().toString());
        
        cadastro.setCliente(cliente);
        cadastro.setCondPagto(condPagto);
    }
    
    private void setCliente(Cliente cliente) {
        if (cliente == null)
            cliente = new Cliente("");
        
        this.cliente = cliente;
        etCliente.setText(cliente.getNome());
    }        
    
    private void setCondPagto(CondPagto condPagto) {
        if (condPagto == null)
            condPagto = new CondPagto("");
        
        this.condPagto = condPagto;
        etCondPagto.setText(condPagto.getDescricao());
    }            

    @SuppressWarnings("unchecked")
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if ((requestCode == IDACHACLIENTE))
                setCliente((Cliente)data.getSerializableExtra(G.EXTRASELECAO));
            if ((requestCode == IDACHACONDPAGTO))
                setCondPagto((CondPagto)data.getSerializableExtra(G.EXTRASELECAO));            
        }
        
        if (requestCode == G.IDEDITAR){
            cadastro.setValorTotal(((new TbOrcamentosItens(this)).totalizar(id)));
            popularTela();
            
            tabela.editar(cadastro);
        }
    }     
    
    private boolean consistir(boolean consisteValor){
        if (cliente.getId() == 0){
            G.msgAlerta(this, G.getString(this, R.string.consistecliente));
            etCliente.requestFocus();
            return false;
        }

        if (condPagto.getId() == 0){
            G.msgAlerta(this, G.getString(this, R.string.consistecondpagto));
            etCondPagto.requestFocus();
            return false;
        }        
        
        if ((consisteValor) && (cadastro.getValorTotal() == 0)){
            G.msgAlerta(this, G.getString(this, R.string.consisteitens));
            etValorTotal.requestFocus();
            return false;
        }                
        
        return true;
    }
    
}
