package com.forcadevenda.classes;

import com.forcadevenda.uteis.G;

@SuppressWarnings("serial")
public class OrcamentoItem extends GRegistro{
 
    private long idOrcamento;
    private Produto produto;
    private double qtde;
    private double valorBruto;
    private double valor;
    	
    public static final String colOrcamento = "IDORCAMENTO";
    public static final String colProduto = "IDPRODUTO";
    public static final String colQtde = "QTDE";
    public static final String colValorBruto = "VALORBRUTO";    
    public static final String colValor = "VALOR";    
    
    public static final String[] colunas = new String[]{
        colId, 
        colOrcamento,        
        colProduto,
        colQtde,
        colValorBruto,
        colValor
    };

    public OrcamentoItem(long idOrcamento) {
        this.idOrcamento = idOrcamento;
        produto = new Produto("");
        qtde = 1;
    }

    public long getIdOrcamento() {
        return idOrcamento;
    }

    public void setIdOrcamento(long idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getQtde() {
        return qtde;
    }
        
    public String getStrQtde(){
        return (String.valueOf(getQtde()));
    } 

    public void setQtde(double qtde) {
        this.qtde = qtde;
    }

    public double getValorBruto() {
        return valorBruto;
    }
    
    public String getStrValorBruto(){
        return (G.formatDouble(getValorBruto()));
    }

    public void setValorBruto(double valorBruto) {
        this.valorBruto = valorBruto;
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
    
    public double getValorTotal(){
        return valor * qtde;
    }
    
    public String getStrValorTotal(){
        return G.formatDouble(getValorTotal());
    }    
    
}
 
