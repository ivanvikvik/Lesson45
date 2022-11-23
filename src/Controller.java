import java.sql.*;
import java.util.Scanner;

public class Controller {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    //    public static final String URL = "jdbc:mysql://127.0.0.1:3306/computerdb";
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/computerdb?serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "123qwe";

    public static final String SIMPLE_SELECT = "SELECT cl.name class_name, " +
            "id_computer, c.name pc_name FROM class cl join computer c on cl.id_class = c.id_class";

    public static final String COMPUTER_SELECT = "SELECT * FROM computer";

    public static final String INSERT_INTO_COMPUTER = "INSERT INTO computer(name) VALUES";

    public static final String PREPARED_INSERT_INTO_COMPUTER = "INSERT INTO computer(name) VALUES(?)";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input name of PC: ");
        String param = scanner.nextLine();

        String query = INSERT_INTO_COMPUTER + "('" + param + "')";

        //NAME1'); DROP TABLE temp_table; INSERT INTO computer(name) VALUES('NAME2


        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //System.out.println(connection.getClass().getName());

            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_INSERT_INTO_COMPUTER);

            preparedStatement.setString(1, param);

            int n = preparedStatement.executeUpdate();

            //System.out.println(statement.getClass().getName());

            //int n = statement.executeUpdate(query);
            System.out.printf("\nIt was %d records updated.\n", n);

            ResultSet resultSet = statement.executeQuery(COMPUTER_SELECT);

            //System.out.println(resultSet.getClass().getName());

            int index = 1;
            while(resultSet.next()) {
                System.out.print(index + ") " + resultSet.getInt("id_computer") + " ");
                System.out.print(resultSet.getString("name")+ " ");
                System.out.println(resultSet.getInt("id_class"));
                //System.out.println(resultSet.wasNull());
                index++;
            }

        } catch (ClassNotFoundException exception) {
            System.out.println(exception);
        } catch (SQLException exception) {
            System.out.println(exception);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException exception) {
                    System.out.println(exception);
                }
            }
        }

    }
}
