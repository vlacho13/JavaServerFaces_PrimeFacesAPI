/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniciencia.proweb.persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author striu
 */
@Entity
@Table(name = "revista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Revista.findAll", query = "SELECT r FROM Revista r")
    , @NamedQuery(name = "Revista.findByIdRevista", query = "SELECT r FROM Revista r WHERE r.idRevista = :idRevista")
    , @NamedQuery(name = "Revista.findByNombre", query = "SELECT r FROM Revista r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "Revista.findByCategoria", query = "SELECT r FROM Revista r WHERE r.categoria = :categoria")
    , @NamedQuery(name = "Revista.findByUniversidad", query = "SELECT r FROM Revista r WHERE r.universidad = :universidad")})
public class Revista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_revista")
    private Integer idRevista;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "universidad")
    private String universidad;

    public Revista() {
    }

    public Revista(Integer idRevista) {
        this.idRevista = idRevista;
    }

    public Integer getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(Integer idRevista) {
        this.idRevista = idRevista;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRevista != null ? idRevista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Revista)) {
            return false;
        }
        Revista other = (Revista) object;
        if ((this.idRevista == null && other.idRevista != null) || (this.idRevista != null && !this.idRevista.equals(other.idRevista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uniciencia.proweb.persistence.Revista[ idRevista=" + idRevista + " ]";
    }
    
}
