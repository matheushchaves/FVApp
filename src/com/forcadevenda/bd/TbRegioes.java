package com.forcadevenda.bd;

import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import com.forcadevenda.classes.*;
import com.forcadevenda.uteis.G;
import java.util.ArrayList;
import java.util.List;

public class TbRegioes extends GTabela<Regiao>{
    
    public TbRegioes(Context ctx) {
        super("REGIOES", Regiao.colunas, ctx);
    }
   
    @Override    
    public List<Regiao> listar(String[] colunas, String filtro, String ordem){
        Cursor cursor = selecionar(colunas, filtro, ordem);
        List<Regiao> retorno = new ArrayList<Regiao>();
                
        if (cursor.getCount() == 0)
            return retorno;
        
        if (cursor.moveToFirst()) {
            do {
		Regiao regiao = new Regiao("");

                regiao.setId(cursor.getLong(cursor.getColumnIndex(Regiao.colId)));
                regiao.setNome(cursor.getString(cursor.getColumnIndex(Regiao.colNome)));                
                regiao.setCodigoImport(cursor.getString(cursor.getColumnIndex(Regiao.colCodigoImport)));                
                
                retorno.add(regiao);
            } while (cursor.moveToNext());
        }        
        
        return retorno;
    }

    @Override
    public ContentValues getContentValues(Regiao regiao) {
        ContentValues valores = new ContentValues();
        
        valores.put(Regiao.colNome, regiao.getNome());	
        valores.put(Regiao.colCodigoImport, regiao.getCodigoImport());	
        
        return valores;
    }
    
    public long inserirSeNaoExistir(Regiao regiao) {
        if (regiao == null)
            return 0;
        
        if (regiao.getId() > 0)
            return regiao.getId();
        
        if (G.strIsEmpty(regiao.getCodigoImport()))
            return 0;
            
        Regiao auxregiao = getBy(Regiao.colCodigoImport, regiao.getCodigoImport());
        
        if (auxregiao != null)
            return auxregiao.getId();
        else
            return incluir(regiao);
    }       

}