package com.forcadevenda.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.List;
import com.forcadevenda.classes.CondPagto;
import java.util.ArrayList;

public class TbCondPagtos extends GTabela<CondPagto>{
    
    public TbCondPagtos(Context ctx) {
        super("CONDPAGTOS", CondPagto.colunas, ctx);
    }    
    
    @Override
    public List<CondPagto> listar(String[] colunas, String filtro, String ordem) {
        Cursor cursor = selecionar(colunas, filtro, ordem);
        List<CondPagto> retorno = new ArrayList<CondPagto>();
                
        if (cursor.getCount() == 0)
            return retorno;
        
        if (cursor.moveToFirst()) {
            do {
		CondPagto condpagto = new CondPagto("");

                condpagto.setId(cursor.getLong(cursor.getColumnIndex(CondPagto.colId)));
                condpagto.setCodigoImport(cursor.getString(cursor.getColumnIndex(CondPagto.colCodigoImport)));                
                condpagto.setDescricao(cursor.getString(cursor.getColumnIndex(CondPagto.colDescricao)));                
                condpagto.setParcelas(cursor.getInt(cursor.getColumnIndex(CondPagto.colParcelas)));                
                condpagto.setPorcDesc(cursor.getDouble(cursor.getColumnIndex(CondPagto.colPorcDesc)));                
                
                retorno.add(condpagto);
            } while (cursor.moveToNext());
        }        
        
        return retorno;
    }    

    @Override
    public ContentValues getContentValues(CondPagto objeto) {
        ContentValues valores = new ContentValues();
        
        valores.put(CondPagto.colDescricao, objeto.getDescricao());	
        valores.put(CondPagto.colCodigoImport, objeto.getCodigoImport());	
        valores.put(CondPagto.colParcelas, objeto.getParcelas());	
        valores.put(CondPagto.colPorcDesc, objeto.getPorcDesc());	        
        
        return valores;
    }
    
}
