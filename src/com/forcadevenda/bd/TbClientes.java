 package com.forcadevenda.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.forcadevenda.classes.Cliente;
import com.forcadevenda.uteis.G;
import java.util.ArrayList;
import java.util.List;

public class TbClientes extends GTabela<Cliente>{
    
    private TbRegioes tbRegioes;

    public TbClientes(Context ctx) {
        super("CLIENTES", Cliente.colunas, ctx);
        tbRegioes = new TbRegioes(ctx);
    }    
   
    @Override
    public List<Cliente> listar(String[] colunas, String filtro, String ordem) {
        Cursor cursor = selecionar(colunas, filtro, ordem);
        List<Cliente> retorno = new ArrayList<Cliente>();
                
        if (cursor.getCount() == 0)
            return retorno;
        
        if (cursor.moveToFirst()) {
            do {
		Cliente cliente = new Cliente("");

                cliente.setId(cursor.getLong(cursor.getColumnIndex(Cliente.colId))); 
                cliente.setAtivo(G.intToBool((cursor.getInt(cursor.getColumnIndex(Cliente.colAtivo)))));
                cliente.setNome(cursor.getString(cursor.getColumnIndex(Cliente.colNome)));
                cliente.setFantasia(cursor.getString(cursor.getColumnIndex(Cliente.colFantasia)));
                cliente.setTelefone(cursor.getString(cursor.getColumnIndex(Cliente.colTelefone)));                
                cliente.setPessoa(cursor.getString(cursor.getColumnIndex(Cliente.colPessoa)));
                cliente.setDocumento1(cursor.getString(cursor.getColumnIndex(Cliente.colDocumento1)));
                cliente.setDocumento2(cursor.getString(cursor.getColumnIndex(Cliente.colDocumento2)));
                cliente.setCep(cursor.getString(cursor.getColumnIndex(Cliente.colCep)));
                cliente.setEndereco(cursor.getString(cursor.getColumnIndex(Cliente.colEndereco)));
                cliente.setNum(cursor.getString(cursor.getColumnIndex(Cliente.colNum)));
                cliente.setBairro(cursor.getString(cursor.getColumnIndex(Cliente.colBairro)));
                cliente.setComplEnd(cursor.getString(cursor.getColumnIndex(Cliente.colComplEnd)));
                cliente.setCidade(cursor.getString(cursor.getColumnIndex(Cliente.colCidade)));
                cliente.setUf(cursor.getString(cursor.getColumnIndex(Cliente.colUf)));
                cliente.setRegiao(tbRegioes.get(cursor.getLong(cursor.getColumnIndex(Cliente.colRegiao))));
                cliente.setEmail(cursor.getString(cursor.getColumnIndex(Cliente.colEmail)));    
                cliente.setResponsavel(cursor.getString(cursor.getColumnIndex(Cliente.colResponsavel)));
                cliente.setObs(cursor.getString(cursor.getColumnIndex(Cliente.colObs)));
                cliente.setCodigoImport(cursor.getString(cursor.getColumnIndex(Cliente.colCodigoImport)));                

                retorno.add(cliente);
            } while (cursor.moveToNext());
        }        
        
        return retorno;
    }
    
    public String[] listarEmails(){
        List<Cliente> lista = listar();        
        List<String> listaStr = new ArrayList<String>();
        
        for (Cliente cliente : lista)
            listaStr.add(cliente.getEmail());  
        
        return listaStr.toArray(new String[listaStr.size()]);
    }    
    
    public String[] listarTelefones(){
        List<Cliente> lista = listar();        
        List<String> listaStr = new ArrayList<String>();
        
        for (Cliente cliente : lista)
            listaStr.add(cliente.getTelefone());  
        
        return listaStr.toArray(new String[listaStr.size()]);
    }        

    @Override
    public ContentValues getContentValues(Cliente cliente) {
        ContentValues valores = new ContentValues();
        
        long regiao = tbRegioes.inserirSeNaoExistir(cliente.getRegiao());
        
        valores.put(Cliente.colNome, cliente.getNome());	                
        valores.put(Cliente.colAtivo, G.boolToInt(cliente.isAtivo()));	                
        valores.put(Cliente.colFantasia, cliente.getFantasia());
        valores.put(Cliente.colTelefone, cliente.getTelefone());	                        
        valores.put(Cliente.colPessoa, cliente.getPessoa());
        valores.put(Cliente.colDocumento1, cliente.getDocumento1());
        valores.put(Cliente.colDocumento2, cliente.getDocumento2());
        valores.put(Cliente.colCep, cliente.getCep());
        valores.put(Cliente.colEndereco, cliente.getEndereco());
        valores.put(Cliente.colNum, cliente.getNum());
        valores.put(Cliente.colBairro, cliente.getBairro());
        valores.put(Cliente.colComplEnd, cliente.getComplEnd());
        valores.put(Cliente.colCidade, cliente.getCidade());
        valores.put(Cliente.colUf, cliente.getUf());
        valores.put(Cliente.colRegiao, regiao);
        valores.put(Cliente.colEmail, cliente.getEmail());    
        valores.put(Cliente.colResponsavel, cliente.getResponsavel());
        valores.put(Cliente.colObs, cliente.getObs());
        valores.put(Cliente.colCodigoImport, cliente.getCodigoImport());
        
        return valores;
    }     

}
