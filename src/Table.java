import java.sql.Connection;

public abstract class Table{

    Connection connection;
    Database database;

    /**
     * Creates a table inside the Database db, via Connection con.
     * @param con   The connection to the database
     * @param db    The database object
     */

    Table(Connection con, Database db){
        connection=con;
        database=db;
    }

}