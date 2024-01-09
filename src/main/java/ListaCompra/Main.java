package ListaCompra;

import ListaCompra.model.Productos;
import ListaCompra.pojo.ListaCompraPojo;
import ListaCompra.utils.LeerCSV;
import java.util.List;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author carol
 */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Productos> producto = LeerCSV.cargarDatos("compra.csv");
        ListaCompraPojo lcp = new ListaCompraPojo();

        if (lcp.obtenerTodosLosProductos().isEmpty()) {
            for (Productos productos : producto) {
                List<Productos> productosExistentes = lcp.obtenerProductoPorNombre(productos.getSuministro());
                if (productosExistentes.isEmpty()) {
                    lcp.añadirProductos(productos);
                } else {
                    Productos pe = productosExistentes.get(0); // para obtener el primer elemento
                    pe.setCantidad(pe.getCantidad() + productos.getCantidad());
                    lcp.actualizarProducto(pe);
                }
            }
        }

        do {
            System.out.println("------MENU------");
            System.out.println("1 -> listar");
            System.out.println("2 -> usar x suministro");
            System.out.println("3 -> hay suministro");
            System.out.println("4 -> adquirir x suministro");
            System.out.println("5 -> salir");

            try {
                System.out.print("Introduzca la opcion: ");
                int opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.println("Lista de los suministros que nos quedan: ");
                        for (Productos p : lcp.obtenerTodosLosProductos()) {
                            System.out.println(p);
                        }
                        break;

                    case 2:
                        lcp.usar();
                        break;

                    case 3:
                        System.out.print("Introduca el nombre del producto que quiere ver: ");
                        String nombreProducto = sc.nextLine().toLowerCase();
                        if (nombreProducto.matches(".*\\d.*")) {
                            System.out.println("No se aceptan numeros en el nombre del producto");
                            return;
                        }
                        List<Productos> productosEncontrados = lcp.obtenerProductoPorNombre(nombreProducto);
                        if (productosEncontrados != null && !productosEncontrados.isEmpty()) {
                            for (Productos p : productosEncontrados) {
                                System.out.println("Hay " + p.getCantidad() + " unidades del producto " + p.getSuministro());
                            }
                        } else {
                            System.out.println("No se encontraron productos con ese nombre");
                        }
                        break;

                    case 4:
                        lcp.adquirir();
                        break;

                    case 5:
                        System.out.println("Saliendo del programa...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error, no es un numero.");
            }
        } while (true);
    }
}

//listado con orm

//el champú
