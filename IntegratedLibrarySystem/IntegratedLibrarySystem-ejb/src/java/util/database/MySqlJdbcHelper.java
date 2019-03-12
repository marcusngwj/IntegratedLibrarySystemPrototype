package util.database;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class MySqlJdbcHelper
{
    public Connection createConnection() throws NamingException, SQLException
    {
        Context context = new InitialContext();
        DataSource dataSource = (DataSource)context.lookup("jdbc/ILSDatasource");
        
        return dataSource.getConnection();
    }
}
