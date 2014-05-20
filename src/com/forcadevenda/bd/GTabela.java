package com.forcadevenda.bd;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.forcadevenda.classes.GRegistro;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GSQL;
public abstract class GTabela <T extends GRegistro> {
    
    protected String nomeTab;
    protected String[] colunas;        
    protected SQLiteDatabase conexao;
    protected Context ctx;    
    
    public GTabela(String nomeTab, String[] colunas, Context ctx, SQLiteDatabase conexao){
        this.nomeTab = nomeTab;        
        this.colunas = colunas;
        this.ctx = ctx;        
        this.conexao = conexao;
    }
    
    public GTabela(String nomeTab, String[] colunas, Context ctx){
        this(nomeTab, colunas, ctx, GConexao.getConexao(ctx));
    }
    
    public void close(){
        conexao.close();
    }
    
    public long incluir(ContentValues valores){
        return conexao.insert(nomeTab, null, valores);                            
    }
    
    public long incluir(T objeto){
        return incluir(getContentValues(objeto));
    }
    
    public boolean editar(String filtro, ContentValues valores){
        return conexao.update(nomeTab, valores, filtro, null) > 0;
    }
    
    public boolean editar(T objeto){
        return editar(GSQL.filtroId(GRegistro.colId, objeto.getId()), getContentValues(objeto));
    }    
    
    public boolean deletar(String filtro){
        return conexao.delete(nomeTab, filtro, null) > 0;
    }    
    
    public boolean deletar(long id){
        return deletar(GSQL.filtroId(GRegistro.colId, id));
    }      
    
    public Cursor query(String q){
        return conexao.rawQuery(q, null);
    }
    
    public Cursor selecionar(String[] colunas, String filtro, String ordem){
        try{
            return conexao.query(nomeTab, colunas, filtro, null, null, null, ordem);
        }catch(Exception e){
            G.msgErro(ctx, e.getMessage());
            return null;
        }
    }
    
    public List<T> listar(String filtro, String ordem){
        return listar(colunas, filtro, ordem);
    }
    
    public List<T> listar(String filtro){
        return listar(filtro, "");
    }
    
    public List<T> listar(){
        return listar("");
    }

    public boolean gravar(T objeto){
        if (objeto.getId() == G.INTNULL)
            return false;
        
        return gravarBy(GRegistro.colId, Long.toString(objeto.getId()), objeto);
    }     
    
    public boolean gravarBy(String coluna, String chave, T objeto){
    	
    	if (G.strIsEmpty(chave))
            return false;
        
        T aux = getBy(coluna, chave);
        if (aux == null)
            return (incluir(objeto) > 0);                
        else{
            objeto.setId(aux.getId());
            return (editar(objeto));         
        }
    }        
    
    public T get(long id){
        return getBy(GRegistro.colId, id);
    }
    
    public T getBy(String coluna, long valor){
        if (valor == G.INTNULL)
            return null;
        
        return getBy(coluna, Long.toString(valor), false);
    }    
    
    public T getBy(String coluna, String valor){
        return getBy(coluna, valor, true);
    }   
    
    protected T getBy(String coluna, String valor, boolean entreAspas) {
        if (G.strIsEmpty(valor))
            return null;
        
        List<T> lista = listar(GSQL.filtro(coluna, "=", valor, entreAspas), "");
        
        if (lista.isEmpty())
            return null;
        else
            return lista.get(0);
    }     

    public String[] getColunas() {
        return colunas;
    }

    public SQLiteDatabase getConexao() {
        return conexao;
    }

    public String getNomeTab() {
        return nomeTab;
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
           
    public abstract List<T> listar(String[] colunas, String filtro, String ordem);    
    
    public abstract ContentValues getContentValues(T objeto);
            
}
