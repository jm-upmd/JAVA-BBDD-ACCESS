package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EjemploBBDD {
	public static void main(String[] args) {
		String databaseURL = "jdbc:ucanaccess://H://Cursos Actuales//Curso Officce (A Fondo)//Access//BBDDs//VERDULEROS.accdb";
		try (Connection connection = DriverManager.getConnection(databaseURL)) {

			// Inserción de un registro en en la tabla Productos.	
			// Ojo si hay indices en las columnas. Ver:
			// https://stackoverflow.com/questions/31948096/how-to-change-sortorder-to-avoid-unsupported-collating-sort-order-error/31972659#31972659
			
			String sql = "INSERT INTO Productos (IdProducto,NomProducto, IdGrupo, Precio) VALUES (?,?, ?, ?)";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1,102);
			preparedStatement.setString(2, "Frambuesa");
			preparedStatement.setString(3, "1");
			preparedStatement.setString(4, "2.75");

			int row = preparedStatement.executeUpdate();

			if (row > 0) {
				System.out.println("Registro insertado.");
			}
			
			// Otra forma (menos segura)
			sql = "INSERT INTO Productos (IdProducto,NomProducto, IdGrupo, Precio) VALUES (103,'Sandía', 1, 1.23)";
			Statement statement = connection.createStatement();
			row = statement.executeUpdate(sql);
			

			// Inserción en una tabla con campo id autogenerado. Tabla Grupos.
			// No se inserta el autonumerado y se inserta automáticamente.
			
			sql = "INSERT INTO Grupos (NombreGrupo) VALUES (?)";
			
			preparedStatement = connection.prepareStatement(sql);	
			preparedStatement.setString(1, "Bebidas");
			
			row = preparedStatement.executeUpdate();
			if(row > 0)
				System.out.println("Registro insertado.");

		
			
			
			
			// Extraemos los datos de la tabla Productos

			sql = "SELECT * FROM Productos";

			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

//            while (result.next()) {
//                int id = result.getInt("idProducto");
//                String nomProducto = result.getString("NomProducto");
//                String precio = result.getString("Precio");
//                Double precio2 = result.getDouble("Precio");
//                                
//                System.out.println(id + " | " + nomProducto + " | " + precio + " | " + precio2);.
//            }

			int j = result.getMetaData().getColumnCount();
			System.out.print("| ");
			for (int i = 1; i <= j; i++) {
				System.out.print(result.getMetaData().getColumnName(i) + " | ");
			}
			System.out.println();

			while (result.next()) {
				for (int i = 1; i <= j; i++) {
					Object o = result.getObject(i);
					System.out.print(o + " | ");

				}
				System.out.println();

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
