package com.forcadevenda.uteis;

import java.util.Date;

public class GSQL {
    
    public static String filtroAnd(String filtro, String add){
        return G.strAdd(filtro, add, " and ");
    }
    
    public static String filtroOr(String filtro, String add){
        return G.strAdd(filtro, add, " or ");
    }    
    
    public static String filtro(String coluna, String operador, String valor, boolean entreAspas){
        if (G.strIsEmpty(valor))
            return "";        
        
        if (entreAspas)
            valor = G.strEntreAspas(valor);
        
        return G.strEntreParenteses(coluna + G.strEntreEspacos(operador) + valor);
    }
    
    public static String filtro(String coluna, String operador, String valor){
        return filtro(coluna, operador, valor, true);
    }
    
    public static String filtro(String coluna, String operador, int valor){
        if (valor <= G.INTNULL)
            return "";
        
        return G.strEntreParenteses(coluna + G.strEntreEspacos(operador) + Integer.toString(valor));
    }
    
    public static String filtro(String coluna, String operador, long valor){
        if (valor <= G.INTNULL)
            return "";
        
        return G.strEntreParenteses(coluna + G.strEntreEspacos(operador) + Long.toString(valor));
    }    
    
    public static String filtroLike(String coluna, String valor){
        if (G.strIsEmpty(valor))
            return "";
        
        return filtro(coluna, "Like", G.strEntrePorcentagem(valor));
    }    

    public static String filtroId(String coluna, Long id){
        return filtro(coluna, "=", Long.toString(id));
    }

    public static String filtroInicioFim(String coluna, String inicio, String fim) {
        return "";
    }

    public static String filtroData(String coluna, String operador, Date valor, String format) {
        if (valor == null)
            return "";
        
        //"strftime('%d/%m/%Y', DATA) = ('14/11/2012')";        
        return G.strEntreParenteses("date("+ coluna +")" + G.strEntreEspacos(operador) + G.strEntreAspas(G.dateToStr(format, valor)));
    }
    
    public static String filtroData(String coluna, String operador, Date valor){
        return filtroData(coluna, operador, valor, G.MASCDATADB);
    }
    
    public static String filtroData(String coluna, String operador, String valor){
        if (G.strIsEmpty(valor))
            return "";
        
        return filtroData(coluna, operador, G.strToDate(G.MASCDATA, valor));
    }
    
}
