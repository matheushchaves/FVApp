package com.forcadevenda.activity;

import com.forcadevenda.uteis.G;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import com.forcadevenda.bd.*;
import com.forcadevenda.classes.*;
import com.forcadevenda.uteis.GApi;
import java.util.ArrayList;
import java.util.List;

public class Clientes extends GListActivity<Cliente> {
    
    private final int IDEMAIL = 1;
    private final int IDSMS = 2;    
    
    public static void call(Activity act, int idChamada, boolean isSelecao){
        try{
            Intent i = new Intent(act, Clientes.class);            
            
            i.putExtra(G.EXTRASELECAO, isSelecao);                
            
            act.startActivityForResult(i, idChamada);
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.errolistaexibir), e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle icicle) {        
        super.onCreate(icicle, R.layout.lista);                        
    }
    
    @Override
    protected void inicializar(){
        tabela = new TbClientes(this);  
        setFiltro("");
        setOrdem(Cliente.colNome);
        
        super.inicializar();
    }  
    
    @Override
    public void longItemClick(int pos, View v){        
        if (isSelecao)
            selecionar();
        else
            editar();
    }          
    
    @Override
    protected void incluir() {
        ClienteForm.call(this, G.IDINCLUIR, null);
    }    
    
    @Override
    protected void editar() {
        if (!isSelected()){
            G.alertar(this, getString(R.string.nadaselecionado));
            return;
        }        

	ClienteForm.call(this, G.IDEDITAR, lista.get(getPosicao()).getId());        
    }

    @Override
    protected void deletar() {
        if (!isSelected()){
            G.alertar(this, getString(R.string.nadaselecionado));
            return;
        }        
        
        try{
            G.pergunta(this, getString(R.string.perguntadeletar), 
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {                                            
                                    // Ação ---
                                    if (tabela.deletar(lista.get(getPosicao()).getId()))
                                        popularTela();                                                        
                                }
                            }, 
                            G.listenerdimiss);  
        }catch(Exception e){
            G.msgErro(this, G.getString(this, R.string.errolistaexcluir), e.getMessage());
        }
    }

    @Override
    protected void ver() {
        
    }       
    
    @Override
    protected void selecionar() {
        if (!isSelected()){
            G.alertar(this, getString(R.string.nadaselecionado));
            return;
        }             
        
        try{        
            setResult(RESULT_OK, new Intent().putExtra(G.EXTRASELECAO, lista.get(getPosicao())));	                    
            finish();                    
        }catch(Exception e){
            G.msgErro(this, G.getString(this, R.string.errolistaselecionar), e.getMessage());
        }
    }

    @Override
    protected void filtrar() {
        ClienteFiltro.call(this, G.IDFILTRAR, getCtvFiltro());
    }    
    
    @Override
    protected void classificar() {
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);        
        miIncluir.setVisible(true);
        miEditar.setVisible(true);
        miDeletar.setVisible(true);        
        miSelecionar.setVisible(isSelecao);        
        miFiltrar.setVisible(true);
        
        SubMenu smOutros = menu.addSubMenu(R.string.menuoutros).setIcon(R.drawable.outros);
        
        smOutros.add(0, IDSMS, 0, R.string.menuenviarsms);
        smOutros.add(0, IDEMAIL, 0, R.string.menuenviaremail);
        
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);
        
        switch (item.getItemId()) {
            case IDEMAIL: GApi.enviarEmail(this, getEmails()); break;            
            case IDSMS: GApi.enviarSMS(this, getTelefones()); break;            
        }        
        
        return true;
    }     
    
    @Override
    protected void onActivityResult(int idChamada, int retorno, Intent it) {
        super.onActivityResult(idChamada, retorno, it);

       if (retorno == RESULT_OK) {
            if ((idChamada == G.IDEDITAR)||(idChamada == G.IDINCLUIR)){
                setFiltro("");
                popularTela();        
            }
            
            if (idChamada == G.IDFILTRAR){                
                setCtvFiltro((ContentValues)it.getParcelableExtra(G.EXTRACTVFILTRO));
                String f = it.getStringExtra(G.EXTRASTRFILTRO);
                
                if (f != null){
                    setFiltro(f);
                    popularTela();
                }
            }
            
            G.alertar(this, getString(R.string.sucesso));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    protected GAdapter adapter() {
        return new ClientesAdapter(this, lista);
    }

    private String[] getEmails() {
        List<String> listaStr = new ArrayList<String>();
        
        for (Cliente cliente : lista)
            listaStr.add(cliente.getEmail());  
        
        return listaStr.toArray(new String[listaStr.size()]);
    }

    private String[] getTelefones() {
        List<String> listaStr = new ArrayList<String>();
        
        for (Cliente cliente : lista)
            listaStr.add(cliente.getTelefone());  
        
        return listaStr.toArray(new String[listaStr.size()]);
    }    
   
}
