/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.samples.entities;

import java.io.Serializable;

public class Cliente implements Serializable{
    
    private String nombre;
    private long documento;
    private String telefono;
    private String direccion;
    private String email;
    private boolean vetado; 

    public Cliente() {
    }

    public Cliente(String nombre, long documento, String telefono, String direccion, String email, boolean vetado) {   
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.vetado = vetado;
    }

  
    public Cliente(String nombre, long documento, String telefono, String direccion, String email) {
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.vetado = false;
    }

           
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getDocumento() {
        return documento;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVetado() {
        return vetado;
    }

    public void setVetado(boolean betado) {
        this.vetado = betado;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nombre=" + nombre + ", documento=" + documento + ", telefono=" + telefono + ", direccion=" + direccion + ", email=" + email + ", vetado=" + vetado + '}' + "\n\t";
    } 
    
}
