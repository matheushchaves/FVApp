package com.forcadevenda.activity;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.forcadevenda.classes.OrcamentoItem;
import com.forcadevenda.uteis.G;

public class OrcamentosItensAdapter<T> extends GAdapter<OrcamentoItem>{
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static OrcamentosItensAdapter criar(Context ctx, List<OrcamentoItem> lista){
        return new OrcamentosItensAdapter(ctx, lista);
    }
    
    public OrcamentosItensAdapter(Context ctx, List<OrcamentoItem> lista) {
        super(ctx, lista);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        
        OrcamentoItem g = lista.get(position);

	LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.orcamentoitemlinha, null);

	TextView tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
        TextView tvCodigo = (TextView) view.findViewById(R.id.tvCodigo);
        TextView tvQtde = (TextView) view.findViewById(R.id.tvQtde);
        TextView tvValorUnit = (TextView) view.findViewById(R.id.tvValorUnit);        
        TextView tvValorTotal = (TextView) view.findViewById(R.id.tvValorTotal);        
	
        tvDescricao.setText(g.getProduto().getDescricao());
        tvCodigo.setText(g.getProduto().getCodigoImport());
        tvQtde.setText(g.getStrQtde() + " x ");
        tvValorUnit.setText(g.getStrValor());
        tvValorTotal.setText(G.getString(ctx, R.string.moeda) + g.getStrValorTotal());

	return view;
    }

}
