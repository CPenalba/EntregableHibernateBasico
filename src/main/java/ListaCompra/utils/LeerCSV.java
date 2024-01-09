/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListaCompra.utils;

import ListaCompra.model.Productos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carol
 */
public class LeerCSV {

    public static List<Productos> cargarDatos(String ruta) {
        List<Productos> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(";");

                int cantidad = Integer.parseInt(partes[0].trim());
                String suministro = partes[1].trim().toLowerCase();
                Productos p = new Productos(cantidad, suministro);
                lista.add(p);

            }
        } catch (Exception e) {
            System.out.println("Error al leer el fichero" + e);
        }
        return lista;
    }
}
