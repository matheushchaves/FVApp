package com.forcadevenda.classes;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class GRegistro implements Serializable{
    
    protected long id;
    
    public static final String colId = "ID";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}
