package com.forcadevenda.activity;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forcadevenda.classes.CondPagto;
import com.forcadevenda.uteis.G;

public class CondPagtosAdapter<T> extends GAdapter<CondPagto>{
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static CondPagtosAdapter criar(Context ctx, List<CondPagto> lista){
        return new CondPagtosAdapter(ctx, lista);
    }
    
    public CondPagtosAdapter(Context ctx, List<CondPagto> lista) {
        super(ctx, lista);
    }
    
    private String getStrParcelas(int parcelas){
        if (parcelas == 0)
            return G.getString(ctx, R.string.condpagtoavista);
        else
            return (Integer.toString(parcelas) + " " + G.getString(ctx, R.string.labelcondpagtoparcelas));                 
    }
    
    private String getStrPorcDesc(double porcDesc){
        return (G.getString(ctx, R.string.labelcondpagtodesconto) + " " + Double.toString(porcDesc) + "%");        
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        
        CondPagto r = lista.get(position);

	LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.condpagtolinha, null);

	TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
        TextView tvParcelas = (TextView) view.findViewById(R.id.tvParcelas);
        TextView tvPorcDesc = (TextView) view.findViewById(R.id.tvPorcdesc);
        
	tvNome.setText(r.getDescricao());
        tvParcelas.setText(getStrParcelas(r.getParcelas()));
        tvPorcDesc.setText(getStrPorcDesc(r.getPorcDesc()));

	return view;
    }

}
