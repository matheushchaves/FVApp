package com.forcadevenda.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.forcadevenda.bd.GConexao;

public class Main extends Activity
{
    
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GConexao.verificarBd(this);
        
        inicializar();
    }
    
    private void inicializar(){
        
        Button btClientes = (Button) findViewById(R.id.btClientes);        
        Button btProdutos = (Button) findViewById(R.id.btProdutos);        
        Button btOrcamentos = (Button) findViewById(R.id.btOrcamentos);        
        Button btGastos = (Button) findViewById(R.id.btGastos);         
        Button btSync = (Button) findViewById(R.id.btSync);        
        Button btConfiguracoes = (Button) findViewById(R.id.btConfiguracoes); 
        //Button btUteis = (Button) findViewById(R.id.btUteis); 
        
        btClientes.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {                                            
                                            Clientes.call(Main.this, 0, false);
                                        }
                                     });        
        
        btProdutos.setOnClickListener(new View.OnClickListener() {            
                                        public void onClick(View v) {   
                                            Produtos.call(Main.this, 0, false);
                                        }
                                     });             
        
        btOrcamentos.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {                                                                                                                                    
                                            Orcamentos.call(Main.this);
                                        }
                                     });                
        
        btGastos.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {                                                                                                                                    
                                            Gastos.call(Main.this);
                                        }
                                     });         
        
        btSync.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {                                                                                                                                    
                                        	Sincronizar.call(Main.this);
                                        }
                                     });                
        
        btConfiguracoes.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {                                                                                                                                    
                                        	   Uteis.call(Main.this);
                                        }
                                     }); 
        
//        btUteis.setOnClickListener(new View.OnClickListener() {
//                                        public void onClick(View v) {                                                                                                                                    
//                                            Uteis.call(Main.this);
//                                        }
//                                     }); 
        
    }

}
