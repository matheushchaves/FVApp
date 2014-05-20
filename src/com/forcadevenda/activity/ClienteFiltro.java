package com.forcadevenda.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import com.forcadevenda.bd.TbRegioes;
import com.forcadevenda.classes.Regiao;
import com.forcadevenda.classes.Cliente;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GSQL;
import android.content.DialogInterface;
import com.forcadevenda.uteis.GApi;

public class ClienteFiltro extends GFiltroActivity {
    
    private EditText etNome;      
    private EditText etRegiao;  
    
    private ImageButton btProcRegiao;    
    
    private Regiao regiao;
    private TbRegioes tbRegioes;
    
    private final int IDACHAREGIAO = 1;
    
    public static void call(Activity act, int idChamada, ContentValues filtro){
        try{    
            Intent i = new Intent(act, ClienteFiltro.class);            
            
            i.putExtra(G.EXTRACTVFILTRO, filtro);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.errofiltroexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        tbRegioes = new TbRegioes(this);
        regiao = new Regiao("");
        
        super.onCreate(icicle, R.layout.clientefiltro);
    }
    
    @Override
    protected void inicializar(){
        GApi.setBtFala(this, (ImageButton) findViewById(R.id.btFala));        
        
        etNome = (EditText) findViewById(R.id.etNome);        
        etRegiao = (EditText) findViewById(R.id.etRegiao);     
        
        btProcRegiao = (ImageButton) findViewById(R.id.btProcRegiao);     
        
        btProcRegiao.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Regioes.call(ClienteFiltro.this, IDACHAREGIAO);
            }
        });
        
        etNome.requestFocus();
        
        super.inicializar();            
    }

    private void setRegiao(long id){
        setRegiao(tbRegioes.get(id));
    }
    
    private void setRegiao(Regiao regiao) {
        if (regiao == null)
            regiao = new Regiao("");
        
        this.regiao = regiao;
        etRegiao.setText(regiao.getNome());
    }
    
    @Override
    protected void popularTela(){
        etNome.setText(filtro.getAsString(Cliente.colNome));
        
        long idregiao = 0;
        try{
            idregiao = filtro.getAsLong(Cliente.colRegiao);
        } catch (Exception e){
            idregiao = 0;
        }
        
        setRegiao(idregiao);                     
    }
    
    @Override
    protected void popularObjeto(){
        filtro.put(Cliente.colNome, etNome.getText().toString());
        filtro.put(Cliente.colRegiao, regiao.getId());
    }
    
    @Override
    protected void limpar() {
        etNome.setText("");
        setRegiao(new Regiao(""));
	filtrar();
    }

    @Override
    public String toString() {
        String f = "";
        
        f = GSQL.filtroAnd(f, GSQL.filtroOr(GSQL.filtroLike(Cliente.colNome, filtro.getAsString(Cliente.colNome)), 
                                          GSQL.filtroLike(Cliente.colFantasia, filtro.getAsString(Cliente.colFantasia))));
        
        if (regiao.getId() != 0)
            f = GSQL.filtroAnd(f, GSQL.filtro(Cliente.colRegiao, " = ", filtro.getAsLong(Cliente.colRegiao)));        
        
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if ((requestCode == IDACHAREGIAO)){
                setRegiao((Regiao)data.getSerializableExtra(G.EXTRASELECAO));
                openOptionsMenu();
            }
            
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
