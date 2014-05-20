package com.forcadevenda.classes;

import com.forcadevenda.uteis.G;

@SuppressWarnings("serial")
public class CondPagto extends GRegistroImport {
 
    private String descricao;	 
    private int parcelas;	 
    private double porcDesc;	 
        
    public static final String colDescricao = "DESCRICAO";	 
    public static final String colParcelas = "PARCELAS";	 
    public static final String colPorcDesc = "PORCDESC";	         
    
    public static final String[] colunas = new String[]{
        colId, 
        colCodigoImport,        
        colDescricao,
        colParcelas,
        colPorcDesc        
    };

    public CondPagto(String descricao) {
        this.descricao = G.strNotNull(descricao);        
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = G.strNotNull(descricao);
    }

    public int getParcelas() {
        return parcelas;
    }
    
    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public double getPorcDesc() {
        return porcDesc;
    }

    public void setPorcDesc(double porcDesc) {
        this.porcDesc = porcDesc;
    }
    	 
}
 
