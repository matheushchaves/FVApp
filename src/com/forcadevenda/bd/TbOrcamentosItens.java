package com.forcadevenda.bd;

import com.forcadevenda.uteis.GSQL;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import com.forcadevenda.classes.*;
import java.util.ArrayList;
import java.util.List;
import com.forcadevenda.uteis.G;

public class TbOrcamentosItens extends GTabela<OrcamentoItem>{
    
    private TbProdutos tbProdutos;
    
    public TbOrcamentosItens(Context ctx) {
        super("ORCAMENTOSITENS", OrcamentoItem.colunas, ctx);
        tbProdutos = new TbProdutos(ctx);
    }
   
    @Override    
    public List<OrcamentoItem> listar(String[] colunas, String filtro, String ordem){
        Cursor cursor = selecionar(colunas, filtro, ordem);
        List<OrcamentoItem> retorno = new ArrayList<OrcamentoItem>();
                
        if (cursor.getCount() == 0)
            return retorno;
        
        if (cursor.moveToFirst()) {
            do {
		OrcamentoItem orcamentoItem = new OrcamentoItem(0);

                orcamentoItem.setId(cursor.getLong(cursor.getColumnIndex(OrcamentoItem.colId)));
                orcamentoItem.setIdOrcamento(cursor.getLong(cursor.getColumnIndex(OrcamentoItem.colOrcamento)));
                orcamentoItem.setProduto(tbProdutos.get(cursor.getLong(cursor.getColumnIndex(OrcamentoItem.colProduto))));
                orcamentoItem.setQtde(cursor.getDouble(cursor.getColumnIndex(OrcamentoItem.colQtde)));
                orcamentoItem.setValorBruto(cursor.getDouble(cursor.getColumnIndex(OrcamentoItem.colValorBruto)));
                orcamentoItem.setValor(cursor.getDouble(cursor.getColumnIndex(OrcamentoItem.colValor)));
                
                retorno.add(orcamentoItem);
            } while (cursor.moveToNext());
        }        
        
        return retorno;
    }
    
    public List<OrcamentoItem> listarByOrcamento (long idOrcamento){
        return listar(GSQL.filtroId(OrcamentoItem.colOrcamento, idOrcamento));
    }        
    
    public Double totalizar(long idOrcamento){
        Cursor cursor = query(G.strAdd("SELECT SUM("+ OrcamentoItem.colQtde +" * "+ OrcamentoItem.colValor +") FROM ORCAMENTOSITENS", GSQL.filtroId(OrcamentoItem.colOrcamento, idOrcamento), " WHERE "));
        
        if (cursor.getCount() == 0)
             return 0.0;
        
        if(cursor.moveToFirst())
            return cursor.getDouble(0);
        else
            return 0.0;
    }       

    @Override
    public ContentValues getContentValues(OrcamentoItem orcamentoItem) {
        ContentValues valores = new ContentValues();
        
        valores.put(OrcamentoItem.colOrcamento, orcamentoItem.getIdOrcamento());	
        valores.put(OrcamentoItem.colProduto, orcamentoItem.getProduto().getId());	
        valores.put(OrcamentoItem.colQtde, orcamentoItem.getQtde());	
        valores.put(OrcamentoItem.colValorBruto, orcamentoItem.getValorBruto());	
        valores.put(OrcamentoItem.colValor, orcamentoItem.getValor());	
        
        return valores;
    }

}
