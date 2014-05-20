package com.forcadevenda.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import com.forcadevenda.bd.TbGrupos;
import com.forcadevenda.classes.Grupo;
import com.forcadevenda.classes.Produto;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GSQL;
import android.content.DialogInterface;
import com.forcadevenda.uteis.GApi;

public class ProdutoFiltro extends GFiltroActivity {
    
    private EditText etDescricao;      
    private EditText etCodigo;      
    private EditText etGrupo;  
    
    private ImageButton btProcGrupo;    
    
    private Grupo grupo;
    private TbGrupos tbGrupos;
    
    private final int IDACHAGRUPO = 1;
    
    public static void call(Activity act, int idChamada, ContentValues filtro){
        try{    
            Intent i = new Intent(act, ProdutoFiltro.class);            
            
            i.putExtra(G.EXTRACTVFILTRO, filtro);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.errofiltroexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        tbGrupos = new TbGrupos(this);
        grupo = new Grupo("");
        
        super.onCreate(icicle, R.layout.produtofiltro);
    }
    
    @Override
    protected void inicializar(){
        GApi.setBtFala(this, (ImageButton) findViewById(R.id.btFala));        
        GApi.setBtCodBarras(this, (ImageButton) findViewById(R.id.btCodBarras));
        
        etDescricao = (EditText) findViewById(R.id.etDescricao);        
        etCodigo = (EditText) findViewById(R.id.etCodigo);        
        etGrupo = (EditText) findViewById(R.id.etGrupo);     
        
        btProcGrupo = (ImageButton) findViewById(R.id.btProcGrupo);     
        
        btProcGrupo.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Grupos.call(ProdutoFiltro.this, IDACHAGRUPO);
            }
        });
        
        etDescricao.requestFocus();
        
        super.inicializar();            
    }

    private void setGrupo(long id){
        setGrupo(tbGrupos.get(id));
    }
    
    private void setGrupo(Grupo grupo) {
        if (grupo == null)
            grupo = new Grupo("");
        
        this.grupo = grupo;
        etGrupo.setText(grupo.getDescricao());
    }
    
    @Override
    protected void popularTela(){
        etDescricao.setText(filtro.getAsString(Produto.colDescricao));
        etCodigo.setText(filtro.getAsString(Produto.colCodigoImport));
        long idgrupo = 0;
        try{
            idgrupo = filtro.getAsLong(Produto.colGrupo);
        } catch (Exception e){
            idgrupo = 0;
        }
        setGrupo(idgrupo);                     
    }
    
    @Override
    protected void popularObjeto(){
        filtro.put(Produto.colDescricao, etDescricao.getText().toString());
        filtro.put(Produto.colCodigoImport, etCodigo.getText().toString());
        filtro.put(Produto.colGrupo, grupo.getId());
    }
    
    @Override
    protected void limpar() {
        etDescricao.setText("");
        etCodigo.setText("");
        setGrupo(new Grupo(""));
	filtrar();
    }

    @Override
    public String toString() {
        String f = "";
        f = GSQL.filtroAnd(f, GSQL.filtroLike(Produto.colDescricao, filtro.getAsString(Produto.colDescricao)));
        
        f = GSQL.filtroAnd(f, GSQL.filtroOr(GSQL.filtro(Produto.colCodigoImport, "=", filtro.getAsString(Produto.colCodigoImport)), 
                                          GSQL.filtro(Produto.colCodBarras, "=", filtro.getAsString(Produto.colCodigoImport))));
        
        if (grupo.getId() != 0)
            f = GSQL.filtroAnd(f, GSQL.filtro(Produto.colGrupo, " = ", filtro.getAsLong(Produto.colGrupo)));        
        
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if ((requestCode == IDACHAGRUPO)){
                setGrupo((Grupo)data.getSerializableExtra(G.EXTRASELECAO));
                openOptionsMenu();
            }
            
            if (requestCode == G.IDGETCODBARRAS) {
                etCodigo.setText(GApi.getResultCodBarras(data));
                openOptionsMenu();
            }
            
            if (requestCode == G.IDGETVOZ){
                final String[] itens = GApi.getResultFala(data);
                G.msgLista(this, itens, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            etDescricao.setText(itens[which]);
                            openOptionsMenu();
                        }
                    });                
            }
        }
    }
    
}
