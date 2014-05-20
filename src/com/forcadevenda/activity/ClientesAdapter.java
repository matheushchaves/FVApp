package com.forcadevenda.activity;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forcadevenda.classes.Cliente;

public class ClientesAdapter<T> extends GAdapter<Cliente>{
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static ClientesAdapter criar(Context ctx, List<Cliente> lista){
        return new ClientesAdapter(ctx, lista);
    }
    
    public ClientesAdapter(Context ctx, List<Cliente> lista) {
        super(ctx, lista);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        
        Cliente c = lista.get(position);

	LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view = inflater.inflate(R.layout.clientelinha, null);

	TextView nome = (TextView) view.findViewById(R.id.tvNome);
        TextView endereco = (TextView) view.findViewById(R.id.tvEndereco);
	
        nome.setText(c.getNome());
        endereco.setText(c.getEnderecoCompleto());

	return view;
    }

}
