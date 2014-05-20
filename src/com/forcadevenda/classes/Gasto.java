package com.forcadevenda.classes;

import com.forcadevenda.uteis.G;
import java.util.Date;

@SuppressWarnings("serial")
public class Gasto extends GRegistro{
 
    private Date data;	 
    private String descricao;	 
    private double qtde;	 
    private double valor;	     
 
    public static final String colData = "DATA";	 
    public static final String colDescricao = "DESCRICAO";	 
    public static final String colQtde = "QTDE";	 
    public static final String colValor = "VALOR";	     
    
    public static final String[] colunas = new String[]{        
        colId,	 
        colData,
        colDescricao,
        colQtde,
        colValor
    };

    public Gasto(String descricao) {
        this.descricao = G.strNotNull(descricao);
        this.qtde = 1;
        this.data = new Date();
    }

    public String getData() {
        return G.dateToStr(data);
    }
    
    public String getData(String format) {
        return G.dateToStr(format, data);
    }    

    public void setData(String data) {
        this.data = G.strToDate(data);
    }
    
    public void setData(String format, String data){
        this.data = G.strToDate(format, data);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = G.strNotNull(descricao);
    }

    public double getQtde() {
        return qtde;
    }

    public void setQtde(double qtde) {
        this.qtde = qtde;
    }
    
    public String getStrQtde(){
        return (String.valueOf(getQtde()));
    }    

    public double getValor() {
        return valor;
    }
    
    public String getStrValor(){
        return (G.formatDouble(getValor()));
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValorTotal() {
        return valor * qtde;
    }
    
    public String getStrValorTotal(){
        return G.formatDouble(getValorTotal());
    }
    
}
 
