module alissouali.amdalatjinadu.crossword {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens alissouali.amdalatjinadu.crossword to javafx.fxml;
    opens alissouali.amdalatjinadu.crossword.controller to javafx.fxml;
    exports alissouali.amdalatjinadu.crossword;
    exports  alissouali.amdalatjinadu.crossword.controller;
}