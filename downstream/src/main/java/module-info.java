module com.ioglyph.modulo.downstream {
	requires transitive com.ioglyph.modulo.core;

	requires java.xml.bind;

	exports com.ioglyph.modulo.downstream.factory;

//	opens com.ioglyph.modulo.downstream.api to java.xml.bind;
}
