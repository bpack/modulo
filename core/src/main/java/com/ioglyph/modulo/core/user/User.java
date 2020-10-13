package com.ioglyph.modulo.core.user;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private UUID id;
    private Username username;
    private String email;
    private String ip;
    private OffsetDateTime created;
    private OffsetDateTime updated;

    private boolean visible = false;
    private OffsetDateTime deleted;

    public User(UUID id, String name, String email, String ip, OffsetDateTime created, OffsetDateTime updated, boolean visible, OffsetDateTime deleted){
        this.id = id;
        this.username = new Username(validate(Validation.USERNAME, name));
        this.email = validate(Validation.EMAIL, email);
        this.ip = validate(Validation.IP, ip);
        this.created = created;
        this.updated = updated;
        this.visible = visible;
        this.deleted = deleted;
    }

    public void delete(){
        this.visible = false;
        this.updated = OffsetDateTime.now();
        this.deleted = OffsetDateTime.now();
    }

    public UUID id(){
        return this.id;
    }

    public Username username(){
        return this.username;
    }

    public String email(){
        return this.email;
    }

    public String lastIp() { return this.ip; }

    public OffsetDateTime created(){
        return this.created;
    }

    public OffsetDateTime updated(){
        return this.updated;
    }

    public boolean visible(){
        return this.visible;
    }

    public OffsetDateTime deleted(){
        return this.deleted;
    }

    private String validate(Validation validation, String value){
        String regex = validation.regex;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if(!matcher.matches()){
            throw new IllegalArgumentException("Value " + value + " is invalid.");
        }
        return value;

    }

    private static final String SUB_IP = "([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private enum Validation {

        EMAIL("^[A-Za-z0-9+_.-]+@(.+)$"),
        IP("^" + SUB_IP + "\\." +
                SUB_IP + "\\." +
                SUB_IP + "\\." +
                SUB_IP + "$"),
        USERNAME("^[A-Za-z0-9+_.-]+$");

        private final String regex;

        Validation(String regex){
            this.regex = regex;
        }
    }
}
