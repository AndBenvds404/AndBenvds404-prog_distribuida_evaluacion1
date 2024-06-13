package com.distribuida.servicios;

import com.distribuida.db.Book;

import java.util.List;

public interface ServicioBook {

    public Book insertar(Book book);
    public Book buscar (int id);
    public Book actualizar (Book book);
    public void borrar(int id);
    public List<Book> listarTodos();




}
