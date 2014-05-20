package com.forcadevenda.activity;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forcadevenda.classes.Produto;
import com.forcadevenda.uteis.G;

public class ProdutosAdapter<T> extends GAdapter<Produto>{
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static ProdutosAdapter criar(Context ctx, List<Produto> lista){
        return new ProdutosAdapter(ctx, lista);
    }
    
    public ProdutosAdapter(Context ctx, List<Produto> lista) {
        super(ctx, lista);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        
        Produto r = lista.get(position);

	LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.produtolinha, null);

	TextView nome = (TextView) view.findViewById(R.id.tvNome);
        TextView codigo = (TextView) view.findViewById(R.id.tvCodigoImport);
        TextView precovenda = (TextView) view.findViewById(R.id.tvPrecoVenda);
        
	nome.setText(r.getDescricao());
        codigo.setText(r.getCodigoImport());
        precovenda.setText(G.getString(ctx, R.string.moeda) + r.getStrPrecoVenda());

	return view;
    }

}
