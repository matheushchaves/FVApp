package com.forcadevenda.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.List;
import com.forcadevenda.classes.*;
import com.forcadevenda.uteis.G;
import java.util.ArrayList;

public class TbGastos extends GTabela<Gasto>{
    
    public TbGastos(Context ctx) {
        super("GASTOS", Gasto.colunas, ctx);
    }    

    @Override
    public List<Gasto> listar(String[] colunas, String filtro, String ordem) {
        Cursor cursor = selecionar(colunas, filtro, ordem);
        List<Gasto> retorno = new ArrayList<Gasto>();
                
        if (cursor.getCount() == 0)
            return retorno;
        
        if (cursor.moveToFirst()) {
            do {
		Gasto gasto = new Gasto("");

                gasto.setId(cursor.getLong(cursor.getColumnIndex(Gasto.colId)));
                gasto.setData(G.MASCDATAHORADB, cursor.getString(cursor.getColumnIndex(Gasto.colData)));                
                gasto.setDescricao(cursor.getString(cursor.getColumnIndex(Gasto.colDescricao)));                
                gasto.setQtde(cursor.getDouble(cursor.getColumnIndex(Gasto.colQtde)));                
                gasto.setValor(cursor.getDouble(cursor.getColumnIndex(Gasto.colValor)));                

                retorno.add(gasto);
            } while (cursor.moveToNext());
        }        
        
        return retorno;
    } 
    
    public Double totalizar(String filtro){
        Cursor cursor = query(G.strAdd("SELECT SUM("+ Gasto.colQtde +" * "+ Gasto.colValor +") FROM GASTOS", filtro, " WHERE "));
        
        if (cursor.getCount() == 0)
             return 0.0;
        
        if(cursor.moveToFirst())
            return cursor.getDouble(0);
        else
            return 0.0;
    }
    
    public List<String> listarDescricoes(){
        List<Gasto> lista = listar("", Gasto.colDescricao);        
        List<String> listaStr = new ArrayList<String>();
        
        for (Gasto gasto : lista)
            if (!listaStr.contains(gasto.getDescricao()))
                listaStr.add(gasto.getDescricao());  
        
        return listaStr;
    }

    @Override
    public ContentValues getContentValues(Gasto objeto) {
        ContentValues valores = new ContentValues();
        
        valores.put(Gasto.colDescricao, objeto.getDescricao());	
        valores.put(Gasto.colData, objeto.getData(G.MASCDATAHORADB));	
        valores.put(Gasto.colQtde, objeto.getQtde());	
        valores.put(Gasto.colValor, objeto.getValor());	        
        
        return valores;
    }
    
}
