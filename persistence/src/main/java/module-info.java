module com.ioglyph.modulo.persistence {
    requires transitive com.ioglyph.modulo.core;

    requires transitive java.persistence;
    requires com.fasterxml.classmate;
    requires net.bytebuddy;
    requires spring.data.jpa;
    requires spring.tx;

    requires jdk.unsupported;
    requires spring.data.commons;

    exports com.ioglyph.modulo.persistence.user;

    opens com.ioglyph.modulo.persistence.user to org.hibernate.orm.core, spring.core;
}
