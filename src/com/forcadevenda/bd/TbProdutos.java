package com.forcadevenda.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.forcadevenda.classes.Produto;
import java.util.ArrayList;
import java.util.List;

public class TbProdutos extends GTabela<Produto>{
    
    private TbGrupos tbGrupos;

    public TbProdutos(Context ctx) {
        super("PRODUTOS", Produto.colunas, ctx);
        tbGrupos = new TbGrupos(ctx);
    }    
   
    @Override
    public List<Produto> listar(String[] colunas, String filtro, String ordem) {
        Cursor cursor = selecionar(colunas, filtro, ordem);
        List<Produto> retorno = new ArrayList<Produto>();
        
        if (cursor.getCount() == 0)
            return retorno;
        
        if (cursor.moveToFirst()) {
            do {
		Produto produto = new Produto("");
                
                produto.setId(cursor.getLong(cursor.getColumnIndex(Produto.colId))); 
                produto.setCodigoImport(cursor.getString(cursor.getColumnIndex(Produto.colCodigoImport))); 
                produto.setDescricao(cursor.getString(cursor.getColumnIndex(Produto.colDescricao))); 
                produto.setEstoque(cursor.getDouble(cursor.getColumnIndex(Produto.colEstoque)));                 
                produto.setPrecoVenda(cursor.getDouble(cursor.getColumnIndex(Produto.colPrecoVenda)));                 
                produto.setGrupo(tbGrupos.get(cursor.getLong(cursor.getColumnIndex(Produto.colGrupo))));
                produto.setUn(cursor.getString(cursor.getColumnIndex(Produto.colUn))); 
                produto.setCodBarras(cursor.getString(cursor.getColumnIndex(Produto.colCodBarras))); 
                produto.setUrl(cursor.getString(cursor.getColumnIndex(Produto.colUrl))); 
                produto.setObs(cursor.getString(cursor.getColumnIndex(Produto.colObs)));                 

                retorno.add(produto);
            } while (cursor.moveToNext());
        }        
        
        return retorno;
    }

    @Override
    public ContentValues getContentValues(Produto produto) {
        ContentValues valores = new ContentValues();
        
        long grupo = tbGrupos.inserirSeNaoExistir(produto.getGrupo());
        
        valores.put(Produto.colCodigoImport, produto.getCodigoImport());
        valores.put(Produto.colDescricao, produto.getDescricao());
        valores.put(Produto.colEstoque, produto.getEstoque());
        valores.put(Produto.colPrecoVenda, produto.getPrecoVenda());
        valores.put(Produto.colGrupo, grupo);
        valores.put(Produto.colUn, produto.getUn());
        valores.put(Produto.colCodBarras, produto.getCodBarras());
        valores.put(Produto.colUrl, produto.getUrl());
        valores.put(Produto.colObs, produto.getObs());        
        
        return valores;
    }
    
}
