package com.forcadevenda.classes;

import com.forcadevenda.uteis.G;

@SuppressWarnings("serial")
public class Produto extends GRegistroImport{
 
    private String descricao;	 
    private double estoque;	 
    private double precoVenda;	     
    private Grupo grupo;	 
    private String un;	 
    private String codBarras;	 
    private String url;	 
    private String obs;	 
 
    public static final String colDescricao = "DESCRICAO";	 
    public static final String colEstoque = "ESTOQUE";	     
    public static final String colPrecoVenda = "PRECOVENDA";	     
    public static final String colGrupo = "IDGRUPO";	 
    public static final String colUn = "UN";	 
    public static final String colCodBarras = "CODBARRAS";	 
    public static final String colUrl = "URL";	 
    public static final String colObs = "OBS";   

    public static final String[] colunas = new String[]{    
        colId,
        colCodigoImport,
        colDescricao,
        colEstoque,
        colPrecoVenda,
        colGrupo,
        colUn,
        colCodBarras,
        colUrl,
        colObs
    };

    public Produto(String descricao) {
        this.descricao = G.strNotNull(descricao);
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = G.strNotNull(codBarras);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = G.strNotNull(descricao);
    }

    public double getEstoque() {
        return estoque;
    }
    
    public String getStrEstoque(){
        return G.formatDouble(estoque);
    }    

    public void setEstoque(double estoque) {
        this.estoque = estoque;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = G.strNotNull(obs);
    }

    public double getPrecoVenda() {
        return precoVenda;
    }
    
    public String getStrPrecoVenda(){
        return G.formatDouble(precoVenda);
    }

    public double getPrecoVenda(double porcDesc) {
        return precoVenda - (precoVenda * (porcDesc/100));
    }
    
    public String getStrPrecoVenda(double porcDesc){
        return G.formatDouble(getPrecoVenda(porcDesc));
    }    

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = G.strNotNull(un);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = G.strNotNull(url);
    }
    	         
}
 
