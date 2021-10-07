/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.sql.Date;

import org.apache.ibatis.annotations.Param;

/**
 *
 * @author 2106913
 */
public class ItemRentado implements Serializable {

    private int id;

    private Item item;
    private Cliente client;
    private Date fechainiciorenta;
    private Date fechafinrenta;

    public ItemRentado(int id, Item item, Cliente cliente, Date fechainiciorenta, Date fechafinrenta) {
        this.id = id;
        this.item = item;
        this.client = cliente;
        this.fechainiciorenta = fechainiciorenta;
        this.fechafinrenta = fechafinrenta;
    }

    public ItemRentado() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Cliente gCliente(){
        return client;
    }

    public void setCliente(Cliente cliente){
        this.client = cliente;
    }

    public Date getFechainiciorenta() {
        return fechainiciorenta;
    }

    public void setFechainiciorenta(Date fechainiciorenta) {
        this.fechainiciorenta = fechainiciorenta;
    }

    public Date getFechafinrenta() {
        return fechafinrenta;
    }

    public void setFechafinrenta(Date fechafinrenta) {
        this.fechafinrenta = fechafinrenta;
    }

    @Override
    public String toString() {
        return "ItemRentado{" + "id=" + id + ", item=" + item + "client="+ client + ", fechainiciorenta=" + fechainiciorenta + ", fechafinrenta=" + fechafinrenta + '}';
    }

    
    
}
