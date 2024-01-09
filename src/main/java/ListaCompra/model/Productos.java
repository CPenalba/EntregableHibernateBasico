/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListaCompra.model;

import jakarta.persistence.*;

/**
 *
 * @author carol
 */
@Entity
@Table(name = "productos")
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "suministro")
    private String suministro;

    public Productos() {
    }

    public Productos(int cantidad, String suministro) {
        this.cantidad = cantidad;
        this.suministro = suministro;
    }

    public int getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getSuministro() {
        return suministro;
    }

    public void setSuministro(String suministro) {
        this.suministro = suministro;
    }

    @Override
    public String toString() {
        return "ListaCompra{" + "cantidad = " + cantidad + ", suministro = " + suministro + '}';
    }
}
