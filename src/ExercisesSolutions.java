import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExercisesSolutions {
    private static String driverName = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://localhost:5432/";
    private static String databaseName = "postgres";
    private static String userName = "admin";
    private static String password = "1234";

    private static Connection connection = null;

    public static Connection connect(){
        try{
            Class.forName(driverName);
            connection = DriverManager.getConnection(url+databaseName, userName, password);

            if(connection == null || connection.isClosed()){
                System.out.println("\nConnection Failed");
            }else{
                System.out.println("\nConnection Succeeded");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static boolean close(AutoCloseable closeable) {
        try {
            if (closeable != null) {
                System.out.println("Closing " + closeable.getClass().getSimpleName());
                closeable.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static List<String> listQuery(String query, String loadingMessage){
        List<String> list = new ArrayList<String>();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            System.out.println("\nExecuting query: \"" + query + "\"" );
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                String cellValue = result.getString(1);
                list.add(cellValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement);
        }
        System.out.println("\n" + loadingMessage);
        return list;
    }

    public static List<String> listTables() {
        List<String> list = new ArrayList<String>();
        try {
            DatabaseMetaData dbMetadata = connect().getMetaData();
            ResultSet rs = dbMetadata.getTables(null, null, null, null);

            while (rs.next()) {
                if ("table".equalsIgnoreCase(rs.getString(4))) {
                    list.add(rs.getString(3));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Listing all tables:");
        return list;
    }

    public static void executeQuery(String query){
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connect().createStatement();
            result = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }
    }

    public static void displayTableFields(String query) {
        Statement statement = null;
        try {
            statement = connect().createStatement();
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData rsm = rs.getMetaData();
            System.out.println("\nExecuting query: \"" + query + "\"");
            System.out.println("Showing table fields:");
            System.out.printf("%s - %s%n", "Column Name", "Datatype");
            for(int i=1; i<rsm.getColumnCount(); i++) {
                System.out.printf("%s - %s%n", rsm.getColumnLabel(i), rsm.getColumnTypeName(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }
    }

    public static void insertData(List<Employee> employees) {
        String insertQuery = "INSERT INTO Employees VALUES  (?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connect();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertQuery);

            for (Employee emp : employees) {
                preparedStatement.setString(1, emp.getFirstName());
                preparedStatement.setString(2, emp.getLastName());
                preparedStatement.setString(3, emp.getEmail());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
            }
        } finally {
            close(preparedStatement);
        }
    }

    public static List<Employee> readEmployees() {
        List<Employee> employees = new ArrayList<Employee>();

        String qrySelect = "SELECT fname, lname, email FROM Employees";

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connect();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qrySelect);

            System.out.println("Displaying table:");
            System.out.println("FirstName\t\tLastName\t\temail");
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setFirstName(rs.getString(1));
                emp.setLastName(rs.getString(2));
                emp.setEmail(rs.getString(3));
                employees.add(emp);
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                System.out.println(fname + "\t\t" + lname
                        + "\t\t" + email);
            }
        } catch (Exception e) {
        } finally {
            close(stmt);
        }
        return employees;
    }

    public static List<Employee> createEmployeeList(){
        List<Employee> employees = new ArrayList<Employee>();

        employees.add(new Employee("Adam", "Falon", "adam.falon@dci.com"));
        employees.add(new Employee("Mary", "Gold", "mary.gold@dci.com"));
        employees.add(new Employee("Adam", "Currie", "adam.currie@dci.com"));
        employees.add(new Employee("Bryan", "Jhonson", "bryan.Jhonson@dci.com"));
        employees.add(new Employee("Mary", "Jhonson", "mary.jhonson@dci.com"));

        return employees;
    }

}
