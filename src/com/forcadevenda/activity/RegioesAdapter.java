package com.forcadevenda.activity;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forcadevenda.classes.Regiao;

public class RegioesAdapter<T> extends GAdapter<Regiao>{
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static RegioesAdapter criar(Context ctx, List<Regiao> lista){
        return new RegioesAdapter(ctx, lista);
    }
    
    public RegioesAdapter(Context ctx, List<Regiao> lista) {
        super(ctx, lista);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        
        Regiao r = lista.get(position);

	LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.linha, null);

	TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
	tvNome.setText(r.getNome());

	return view;
    }

}
