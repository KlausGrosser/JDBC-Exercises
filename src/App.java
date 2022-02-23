import java.sql.Connection;
import java.util.List;

public class App {

    public static void main(String[] args) {
        //Exercise 1 Solution:
        Connection connection = ExercisesSolutions.connect();
        System.out.println(ExercisesSolutions.listQuery("SELECT datname FROM pg_database", "Listing all databases..."));

        //Exercise 2 Solution:
        System.out.println(ExercisesSolutions.listQuery("SELECT schema_name FROM information_schema.schemata", "Listing all schemas..."));

        //Exercise 3 Solution:
        System.out.println(ExercisesSolutions.listTables());

        //Exercise 4 Solution:
        ExercisesSolutions.executeQuery("DROP TABLE IF EXISTS employees CASCADE");
        System.out.println(ExercisesSolutions.listTables());
        ExercisesSolutions.executeQuery("CREATE TABLE employees (fname varchar(30) NOT NULL, lname VARCHAR(30) NOT NULL, email VARCHAR(50) UNIQUE NOT NULL, contact NUMERIC(15,0))");
        ExercisesSolutions.displayTableFields("SELECT * FROM employees");
        System.out.println(ExercisesSolutions.listTables());
        ExercisesSolutions.insertData(ExercisesSolutions.createEmployeeList());
        List<Employee> employeesLoaded = ExercisesSolutions.readEmployees();
        System.out.println(employeesLoaded);
        ExercisesSolutions.displayTableFields("SELECT * FROM employees");
        ExercisesSolutions.close(connection);
    }
}
