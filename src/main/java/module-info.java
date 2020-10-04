module codebb.protoERP {
  requires transitive javafx.controls;
  requires transitive javafx.fxml;
  requires java.base;
  requires java.desktop;
  requires lombok;

  opens gr.codebb.protoERP to
      javafx.fxml;

  exports gr.codebb.protoERP;
}
