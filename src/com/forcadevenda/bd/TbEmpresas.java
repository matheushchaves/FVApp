package com.forcadevenda.bd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;



import com.forcadevenda.classes.Empresa;


public class TbEmpresas extends GTabela<Empresa>{

	public TbEmpresas(Context ctx) {
        super("EMPRESAS", Empresa.colunas, ctx);
    }

	public List<Empresa> listar(String[] colunas, String filtro, String ordem) {
        Cursor cursor = selecionar(colunas, filtro, ordem);
        List<Empresa> retorno = new ArrayList<Empresa>();
                
        if (cursor.getCount() == 0)
            return retorno;
        
        if (cursor.moveToFirst()) {
            do {
		Empresa empresa = new Empresa();

                
		        empresa.setId(cursor.getLong(cursor.getColumnIndex(Empresa.colId))); 
                empresa.setRasoemp(cursor.getString(cursor.getColumnIndex(Empresa.colRasoemp)));
                empresa.setFtpdemp(cursor.getString(cursor.getColumnIndex(Empresa.colFtpdemp)));
                empresa.setFtpuser(cursor.getString(cursor.getColumnIndex(Empresa.colFtpuser)));
                empresa.setFtppass(cursor.getString(cursor.getColumnIndex(Empresa.colFtppass)));
                empresa.setCodiven(cursor.getString(cursor.getColumnIndex(Empresa.colCodiven)));
                empresa.setCodigoImport(cursor.getString(cursor.getColumnIndex(Empresa.colCodigoImport)));                
                retorno.add(empresa);
            } while (cursor.moveToNext());
        }        
        return retorno;
    }
	
	@Override
	 public ContentValues getContentValues(Empresa empresa) {
        ContentValues valores = new ContentValues();
        
        
        
        valores.put(Empresa.colId, empresa.getId());
        valores.put(Empresa.colCodigoImport, empresa.getCodigoImport());
        valores.put(Empresa.colRasoemp, empresa.getRasoemp());	                
        valores.put(Empresa.colFtpdemp, empresa.getFtpdemp());	                
        valores.put(Empresa.colFtpuser, empresa.getFtpuser());
        valores.put(Empresa.colFtppass, empresa.getFtppass());
        valores.put(Empresa.colCodiven, empresa.getCodiven());
        
        
        
        return valores;
    }     
	    
}
