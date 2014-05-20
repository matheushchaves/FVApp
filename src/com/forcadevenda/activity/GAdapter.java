package com.forcadevenda.activity;

import java.util.List;
import android.content.Context;
import android.widget.BaseAdapter;

public abstract class GAdapter<T> extends BaseAdapter {
    
    protected Context ctx;    
    protected List<T> lista;
    
    public GAdapter(Context ctx, List<T> lista){
        this.ctx = ctx;
        this.lista = lista;
    }

    public int getCount() {
        return lista.size();
    }

    public Object getItem(int position) {
        return lista.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
}
