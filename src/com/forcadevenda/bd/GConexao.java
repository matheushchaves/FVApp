package com.forcadevenda.bd;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import com.forcadevenda.uteis.G;
import com.forcadevenda.activity.R;

public class GConexao {
    
    public static SQLiteDatabase getConexao(Context ctx){
        return ctx.openOrCreateDatabase(G.BDNOME, 0, null);        
    }
    
    public static boolean verificarBd(Context ctx){        
        try{
            SQLiteDatabase conexaoteste = getConexao(ctx);

            try{    
                conexaoteste.execSQL(ctx.getString(R.string.createregioes));
                conexaoteste.execSQL(ctx.getString(R.string.createclientes));
                conexaoteste.execSQL(ctx.getString(R.string.creategastos));
                conexaoteste.execSQL(ctx.getString(R.string.createcondpagtos));
                conexaoteste.execSQL(ctx.getString(R.string.creategrupos));
                conexaoteste.execSQL(ctx.getString(R.string.createprodutos));
                conexaoteste.execSQL(ctx.getString(R.string.createorcamentos));
                conexaoteste.execSQL(ctx.getString(R.string.createorcamentositens));
                conexaoteste.execSQL(ctx.getString(R.string.createempresas));
                
                return true;
            } finally{
                conexaoteste.close();
            }
        }catch(Exception e){            
            G.msgErro(ctx, G.getString(ctx, R.string.errobdiniciar) + e.getMessage());                        
            return false;
        }
    }    
    
}
