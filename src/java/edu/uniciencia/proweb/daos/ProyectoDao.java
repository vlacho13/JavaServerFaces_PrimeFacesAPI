
package edu.uniciencia.proweb.daos;

import edu.uniciencia.proweb.persistence.Proyecto;
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

public class ProyectoDao implements Serializable {
    
    private List<Proyecto> listaMem;
    private int id;
    private String nombre;
    private String autor;
    private int fecha;

    public List<Proyecto> getListaMem() {
        return listaMem;
    }

    public void setListaMem(List<Proyecto> listaMem) {
        this.listaMem = listaMem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProgWebPU");    
    
    @PostConstruct
    public void init() {
        listaMem = getListaProyecto();
    }
    
    public List<Proyecto> getListaProyecto() {
        EntityManager em = emf.createEntityManager();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o from Proyecto o");
        List<Proyecto> lista = em.createQuery(sql.toString()).getResultList();
        if (lista != null && lista.size() > 0) {           
            return lista;
        } else {            
            return null;
        }
    }      
    
    public void eliminar() {
        eliminarProyecto(id);
    }
    
    public void eliminarProyecto(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Proyecto u = em.find(Proyecto.class, id);
            em.remove(u);
            em.getTransaction().commit();
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Proyecto Eliminado"));
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void guardarProyecto() {
        EntityManager em = emf.createEntityManager();
        Proyecto p = new Proyecto();
        p.setIdProyecto(id);
        p.setNombre(nombre);
        p.setAutor(autor);
        p.setFecha(fecha);               
        
        em.merge(p);
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Proyecto Agregado Exitosamente"));
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void actualizarProyecto(Proyecto pro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Proyecto p = em.find(Proyecto.class, pro.getIdProyecto());
            p.setIdProyecto(pro.getIdProyecto());
            p.setNombre(pro.getNombre());
            p.setAutor(pro.getAutor());
            p.setFecha(pro.getFecha());
            
            em.merge(p);
            em.getTransaction().commit();
            System.out.println("ok");
        } catch (Exception e) {
            System.out.println("error");
            em.getTransaction().rollback();
        }
    }

    public void actualizarAceptar(RowEditEvent event) {
        Proyecto l = (Proyecto) event.getObject();             
        actualizarProyecto(l);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Proyecto Actualizado"));
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
