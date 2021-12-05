package pr.gov.edutech.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection recuperarConexao() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://MYSQL5039.site4now.net:3306/db_a7d52e_kobinsk", "a7d52e_kobinsk", "Victorkob12");
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
