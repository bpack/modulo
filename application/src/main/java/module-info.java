module com.ioglyph.modulo.application {
    requires com.ioglyph.modulo.core;
    requires com.ioglyph.modulo.persistence;
    requires com.ioglyph.modulo.downstream;
    requires com.ioglyph.modulo.rest;
    requires com.ioglyph.modulo.security;

    requires java.instrument;
    requires java.sql;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.context;

    requires org.slf4j;

    exports com.ioglyph.modulo.application;

    opens com.ioglyph.modulo.application to spring.core;
}
