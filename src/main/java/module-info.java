module com.ab.aplikacje_biznesowe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires itextpdf;
    requires java.desktop;
    requires org.apache.pdfbox;

    opens com.ab.aplikacje_biznesowe to javafx.fxml;
    exports com.ab.aplikacje_biznesowe;
}