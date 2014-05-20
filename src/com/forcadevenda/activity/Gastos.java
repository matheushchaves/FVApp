package com.forcadevenda.activity;

import com.forcadevenda.uteis.G;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.forcadevenda.bd.*;
import com.forcadevenda.classes.*;

public class Gastos extends GListActivity<Gasto> {
    
    TextView tvValorTotal;
    
    public static void call(Activity act){
        try{
            act.startActivity(new Intent(act, Gastos.class));
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.errolistaexibir), e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle icicle) {        
        super.onCreate(icicle, R.layout.gastos);                        
    }
    
    @Override
    protected void inicializar(){
        tabela = new TbGastos(this);  
        setFiltro("");
        setOrdem(Gasto.colData + G.ORDERDESC);
        
        tvValorTotal = (TextView) findViewById(R.id.tvValorTotal);
        
        super.inicializar();
    }

    @Override
    protected void popularTela() {
        super.popularTela();
        tvValorTotal.setText(G.getString(this, R.string.moeda) + G.formatDouble((new TbGastos(this)).totalizar(getFiltro())));
    }
    
    @Override
    public void longItemClick(int pos, View v){        
        editar();
    }          
    
    @Override
    protected void incluir() {
        GastoForm.call(this, G.IDINCLUIR, null);
    }    
    
    @Override
    protected void editar() {
        if (!isSelected()){
            G.alertar(this, getString(R.string.nadaselecionado));
            return;
        }        

	GastoForm.call(this, G.IDEDITAR, lista.get(getPosicao()).getId());
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

    }

    @Override
    protected void filtrar() {
        GastoFiltro.call(this, G.IDFILTRAR, getCtvFiltro());
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
        miFiltrar.setVisible(true);
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
        return new GastosAdapter(this, lista);
    }
    
}
