package com.forcadevenda.bd;

import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import com.forcadevenda.classes.*;
import com.forcadevenda.uteis.G;
import java.util.ArrayList;
import java.util.List;

public class TbGrupos extends GTabela<Grupo>{
    
    public TbGrupos(Context ctx) {
        super("GRUPOS", Grupo.colunas, ctx);
    }
   
    @Override    
    public List<Grupo> listar(String[] colunas, String filtro, String ordem){
        Cursor cursor = selecionar(colunas, filtro, ordem);
        List<Grupo> retorno = new ArrayList<Grupo>();
                
        if (cursor.getCount() == 0)
            return retorno;
        
        if (cursor.moveToFirst()) {
            do {
		Grupo grupo = new Grupo("");

                grupo.setId(cursor.getLong(cursor.getColumnIndex(Grupo.colId)));
                grupo.setDescricao(cursor.getString(cursor.getColumnIndex(Grupo.colDescricao)));                                
                grupo.setCodigoImport(cursor.getString(cursor.getColumnIndex(Grupo.colCodigoImport)));
                
                retorno.add(grupo);
            } while (cursor.moveToNext());
        }        
        
        return retorno;
    }

    @Override
    public ContentValues getContentValues(Grupo grupo) {
        ContentValues valores = new ContentValues();
        
        valores.put(Grupo.colDescricao, grupo.getDescricao());	
        valores.put(Grupo.colCodigoImport, grupo.getCodigoImport());	
        
        return valores;
    }
    
    public long inserirSeNaoExistir(Grupo grupo) {
        if (grupo == null)
            return 0;
        
        if (grupo.getId() > 0)
            return grupo.getId();
        
        if (G.strIsEmpty(grupo.getCodigoImport()))
            return 0;
            
        Grupo aux = getBy(Grupo.colCodigoImport, grupo.getCodigoImport());
        
        if (aux != null)
            return aux.getId();
        else
            return incluir(grupo);
    }      

}
