module codebb.protoerp {
  requires transitive javafx.controls;
  requires transitive javafx.fxml;
  requires javafx.web;
  requires java.base;
  requires java.desktop;
  requires java.net.http;
  requires lombok;
  requires org.controlsfx.controls;
  requires com.jfoenix;
  requires gr.codebb.ctl;
  requires gr.codebb.util;
  requires gr.codebb.webserv;
  requires gr.codebb.codebblib;
  requires org.kordamp.ikonli.fontawesome;
  requires org.hibernate.orm.core;
  requires java.persistence;
  requires java.sql;
  requires liquibase.core;
  requires org.jinq.api;
  requires org.jinq.jpa;
  requires validatorfx;
  requires shiro.core;
  requires org.apache.logging.log4j;
  requires org.apache.logging.log4j.core;
  requires org.apache.log4j;

  opens gr.codebb.protoerp to
      javafx.fxml;

  exports gr.codebb.protoerp;
}
