
package edu.eci.cvds.sampleprj.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCExample {
    
    public static void main(String args[]){
        try {
            String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="prueba2019";
                        
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,user,pwd);
            con.setAutoCommit(false);
                 
            
            System.out.println("Valor total pedido 1: " + valorTotalPedido(con, 1));
            
            List<String> prodsPedido=nombresProductosPedido(con, 1);
            
            
            System.out.println("Productos del pedido 1:");
            System.out.println("-----------------------");
            for (String nomprod:prodsPedido){
                System.out.println(nomprod);
            }
            System.out.println("-----------------------");
            
            
            int suCodigoECI=2162965;
            registrarNuevoProducto(con, suCodigoECI, "José Gamboa", 1545154);
            con.commit();
                        
            
            con.close();
                                   
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Agregar un nuevo producto con los parámetros dados
     * @param con la conexión JDBC
     * @param codigo
     * @param nombre
     * @param precio
     * @throws SQLException 
     */
    public static void registrarNuevoProducto(Connection con, int codigo, String nombre,int precio) throws SQLException{

        String insertString ="INSERT INTO ORD_PRODUCTOS VALUES (?, ?, ?)";
        try(PreparedStatement insertProduct = con.prepareStatement(insertString);)
        {
            insertProduct.setInt(1, codigo);
            insertProduct.setString(2, nombre);
            insertProduct.setInt(3, precio);
            insertProduct.execute();
        }
        catch(SQLException e){
        	System.out.println("Se petateto " + e.getMessage());
        }

        //Crear preparedStatement
        //Asignar parámetros
        //usar 'execute'

        
        con.commit();
        
    }
    
    /**
     * Consultar los nombres de los productos asociados a un pedido
     * @param con la conexión JDBC
     * @param codigoPedido el código del pedido
     * @return 
     */
    public static List<String> nombresProductosPedido(Connection con, int codigoPedido){
        List<String> np=new LinkedList<>();
        String selectString ="SELECT DISTINCT nombre FROM ORD_PEDIDOS INNER JOIN ORD_DETALLE_PEDIDO INNER JOIN ORD_PRODUCTOS WHERE ORD_PEDIDOS.codigo = ?";
        try(PreparedStatement selectProduct = con.prepareStatement(selectString);)
        {
        	selectProduct.setInt(1, codigoPedido);
            ResultSet selectSet = selectProduct.executeQuery();
            while(selectSet.next()){
                np.add(selectSet.getString(1));
            }
        }
        catch(SQLException e){
        	
        }
        //Crear prepared statement
        //asignar parámetros
        //usar executeQuery
        //Sacar resultados del ResultSet
        //Llenar la lista y retornarla
        
        return np;
    }

    
    /**
     * Calcular el costo total de un pedido
     * @param con
     * @param codigoPedido código del pedido cuyo total se calculará
     * @return el costo total del pedido (suma de: cantidades*precios)
     */
    public static Long valorTotalPedido(Connection con, int codigoPedido){

        ArrayList<String> value = new ArrayList<>();

        String calculateInt ="SELECT SUM(ORD_DETALLE_PEDIDO.cantidad*ORD_PRODUCTOS.precio) AS Total FROM ORD_PEDIDOS INNER JOIN ORD_DETALLE_PEDIDO INNER JOIN ORD_PRODUCTOS WHERE ORD_PEDIDOS.codigo = ?";

        try(PreparedStatement calculateValue = con.prepareStatement(calculateInt);)
        {
        	calculateValue.setInt(1, codigoPedido);
            ResultSet calculateSet = calculateValue.executeQuery();
            while(calculateSet.next()){
                value.add(calculateSet.getString(1));
            }
        }
        catch(SQLException e) {
        	System.out.println("Se petateto " + e.getMessage());
            
        }
        
        //Crear prepared statement
        //asignar parámetros
        //usar executeQuery
        //Sacar resultado del ResultSet
        return Long.parseLong(value.get(0));
    }
}