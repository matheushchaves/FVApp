package com.forcadevenda.classes;

import com.forcadevenda.uteis.G;

@SuppressWarnings("serial")
public class Grupo extends GRegistroImport{
 
    private String descricao;	 
        
    public static final String colDescricao = "DESCRICAO";	         
	 
    public static final String[] colunas = new String[]{
        colId, 
        colCodigoImport,        
        colDescricao
    };

    public Grupo(String descricao) {
        this.descricao = G.strNotNull(descricao);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = G.strNotNull(descricao);
    }
        
}
 
