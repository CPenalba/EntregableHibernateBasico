/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListaCompra.pojo;

import ListaCompra.model.Productos;
import ListaCompra.utils.HibernateUtil;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author carol
 */
public class ListaCompraPojo implements ListaCompra.dao.ListaCompraDAO {

    @Override
    public void añadirProductos(Productos producto) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(producto);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al añadir: " + e);
        }
    }

    @Override
    public List<Productos> obtenerProductoPorNombre(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Productos> query = session.createQuery("FROM Productos WHERE suministro LIKE :value", Productos.class);
            query.setParameter("value", "%" + name + "%");
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Error al hacer la consulta: " + e);
            return null;
        }
    }

    @Override
    public void actualizarProducto(Productos producto) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            if (producto.getId() != 0) {
                session.merge(producto);
            }
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al actualizar: " + e);
        }
    }

    @Override
    public List<Productos> obtenerTodosLosProductos() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Productos> query = session.createQuery("FROM Productos", Productos.class);
            return query.list();
        } catch (Exception e) {
            System.out.println("Error al hacer la consulta: " + e);
            return null;
        }
    }

    @Override
    public void usar() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre del producto que quiere usar: ");
        String nombre = sc.nextLine().toLowerCase();

        if (nombre.matches(".*\\d.*")) {
            System.out.println("No se aceptan numeros en el nombre del producto");
            return;
        }

        List<Productos> productosEncontrados = obtenerProductoPorNombre(nombre);

        if (productosEncontrados.isEmpty()) {
            System.out.println("No se encontraron productos con ese nombre");
        } else {
            System.out.print("Introduzca la cantidad que quiere usar: ");

            while (!sc.hasNextInt()) {
                System.out.println("Error no es un numero. ");
                System.out.print("Introduzca la cantidad que quiere usar: ");
                sc.next();
            }
            int cantidad = sc.nextInt();

            while (cantidad < 1) {
                System.out.println("Error, la cantidad debe ser un entero mayor o igual a 1.");
                System.out.print("Introduzca la cantidad que quiere usar (debe ser un entero mayor o igual a 1): ");
                while (!sc.hasNextInt()) {
                    System.out.println("Error, no es un número válido.");
                    System.out.print("Introduzca la cantidad que quiere usar (debe ser un entero mayor o igual a 1): ");
                    sc.next();  
                }
                cantidad = sc.nextInt();
            }

            for (Productos p : productosEncontrados) {
                if (cantidad > p.getCantidad()) {
                    System.out.println("Error estas poniendo mas cantidad de la que hay");
                } else if (cantidad == p.getCantidad()) {
                    borrarProducto(p);
                    System.out.println("Borrado correctamente");
                } else {
                    p.setCantidad(p.getCantidad() - cantidad);
                    actualizarProducto(p);
                    System.out.println("Actualizado correctamente");
                }
            }
        }
    }

    @Override
    public void adquirir() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre del producto que quiere adquirir: ");
        String nombre = sc.nextLine().toLowerCase();

        if (nombre.matches(".*\\d.*")) {
            System.out.println("No se aceptan numeros en el nombre del producto");
            return;
        }

        System.out.print("Introduzca la cantidad que quiere usar: ");

        while (!sc.hasNextInt()) {
            System.out.println("Error no es un numero. ");
            System.out.print("Introduzca la cantidad que quiere adquirir: ");
            sc.next();
        }
        int cantidad = sc.nextInt();
        
        while (cantidad < 1) {
            System.out.println("Error, la cantidad debe ser un entero mayor o igual a 1.");
            System.out.print("Introduzca la cantidad que quiere usar (debe ser un entero mayor o igual a 1): ");
            while (!sc.hasNextInt()) {
                System.out.println("Error, no es un número válido.");
                System.out.print("Introduzca la cantidad que quiere usar (debe ser un entero mayor o igual a 1): ");
                sc.next();  
            }
            cantidad = sc.nextInt();
        }

        List<Productos> productosEncontrados = obtenerProductoPorNombre(nombre);

        if (productosEncontrados.isEmpty()) {
            System.out.println("Como no existe lo añade");
            añadirProductos(new Productos(cantidad, nombre));
            System.out.println("Añadidio correctamente");
        } else {
            for (Productos productoExistente : productosEncontrados) {
                productoExistente.setCantidad(productoExistente.getCantidad() + cantidad);
                actualizarProducto(productoExistente);
                System.out.println("Actualizado correctamente");
            }
        }
    }

    @Override
    public void borrarProducto(Productos producto) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            if (producto.getId() != 0) {
                session.remove(producto);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al borrar: " + e);
        }
    }
}
