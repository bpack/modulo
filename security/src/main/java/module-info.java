module com.ioglyph.modulo.security {
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;
    requires spring.beans;
    requires spring.context;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.security.web;
    requires spring.web;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports com.ioglyph.modulo.security;
    opens com.ioglyph.modulo.security to spring.core;
}