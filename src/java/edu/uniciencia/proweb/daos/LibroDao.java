package edu.uniciencia.proweb.daos;

import edu.uniciencia.proweb.persistence.Libro;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@ViewScoped

public class LibroDao implements Serializable {

    private List<Libro> listaMem;
    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private String editorial;

    public List<Libro> getListaMem() {
        return listaMem;
    }

    public void setListaMem(List<Libro> listaMem) {
        this.listaMem = listaMem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProgWebPU");

    @PostConstruct
    public void init() {
        listaMem = getListaLibro();
    }

    public List<Libro> getListaLibro() {
        EntityManager em = emf.createEntityManager();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o from Libro o");
        List<Libro> lista = em.createQuery(sql.toString()).getResultList();
        if (lista != null && lista.size() > 0) {
            return lista;
        } else {
            return null;
        }
    }

    public void eliminar() {
        eliminarLibro(id);
    }

    public void eliminarLibro(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Libro u = em.find(Libro.class, id);
            em.remove(u);
            em.getTransaction().commit();
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Libro Eliminado"));
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void guardarLibro() {
        EntityManager em = emf.createEntityManager();
        Libro l = new Libro();
        l.setIdLibro(id);
        l.setAutor(autor);
        l.setTitulo(titulo);
        l.setCategoria(categoria);
        l.setEditorial(editorial);

        em.merge(l);
        try {
            em.getTransaction().begin();
            em.persist(l);
            em.getTransaction().commit();
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Libro Agregado Exitosamente"));
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void actualizarLibro(Libro lib) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Libro l = em.find(Libro.class, lib.getIdLibro());
            l.setIdLibro(lib.getIdLibro());
            l.setAutor(lib.getAutor());
            l.setTitulo(lib.getTitulo());
            l.setCategoria(lib.getCategoria());
            l.setEditorial(lib.getEditorial());

            em.merge(l);
            em.getTransaction().commit();
            System.out.println("ok");
        } catch (Exception e) {
            System.out.println("error");
            em.getTransaction().rollback();
        }
    }

    public void actualizarAceptar(RowEditEvent event) {
        Libro l = (Libro) event.getObject();
        actualizarLibro(l);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Libro Actualizado"));
    }

    public void actualizarCancelar(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Cancelado"));
    }

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
