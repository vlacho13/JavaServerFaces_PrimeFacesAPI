
package edu.uniciencia.proweb.daos;

import edu.uniciencia.proweb.persistence.Revista;
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

public class RevistaDao {
    
    private List<Revista> listaMem;
    private int id;
    private String nombre;
    private String categoria;
    private String universidad;

    public List<Revista> getListaMem() {
        return listaMem;
    }

    public void setListaMem(List<Revista> listaMem) {
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProgWebPU");    
    
    @PostConstruct
    public void init() {
        listaMem = getListaRevista();
    }
    
    public List<Revista> getListaRevista() {
        EntityManager em = emf.createEntityManager();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o from Revista o");
        List<Revista> lista = em.createQuery(sql.toString()).getResultList();
        if (lista != null && lista.size() > 0) {           
            return lista;
        } else {            
            return null;
        }
    }      
    
    public void eliminar() {
        eliminarRevista(id);
    }
    
    public void eliminarRevista(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Revista u = em.find(Revista.class, id);
            em.remove(u);
            em.getTransaction().commit();
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Revista Eliminada"));
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void guardarRevista() {
        EntityManager em = emf.createEntityManager();
        Revista r = new Revista();
        r.setIdRevista(id);
        r.setNombre(nombre);
        r.setCategoria(categoria);
        r.setUniversidad(universidad);               
        
        em.merge(r);
        try {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Revista Agregada Exitosamente"));
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void actualizarRevista(Revista rev) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Revista r = em.find(Revista.class, rev.getIdRevista());
            r.setIdRevista(rev.getIdRevista());
            r.setNombre(rev.getNombre());
            r.setCategoria(rev.getCategoria());
            r.setUniversidad(rev.getUniversidad());
            
            em.merge(r);
            em.getTransaction().commit();
            System.out.println("ok");
        } catch (Exception e) {
            System.out.println("error");
            em.getTransaction().rollback();
        }
    }

    public void actualizarAceptar(RowEditEvent event) {
        Revista l = (Revista) event.getObject();             
        actualizarRevista(l);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Revista Actualizada"));
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
