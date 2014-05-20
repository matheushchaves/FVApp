package com.forcadevenda.activity;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.forcadevenda.classes.Orcamento;
import com.forcadevenda.uteis.G;

public class OrcamentosAdapter<T> extends GAdapter<Orcamento>{
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static OrcamentosAdapter criar(Context ctx, List<Orcamento> lista){
        return new OrcamentosAdapter(ctx, lista);
    }
    
    public OrcamentosAdapter(Context ctx, List<Orcamento> lista) {
        super(ctx, lista);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        
        Orcamento g = lista.get(position);

	LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.orcamentolinha, null);

	TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
        TextView tvNumero = (TextView) view.findViewById(R.id.tvNumero);
        TextView tvData = (TextView) view.findViewById(R.id.tvData);
        TextView tvValorTotal = (TextView) view.findViewById(R.id.tvValorTotal);        
	
        tvNome.setText(g.getCliente().getNome());
        tvNumero.setText(g.getNumero());
        tvData.setText(g.getData());
        tvValorTotal.setText(G.getString(ctx, R.string.moeda) + g.getStrValorTotal());
        
        if (!g.isAberto()){
            tvNome.setTextColor(G.getColor(ctx, R.color.textooculto));
            tvNumero.setTextColor(G.getColor(ctx, R.color.textooculto));
            tvData.setTextColor(G.getColor(ctx, R.color.textooculto));
            tvValorTotal.setTextColor(G.getColor(ctx, R.color.textooculto));            
        }

	return view;
    }

}
