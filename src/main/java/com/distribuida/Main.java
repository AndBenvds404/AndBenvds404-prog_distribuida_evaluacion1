package com.distribuida;

import com.distribuida.db.Book;
import com.distribuida.servicios.ServicioBook;
import com.distribuida.servicios.ServicioBookImpl;

import com.google.gson.Gson;
import io.helidon.config.Config;
import io.helidon.webserver.WebServer;

import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.enterprise.inject.spi.CDI;

import jakarta.inject.Inject;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;


public class Main {

    private static ContainerLifecycle lifecycle;

    static ServicioBook servicioBook;

    static Gson gson = new Gson();


    public static void main(String[] args) {

        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);

        servicioBook = CDI.current().select(ServicioBook.class).get();

        WebServer server = WebServer.builder()
                .port(8080)
                .routing(builder -> builder
                        .get("/books/{id}", Main::buscarPorId)
                        .get("/books", Main::listarTodos)
                        .post("/books", Main::insertar)
                        .put("/books/{id}", Main::actualizar)
                        .delete("/books/{id}", Main::borrar)
                )
                .build();

        server.start();

        servicioBook.listarTodos().stream().forEach(System.out::println);

        shutdown();
    }



    public static void shutdown() {
        lifecycle.stopApplication(null);
    }

    static void buscarPorId(ServerRequest req, ServerResponse res) {
        res.send(gson.toJson(servicioBook.buscar(Integer.valueOf(req.path().
                pathParameters().get("id"))
        )));
    }

    static void listarTodos(ServerRequest req, ServerResponse res) {
        res.send(gson.toJson(servicioBook.listarTodos()));
    }

    static void insertar(ServerRequest req, ServerResponse res) {
        Book book = gson.fromJson(req.content().as(String.class),Book.class);

        res.send(gson.toJson(servicioBook.insertar(book)));
    }

    static void actualizar(ServerRequest req, ServerResponse res) {

        var book = gson.fromJson(req.content().as(String.class), Book.class);
        book.setId(book.getId());
        res.send(gson.toJson(servicioBook.actualizar(book)));
    }


    static void borrar(ServerRequest req, ServerResponse res) {

        servicioBook.borrar(Integer.valueOf(req.path().pathParameters().get("id")));
        res.send("id");
    }



}
