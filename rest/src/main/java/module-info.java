module com.ioglyph.modulo.rest {
    requires transitive com.ioglyph.modulo.core;
    requires spring.web;
    requires org.slf4j;

    exports com.ioglyph.modulo.rest;
    exports com.ioglyph.modulo.rest.data to com.fasterxml.jackson.databind;
}