package com.forcadevenda.classes;

import com.forcadevenda.uteis.G;

@SuppressWarnings("serial")
public class Regiao extends GRegistroImport{
    
    private String nome;
    
    public static final String colNome = "NOME";
    
    public static final String[] colunas = new String[]{
        colId, 
        colNome, 
        colCodigoImport            
    };

    public Regiao(String nome) {
        this.nome = G.strNotNull(nome);
        this.codigoImport = "";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = G.strNotNull(nome);
    }
    
}
