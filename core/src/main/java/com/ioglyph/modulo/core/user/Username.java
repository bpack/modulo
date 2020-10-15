package com.ioglyph.modulo.core.user;

/**
 * Domain primitive for a username.
 */
public class Username {
    private String value;

    public Username(String value){
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}