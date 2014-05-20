package com.forcadevenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.forcadevenda.bd.TbClientes;
import com.forcadevenda.classes.Cliente;
import com.forcadevenda.classes.Regiao;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GApi;

public class ClienteForm extends GFormActivity<Cliente,TbClientes> {
    
    private EditText etNome;
    private EditText etFantasia;
    private EditText etTelefone;
    private RadioButton rbPessoaFisica;
    private RadioButton rbPessoaJuridica;
    private EditText etDocumento1;
    private EditText etDocumento2;
    private EditText etCep;
    private EditText etEndereco;
    private EditText etNum;
    private EditText etBairro;
    private EditText etComplEnd;
    private EditText etCidade;
    private Spinner spUf;
    private EditText etRegiao;
    private EditText etEmail;    
    private EditText etResponsavel;
    private EditText etObs;    
    
    private TextView tvDocumento1;
    private TextView tvDocumento2;
    
    private ImageButton btProcRegiao;    
    
    private Regiao regiao;
    
    private final int IDACHAREGIAO = 1;
    
    private final int IDLIGAR = 2;
    private final int IDEMAIL = 3;
    private final int IDSMS = 4;
    private final int IDMAPA = 5;
    
    public static void call(Activity act, int idChamada, Long id){
        try{    
            Intent i = new Intent(act, ClienteForm.class);            
            
            if (id != null)
                i.putExtra(G.EXTRAID, id);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.erroformexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        regiao = new Regiao("");
        
        super.onCreate(icicle, R.layout.clienteform);        
    }
    
    @Override
    public void inicializar(){
        tabela = new TbClientes(this);
        
        tvDocumento1 = (TextView) findViewById(R.id.tvDocumento1);            
        tvDocumento2 = (TextView) findViewById(R.id.tvDocumento2);            
        
        etNome = (EditText) findViewById(R.id.etNome);            
        etFantasia = (EditText) findViewById(R.id.etFantasia);
        etTelefone = (EditText) findViewById(R.id.etTelefone);
        rbPessoaFisica = (RadioButton) findViewById(R.id.rbPessoaFisica);
        rbPessoaJuridica = (RadioButton) findViewById(R.id.rbPessoaJuridica);
        etDocumento1 = (EditText) findViewById(R.id.etDocumento1);
        etDocumento2 = (EditText) findViewById(R.id.etDocumento2);
        etCep = (EditText) findViewById(R.id.etCep);
        etEndereco = (EditText) findViewById(R.id.etEndereco);
        etNum = (EditText) findViewById(R.id.etNum);
        etBairro = (EditText) findViewById(R.id.etBairro);
        etComplEnd = (EditText) findViewById(R.id.etComplEnd);
        etCidade = (EditText) findViewById(R.id.etCidade);
        spUf = (Spinner)findViewById(R.id.spUf);
        etRegiao = (EditText) findViewById(R.id.etRegiao);        
        etEmail = (EditText) findViewById(R.id.etEmail);    
        etResponsavel = (EditText) findViewById(R.id.etResponsavel);
        etObs = (EditText) findViewById(R.id.etObs);        
        
        etNome.requestFocus();        
        
        btProcRegiao = (ImageButton) findViewById(R.id.btProcRegiao);     
        
        btProcRegiao.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Regioes.call(ClienteForm.this, IDACHAREGIAO);
            }
        });
             
        OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (rbPessoaFisica.isChecked()){
                    tvDocumento1.setText(G.getString(ClienteForm.this, R.string.labelcpf));
                    tvDocumento2.setText(G.getString(ClienteForm.this, R.string.labelrg));                    
                } else {
                    tvDocumento1.setText(G.getString(ClienteForm.this, R.string.labelcnpj));
                    tvDocumento2.setText(G.getString(ClienteForm.this, R.string.labelie));    
                }
            }
        };
        
        rbPessoaFisica.setOnCheckedChangeListener(onCheckedChangeListener);
        rbPessoaJuridica.setOnCheckedChangeListener(onCheckedChangeListener);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Cliente.getEstados());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);            
       
        spUf.setAdapter(adapter);
        
        super.inicializar();
    }
    
    @Override
    protected void recuperar() {
        cadastro = (Cliente)tabela.get(id);            
    }    
    
    @Override
    protected void criar() {
        cadastro = new Cliente("");
    }
    
    @Override
    protected void popularTela(){        
        etNome.setText(cadastro.getNome());
        etFantasia.setText(cadastro.getFantasia());
        etTelefone.setText(cadastro.getTelefone());
        etDocumento1.setText(cadastro.getDocumento1());
        etDocumento2.setText(cadastro.getDocumento2());
        etCep.setText(cadastro.getCep());
        etEndereco.setText(cadastro.getEndereco());
        etNum.setText(cadastro.getNum());
        etBairro.setText(cadastro.getBairro());
        etComplEnd.setText(cadastro.getComplEnd());
        etCidade.setText(cadastro.getCidade());
        etRegiao.setText(cadastro.getRegiao().getNome());
        etEmail.setText(cadastro.getEmail());    
        etResponsavel.setText(cadastro.getResponsavel());
        etObs.setText(cadastro.getObs());        
        
        rbPessoaFisica.setChecked(cadastro.getPessoa().equals(Cliente.PESSOAFISICA));
        rbPessoaJuridica.setChecked(cadastro.getPessoa().equals(Cliente.PESSOAJURIDICA));        
        
        spUf.setSelection(Cliente.getEstados().indexOf(cadastro.getUf()));
        
        setRegiao(cadastro.getRegiao());                 
    }
    
    @Override
    protected void popularObjeto(){
        cadastro.setNome(etNome.getText().toString());        
        cadastro.setFantasia(etFantasia.getText().toString());
        cadastro.setTelefone(etTelefone.getText().toString());        
        cadastro.setDocumento1(etDocumento1.getText().toString());
        cadastro.setDocumento2(etDocumento2.getText().toString());
        cadastro.setCep(etCep.getText().toString());
        cadastro.setEndereco(etEndereco.getText().toString());
        cadastro.setNum(etNum.getText().toString());
        cadastro.setBairro(etBairro.getText().toString());
        cadastro.setComplEnd(etComplEnd.getText().toString());
        cadastro.setCidade(etCidade.getText().toString());
        cadastro.setEmail(etEmail.getText().toString());    
        cadastro.setResponsavel(etResponsavel.getText().toString());
        cadastro.setObs(etObs.getText().toString());                
        
        if (rbPessoaFisica.isChecked())
            cadastro.setPessoa(Cliente.PESSOAFISICA);
        else
            cadastro.setPessoa(Cliente.PESSOAJURIDICA);
        
        cadastro.setUf((String)spUf.getSelectedItem());
        
        cadastro.setRegiao(regiao);
    }
    
    private void setRegiao(Regiao regiao) {
        if (regiao == null)
            regiao = new Regiao("");
        
        this.regiao = regiao;
        etRegiao.setText(regiao.getNome());
    }    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if ((requestCode == IDACHAREGIAO))
                setRegiao((Regiao)data.getSerializableExtra(G.EXTRASELECAO));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        SubMenu smOutros = menu.addSubMenu(R.string.menuoutros).setIcon(R.drawable.outros);
        
        smOutros.add(0, IDLIGAR, 0, R.string.menuligar);
        smOutros.add(0, IDSMS, 0, R.string.menuenviarsms);
        smOutros.add(0, IDEMAIL, 0, R.string.menuenviaremail);
        smOutros.add(0, IDMAPA, 0, R.string.menumapa);
        
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);
        
        switch (item.getItemId()) {
            case IDLIGAR: GApi.ligar(this, cadastro.getTelefone()); break;
            case IDEMAIL: GApi.enviarEmail(this, new String[]{cadastro.getEmail()}); break;            
            case IDSMS: GApi.enviarSMS(this, new String[]{cadastro.getTelefone()}); break;            
            case IDMAPA: {
                popularObjeto();
                GApi.mapa(this, cadastro.getEnderecoCompleto(), cadastro.getCep());
            } break;            
        }        
        
        return true;
    }
    
}
