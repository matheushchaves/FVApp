package com.forcadevenda.sinc;

import android.annotation.SuppressLint;
import android.util.Log;

import com.forcadevenda.bd.TbEmpresas;
import com.forcadevenda.classes.GRegistro;
import com.forcadevenda.uteis.G;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
@SuppressWarnings("unused")
public class TbXml<T extends GRegistro> {
	private String vendedor;
	private String data;
	private List<T> valores;

	public TbXml(List<T> valores) {

		this.valores = valores;
		this.vendedor = G.prefCodVend;
		this.data = G.dateToStr(G.data());
	}

	public TbXml() {
		this(new ArrayList<T>());
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@SuppressWarnings("rawtypes")
	public List getValores() {

		return valores;
	}

	public void setValores(List<T> valores) {
		this.valores = valores;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

}