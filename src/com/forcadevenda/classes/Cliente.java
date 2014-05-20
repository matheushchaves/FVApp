package com.forcadevenda.classes;

import com.forcadevenda.uteis.G;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Cliente extends GRegistroImport{
    
    private boolean ativo;
    private String nome;    
    private String fantasia;
    private String telefone;
    private String pessoa;
    private String documento1;
    private String documento2;
    private String cep;
    private String endereco;
    private String num;
    private String bairro;
    private String complEnd;
    private String cidade;
    private String uf;
    private Regiao regiao;
    private String email;    
    private String responsavel;
    private String obs;    
       
    public static final String colAtivo = "ATIVO";
    public static final String colNome = "NOME";        
    public static final String colFantasia = "FANTASIA";
    public static final String colTelefone = "TELEFONE";    
    public static final String colPessoa = "PESSOA";
    public static final String colDocumento1 = "DOCUMENTO1";
    public static final String colDocumento2 = "DOCUMENTO2";
    public static final String colCep = "CEP";
    public static final String colEndereco = "ENDERECO";
    public static final String colNum = "NUM";
    public static final String colBairro = "BAIRRO";
    public static final String colComplEnd = "COMPLEND";
    public static final String colCidade = "CIDADE";
    public static final String colUf = "UF";
    public static final String colRegiao = "IDREGIAO";
    public static final String colEmail = "EMAIL";    
    public static final String colResponsavel = "RESPONSAVEL";
    public static final String colObs = "OBS";    
    
    public static String PESSOAFISICA = "F";
    public static String PESSOAJURIDICA = "J";
    public static final String[] colunas = new String[]{
        colId, 
        colAtivo, 
        colNome,        
        colFantasia,
        colTelefone,
        colPessoa,
        colDocumento1,
        colDocumento2,
        colCep,
        colEndereco,
        colNum,
        colBairro,
        colComplEnd,
        colCidade,
        colUf,
        colRegiao,
        colEmail,    
        colResponsavel,
        colObs,
        colCodigoImport
    };
    
    public Cliente(String nome){
        this.nome = G.strNotNull(nome);
        this.pessoa = PESSOAJURIDICA;
        this.uf = "PB";
        regiao = new Regiao("");
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = G.strNotNull(bairro);
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = G.strNotNull(cep);
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = G.strNotNull(cidade);
    }

    public String getComplEnd() {
        return complEnd;
    }

    public void setComplEnd(String complEnd) {
        this.complEnd = G.strNotNull(complEnd);
    }

    public String getDocumento1() {
        return documento1;
    }

    public void setDocumento1(String documento1) {
        this.documento1 = G.strNotNull(documento1);
    }

    public String getDocumento2() {
        return documento2;
    }

    public void setDocumento2(String documento2) {
        this.documento2 = G.strNotNull(documento2);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = G.strNotNull(email);
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = G.strNotNull(endereco);
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = G.strNotNull(fantasia);
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = G.strNotNull(telefone);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = G.strNotNull(nome);
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = G.strNotNull(num);
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = G.strNotNull(obs);
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = G.strNotNull(pessoa);
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        if (regiao != null)
            this.regiao = regiao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = G.strNotNull(responsavel);
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = G.strNotNull(uf);
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public String getEnderecoCompleto(){
        String retorno = getEndereco();
        retorno = G.strAdd(retorno, getNum(), ", ");
        retorno = G.strAdd(retorno, getComplEnd(), ", ");
        retorno = G.strAdd(retorno, getBairro(), ", ");
        retorno = G.strAdd(retorno, getCidade(), " - ");
        retorno = G.strAdd(retorno, getUf(), " ");
        
        return retorno;
    }
    
    public static List<String> getEstados(){
        List<String> estados = new ArrayList<String>();
        
        estados.add("AC");
        estados.add("AL");
        estados.add("AM");
        estados.add("AP");
        estados.add("BA");
        estados.add("CE");
        estados.add("DF");
        estados.add("ES");
        estados.add("EX");
        estados.add("GO");
        estados.add("MA");
        estados.add("MG");
        estados.add("MS");
        estados.add("MT");
        estados.add("PA");
        estados.add("PB");
        estados.add("PE");
        estados.add("PI");
        estados.add("PR");
        estados.add("RJ");
        estados.add("RN");
        estados.add("RO");
        estados.add("RR");
        estados.add("RS");
        estados.add("SC");
        estados.add("SE");
        estados.add("SP");
        estados.add("TO");
        
        return estados;
    }    
    
}
