module codebb.protoerp {
  requires transitive javafx.controls;
  requires transitive javafx.fxml;
  requires java.base;
  requires java.desktop;
  requires lombok;
  requires org.controlsfx.controls;
  requires com.jfoenix;
  requires gr.codebb.ctl;
  requires gr.codebb.util;
  requires org.kordamp.ikonli.fontawesome;
  requires org.hibernate.orm.core;
  requires java.persistence;

  opens gr.codebb.protoerp to
      javafx.fxml;

  exports gr.codebb.protoerp;
}
