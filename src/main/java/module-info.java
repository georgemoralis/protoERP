module codebb.protoerp {
  requires transitive javafx.controls;
  requires transitive javafx.fxml;
  requires java.base;
  requires java.desktop;
  requires lombok;
  requires org.controlsfx.controls;
  requires com.jfoenix;
  requires gr.codebb.ctl;

  opens gr.codebb.protoerp to
      javafx.fxml;

  exports gr.codebb.protoerp;
}
