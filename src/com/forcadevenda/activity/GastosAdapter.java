package com.forcadevenda.activity;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.forcadevenda.classes.Gasto;
import com.forcadevenda.uteis.G;

public class GastosAdapter<T> extends GAdapter<Gasto>{
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static GastosAdapter criar(Context ctx, List<Gasto> lista){
        return new GastosAdapter(ctx, lista);
    }
    
    public GastosAdapter(Context ctx, List<Gasto> lista) {
        super(ctx, lista);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        
        Gasto g = lista.get(position);

	LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.gastolinha, null);

	TextView tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
        TextView tvData = (TextView) view.findViewById(R.id.tvData);
        TextView tvValorTotal = (TextView) view.findViewById(R.id.tvValorTotal);        
	
        tvDescricao.setText(g.getDescricao());
        tvData.setText(g.getData());
        tvValorTotal.setText(G.getString(ctx, R.string.moeda) + g.getStrValorTotal());

	return view;
    }

}
