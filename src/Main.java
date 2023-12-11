import java.sql.*;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static Connection createDbConnection() throws ClassNotFoundException, SQLException {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "root";

        // JDBC variables for opening, closing, and managing connection
        Connection connection = null;


        // Step 1: Register JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Step 2: Open a connection
        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(url, user, password);

        return connection;
    }

    static Connection db;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        db = createDbConnection();

        while (true) {
            System.out.println("\n=======   MENU UTAMA  ===========");
            System.out.println("1. Input Data");
            System.out.println("2. Tampil Data");
            System.out.println("3. Update Data");
            System.out.println("0. Keluar");
            System.out.print("PILIHAN> ");

            Scanner scanner = new Scanner(System.in);
            int pilihanFitur = scanner.nextInt();
            if (pilihanFitur == 1) {
                menuTambah();
                menuTampil();
            } else if (pilihanFitur == 2) {
                menuTampil();
            } else if (pilihanFitur == 3) {
                menuUbah();
                menuTampil();
            } else {
                if (db != null) {
                    db.close();
                    System.out.println("Connection closed.");
                }

                System.out.print("Anda Keluar dari sistem...");

                return;
            }
        }
    }

    private static int menuTambah() throws SQLException {
        Scanner newscanner = new Scanner(System.in);
        System.out.print("Masukkan nama : ");
        String nama = newscanner.nextLine().trim();

        System.out.print("Masukkan alamat : ");
        String alamat = newscanner.nextLine();

        //  Prepare a SQL statement
        String sql = "INSERT INTO mahasiswa (nama, alamat) VALUES (?, ?)";
        PreparedStatement preparedStatement = db.prepareStatement(sql);
        // Set values for the parameters
        preparedStatement.setString(1, nama);
        preparedStatement.setString(2, alamat);

        // Execute the statement
        return preparedStatement.executeUpdate();
    }

    private static void menuTampil() throws SQLException {
        // Step 2: Create a statement
        Statement statement = db.createStatement();

        // Step 3: Execute a SELECT * query
        String sql = "SELECT * FROM mahasiswa";
        ResultSet resultSet = statement.executeQuery(sql);

        // Step 4: Process the result set
        while (resultSet.next()) {
            // Retrieve data from the result set
            int id = resultSet.getInt("id");
            String nama = resultSet.getString("nama");
            String alamat = resultSet.getString("alamat");

            // Process the retrieved data (you can print or use it as needed)
            System.out.printf("ID: %d, Nama: %s, Alamat: %s\n", id, nama, alamat);
        }
    }

    private static int menuUbah() throws SQLException {
        Scanner idScanner = new Scanner(System.in);
        System.out.print("Masukkan id produk : ");
        int id = idScanner.nextInt();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama : ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan alamat : ");
        String alamat = scanner.nextLine();

        //  Prepare a SQL statement
        String sql = "UPDATE mahasiswa SET nama = ?,  alamat = ? WHERE id = ?";
        PreparedStatement preparedStatement = db.prepareStatement(sql);
        // Set values for the parameters
        preparedStatement.setString(1, nama);
        preparedStatement.setString(2, alamat);
        preparedStatement.setInt(3, id);

        // Execute the statement
        return preparedStatement.executeUpdate();
    }
}