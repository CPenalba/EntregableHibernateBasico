/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListaCompra.dao;

import ListaCompra.model.Productos;
import java.util.List;

/**
 *
 * @author carol
 */
public interface ListaCompraDAO {

    void añadirProductos(Productos producto);

    List<Productos> obtenerProductoPorNombre(String name);

    void actualizarProducto(Productos producto);

    List<Productos> obtenerTodosLosProductos();

    void usar();
    
    void adquirir();

    void borrarProducto(Productos producto);
}
