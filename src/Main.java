import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String username = "root";
    private static final String password = "admin";

    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM students";
            String query2 = "INSERT INTO students(name,age,marks) VALUES(?,?,?)";
            //Better to use PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query2);
            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.println("Enter Name: ");
                String name = scanner.next();
                System.out.println("Enter Age: ");
                int age = scanner.nextInt();
                System.out.println("Enter Marks: ");
                double marks = scanner.nextDouble();

                preparedStatement.setString(1,name);
                preparedStatement.setInt(2,age);
                preparedStatement.setDouble(3,marks);

                preparedStatement.addBatch();

                System.out.print("Enter More Data?(Y/N): ");
                String choice = scanner.next();

                if(choice.equalsIgnoreCase("N")){
                    break;
                }
            }

            preparedStatement.executeBatch();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double marks = resultSet.getDouble("marks");
                System.out.println("ID : "+id);
                System.out.println("Name : "+name);
                System.out.println("Age : "+age);
                System.out.println("Marks : "+marks);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}