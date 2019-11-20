/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlliteapplication.pk;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 1
 *
 * @author kirusiha
 */
public class SQLiteConnection {

    public static Connection myConn = null;
    public static String sqliteServer = "jdbc:sqlite:";
    public static String resetPath = "";

    public static boolean isDatabaseExists(String dbFilePath) {
        File dbFile = new File(dbFilePath);
        return dbFile.exists();
    }

    public static Connection getConnection() {
        sqliteServer = "jdbc:sqlite:";
        String getFilePath = new File("").getAbsolutePath();
        String fileAbsolutePath = getFilePath.concat("\\src\\sqlliteapplication\\pk\\database\\dictionary.db");

        if (isDatabaseExists(fileAbsolutePath)) {
            try {
                myConn = DriverManager.getConnection(sqliteServer + fileAbsolutePath);
            } catch (SQLException ex) {
                Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            createNewDatabase("database", "dictionary");
            try {
                myConn = DriverManager.getConnection(sqliteServer + fileAbsolutePath);
            } catch (SQLException ex) {
                Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return myConn;
    }

    public static void createNewDatabase(String subFileFolder, String fileName) {

        String getFilePath = new File("").getAbsolutePath();
        String fileAbsolutePath = "";

        if (subFileFolder.isEmpty()) {

            fileAbsolutePath = getFilePath.concat("\\src\\sqlliteapplication\\pk\\" + fileName + ".db");
            resetPath = fileAbsolutePath;
        } else {
            fileAbsolutePath = getFilePath.concat("\\src\\sqlliteapplication\\pk\\" + subFileFolder + "\\" + fileName + ".db");
            resetPath = fileAbsolutePath;
        }

        Connection conn;
        try {
            conn = DriverManager.getConnection(sqliteServer + fileAbsolutePath);
            if (conn != null) {
                //  DatabaseMetaData meta=conn.getMetaData();
                String qry = "CREATE TABLE words(\n"
                        + "ID INT PRIMARY KEY,\n"
                        + "word TEXT NOT NULL,\n"
                        + "type TEXT NOT NULL,\n"
                        + "definition TEXT NOT NULL\n"
                        + ");";
                try {
                    Statement statement = conn.createStatement();
                    statement.execute(
                            qry
                    );
                    System.out.println("fgdg");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
