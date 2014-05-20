package com.forcadevenda.activity;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forcadevenda.classes.Grupo;

public class GruposAdapter<T> extends GAdapter<Grupo>{
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static GruposAdapter criar(Context ctx, List<Grupo> lista){
        return new GruposAdapter(ctx, lista);
    }
    
    public GruposAdapter(Context ctx, List<Grupo> lista) {
        super(ctx, lista);
    }

    public View getView(int position, View convertView, ViewGroup parent) {  
        
        Grupo r = lista.get(position);

	LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.linha, null);

	TextView nome = (TextView) view.findViewById(R.id.tvNome);
	nome.setText(r.getDescricao());

	return view;
    }

}
