package app;

import java.sql.Connection;

public interface InterfaceDB {
    public void menu();
    public boolean initTables(Connection conn);
    public boolean deleteData(Connection conn);
}
