package tn.chouflifilm.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MyDataBase {
    public final String URL="jdbc:mysql://localhost:3306/6core_cinema";
    public final String USER="root";
    public  final   String PWD="";
    private Connection connection=null;
    static int compteur=0;
    static  MyDataBase mydatabase ;

                    public Connection getConnection() {
                        return connection;
                    }

                    public static  MyDataBase getDataBase(){
                            if (mydatabase == null)
                            {
                                 mydatabase = new MyDataBase();
                            }
                                 return mydatabase;
                            }



                    private MyDataBase() {

                        try {

                            connection = DriverManager.getConnection(URL, USER, PWD);

                            System.out.println("Connection established successfully!");

                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.out.println("Failed to establish connection!");

                        }
                    }

}

