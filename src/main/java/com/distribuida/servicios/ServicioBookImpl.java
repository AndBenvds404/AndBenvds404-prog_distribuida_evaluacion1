package com.distribuida.servicios;

import com.distribuida.db.Book;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;


import java.util.List;

@ApplicationScoped
public class ServicioBookImpl implements  ServicioBook {

    @Inject
    private EntityManager entityManager;


    @Transactional
    public Book insertar(Book book) {

        EntityTransaction transaction = this.entityManager.getTransaction();

        try {

            transaction.begin();
            this.entityManager.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {

            transaction.rollback();
            return null;
        }
    }

    @Transactional
    public Book buscar(int id) {

        return this.entityManager.find(Book.class, id);
    }

    public List<Book> listarTodos() {
        return this.entityManager.createQuery("SELECT b FROM Book b ORDER BY b.id ASC", Book.class)
                .getResultList();
    }


    @Transactional
    public Book actualizar(Book book) {

        return this.entityManager.merge(book);
    }

    @Transactional
    public void borrar(int id) {

        Book book = buscar(id);
        if (book != null) {
            this.entityManager.remove(book);
        }
    }


}