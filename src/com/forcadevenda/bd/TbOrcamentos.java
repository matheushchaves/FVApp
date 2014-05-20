package com.forcadevenda.bd;

import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import com.forcadevenda.classes.*;
import java.util.ArrayList;
import java.util.List;
import com.forcadevenda.uteis.G;

public class TbOrcamentos extends GTabela<Orcamento>{
    
    private TbClientes tbClientes;
    private TbCondPagtos tbCondPagtos;
    private TbOrcamentosItens tbOrcamentosItens;
    
    public TbOrcamentos(Context ctx) {
        super("ORCAMENTOS", Orcamento.colunas, ctx);
        tbClientes = new TbClientes(ctx);
        tbCondPagtos = new TbCondPagtos(ctx);
        tbOrcamentosItens = new TbOrcamentosItens(ctx);
    }
   
    @Override    
    public List<Orcamento> listar(String[] colunas, String filtro, String ordem){
        Cursor cursor = selecionar(Orcamento.colunas, filtro, ordem);
        List<Orcamento> retorno = new ArrayList<Orcamento>();
                
        if (cursor.getCount() == 0)
            return retorno;
        
        if (cursor.moveToFirst()) {
            do {
		Orcamento orcamento = new Orcamento();

                orcamento.setId(cursor.getLong(cursor.getColumnIndex(Orcamento.colId)));                
                orcamento.setCliente(tbClientes.get(cursor.getLong(cursor.getColumnIndex(Orcamento.colCliente))));
                orcamento.setCondPagto(tbCondPagtos.get(cursor.getLong(cursor.getColumnIndex(Orcamento.colCondPagto))));
                orcamento.setData(G.MASCDATAHORADB, cursor.getString(cursor.getColumnIndex(Orcamento.colData)));
                orcamento.setObs(cursor.getString(cursor.getColumnIndex(Orcamento.colObs)));
                orcamento.setStatus(cursor.getInt(cursor.getColumnIndex(Orcamento.colStatus)));
                orcamento.setValorTotal(cursor.getDouble(cursor.getColumnIndex(Orcamento.colValorTotal)));
                
                orcamento.setItens(tbOrcamentosItens.listarByOrcamento(orcamento.getId()));
                
                retorno.add(orcamento);
            } while (cursor.moveToNext());
        }        
        
        return retorno;
    }
    
    public Double totalizar(String filtro){
        Cursor cursor = query(G.strAdd("SELECT SUM("+ Orcamento.colValorTotal +") FROM ORCAMENTOS", filtro, " WHERE "));
        
        if (cursor.getCount() == 0)
             return 0.0;
        
        if(cursor.moveToFirst())
            return cursor.getDouble(0);
        else
            return 0.0;
    }    
 
    @Override
    public ContentValues getContentValues(Orcamento orcamento) {
        ContentValues valores = new ContentValues();
        
        valores.put(Orcamento.colCliente, orcamento.getCliente().getId());	
        valores.put(Orcamento.colCondPagto, orcamento.getCondPagto().getId());
        valores.put(Orcamento.colData, orcamento.getData(G.MASCDATAHORADB));        
        valores.put(Orcamento.colObs, orcamento.getObs());
        valores.put(Orcamento.colStatus, orcamento.getStatus());
        valores.put(Orcamento.colValorTotal, orcamento.getValorTotal());
        
        return valores;
    }

}
