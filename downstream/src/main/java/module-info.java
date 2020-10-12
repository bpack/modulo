module com.ioglyph.modulo.downstream {
	requires transitive com.ioglyph.modulo.core;

	requires java.xml.bind;
	requires java.net.http;

	exports com.ioglyph.modulo.downstream.factory;

	opens com.ioglyph.modulo.downstream.api to java.xml.bind;
}
