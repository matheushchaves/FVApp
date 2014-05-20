package com.forcadevenda.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import com.forcadevenda.activity.R;
import com.forcadevenda.uteis.G;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("DefaultLocale")
@SuppressWarnings("serial")
public class Orcamento extends GRegistro{
 
    private Date data;	 
    private int status;	 
    private Cliente cliente;	 
    private CondPagto condPagto;	 
    private double valorTotal;	 
    private String obs;	 
    
    private List<OrcamentoItem> itens;
    
    public static final String colData = "DATA";	 
    public static final String colStatus = "STATUS";	 
    public static final String colCliente = "IDCLIENTE";	 
    public static final String colCondPagto = "IDCONDPAGTO";	 
    public static final String colValorTotal = "VALORTOTAL";	 
    public static final String colObs = "OBS";	     

    public static final String[] colunas = new String[]{    
        colId,
        colData,
        colStatus,
        colCliente,
        colCondPagto,
        colValorTotal,
        colObs
    };
    
    public static final int STABERTO = 0;
    public static final int STCONCLUIDO = 1;
    public static final int STENVIADO = 2;

    public Orcamento(){
        cliente = new Cliente("");
        condPagto = new CondPagto("");
        itens = new ArrayList<OrcamentoItem>();
        this.data = new Date();
    }
    
    public static String getNomeStatus(Context ctx, int status){
        String retorno = "";
        switch (status) {
            case STABERTO: retorno = G.getString(ctx, R.string.storcaberto); break;
            case STCONCLUIDO: retorno = G.getString(ctx, R.string.storcconcluido); break;            
        }
        return retorno;
    }
    
    public String getNumero(){
        return String.format("%06d", id);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente != null)
            this.cliente = cliente;
    }

    public CondPagto getCondPagto() {
        return condPagto;
    }

    public void setCondPagto(CondPagto condPagto) {
        if (condPagto != null)
            this.condPagto = condPagto;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = G.strNotNull(obs);
    }

    public int getStatus() {
        return status;
    }
    
    public boolean isAberto(){
        return (status == STABERTO);
    }
    
    public String getStrStatus(Context ctx){
        return getNomeStatus(ctx, status);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getValorTotal() {
        return valorTotal;
    }
    
    public String getStrValorTotal(){
        return G.formatDouble(getValorTotal());
    }    

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<OrcamentoItem> getItens() {
        return itens;
    }

    public void setItens(List<OrcamentoItem> itens) {
        this.itens = itens;
    }
    
}
 
