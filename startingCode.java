//This is the code that I started with, everything was still mixed up for we had not yet started to learn the MVC technique!
package projects.bankProject;

import java.sql.*;
import java.util.Scanner;

public class startingCode {
    public static void main(String[] args) {

        boolean checker = true;
        Scanner input = new Scanner(System.in);
        String accountName = "";
        String accountNumber = "";
        String accountType = "";
        double amount = 0.0;

        //Objects creation
        Validate val=new Validate();

        String jdbcUrl = "jdbc:mysql://localhost:3306/JeanLucBanking";
        String dbUsername = "root";
        String dbPassword = "{PASSWORD}";// Here goes the password for your mysql
        Scanner sc = new Scanner(System.in);

        while (checker) {
            System.out.println("CORE BANKING SYSTEM - Jean Luc");
            System.out.println("--------------------------------------");
            System.out.println("1. Register");
            System.out.println("2. Update");
            System.out.println("3. Search account by account name");
            System.out.println("4. Show all accounts");
            System.out.println("5. Delete");
            System.out.println("0. Exit");
            System.out.println("--------------------------------------");
            System.out.print("Please enter a choice: ");

            Integer choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter your personal info!");
                    System.out.print("Account name: ");
                    accountName = input.nextLine();
                    System.out.print("Account Number: ");
                    accountNumber = input.nextLine();
                    System.out.print("Account type: ");
                    accountType = input.nextLine();
                    System.out.print("Amount to deposit: ");
                    amount = input.nextDouble();

                    try {
                        Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                        Statement statement = conn.createStatement();
                        String sql = String.format(
                                "INSERT INTO bank_account (account_name, account_number, account_type, account_amount) VALUES ('%s', '%s', '%s', %f);",
                                accountName, accountNumber, accountType, amount
                        );

                        int affectedRow = statement.executeUpdate(sql);
                        if (affectedRow > 0) {
                            System.out.println("Data saved successfully!");
                        } else {
                            System.out.println("Data not saved successfully!");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;

                case 2:
                    System.out.println("Option 2 selected");
                    System.out.print("Enter the account number of the account, whose account name you want to change: ");
                    String accNum= sc.nextLine();

                            try (Connection con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
                                String sql = "SELECT * FROM bank_account WHERE account_number = ?";
                                try (PreparedStatement ps = con.prepareStatement(sql)) {
                                    ps.setString(1, accNum);
                                    try (ResultSet rs = ps.executeQuery()) {
                                        if (rs.next()) {
                                            System.out.println("ACCOUNT YOU WANT TO CHANGE");
                                            System.out.println("-------------------------------");
                                            System.out.println("Account name: " + rs.getString("account_name"));
                                            System.out.println("Account type: " + rs.getString("account_type"));
                                            System.out.println("Account number: " + rs.getString("account_number"));
                                            System.out.println("Account amount: " + rs.getString("account_amount"));
                                            System.out.println("-------------------------------");

                                            System.out.print("Do you really want to update this  whole account? (YES/NO): ");
                                            String decision = sc.nextLine();
                                            val.validateUserInputYesOrNo(decision);
                                            if (decision.equalsIgnoreCase("YES")) {

                                                val.changeTheWholeAccount(accNum);

                                                //DISPLAYING THE UPDATED ACCOUNT

                                            } else if(decision.equalsIgnoreCase("NO")) {
                                                val.elseIf(accNum);
                                                //DISPLAYING THE UPDATED ACCOUNT
                                                System.out.println("THIS IS THE UPDATED ACCOUNT!");
                                                System.out.println("--------------------------------------------------------------");
                                                String displayUpdatedAccount="SELECT * FROM bank_account WHERE account_number=?";
                                                PreparedStatement pst=con.prepareStatement(displayUpdatedAccount);
                                                pst.setString(1,accNum);
                                                ResultSet rst=pst.executeQuery();
                                                if(rst.next()){
                                                    DBMSnames dbNames=new DBMSnames();
                                                    System.out.println("Account name: "+rst.getString(dbNames.account_name));
                                                    System.out.println("Account number: "+rst.getString(dbNames.account_number));
                                                    System.out.println("Account type: "+rst.getString(dbNames.account_type));
                                                    System.out.println("Account amount: "+rst.getDouble(dbNames.account_amount));
                                                    System.out.println("-----------------------------------------------------------");
                                                }
                                            }
                                        } else {
                                            System.out.println("❌ No account found with that number.");
                                        }
                                    }



                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    break;

                case 3:
                    System.out.print("Enter the account name that you want to search: ");
                    String name = sc.nextLine();

                    try {
                        Connection connect = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                        Statement st = connect.createStatement();
                        String sql = String.format("SELECT * FROM bank_account WHERE account_name LIKE '%%%s%%';", name);

                        ResultSet resultSet = st.executeQuery(sql);
                        if (!resultSet.next()) {
                            System.out.println("Oops, no rows found! in Jean Luc's banking table containing the names: " + name);
                        } else {
                            System.out.println("These are the records where the account name is => " + name);
                            do {
                                String accName = resultSet.getString("account_name");
                                String accNumber = resultSet.getString("account_number");
                                String accType = resultSet.getString("account_type");
                                double acNumber = resultSet.getDouble("account_amount");
                                System.out.println("USER'S PERSONAL INFO ");
                                System.out.println("Names: " + accName);
                                System.out.println("Account Number: " + accNumber);
                                System.out.println("Account type: " + accType);
                                System.out.println("Account amount: " + acNumber);
                            } while (resultSet.next());
                        }
                    } catch (Exception select) {
                        select.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.println("SHOWING ALL ACCOUNTS!");
                    System.out.println("=========================================");
                    try {
                        Connection con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                        String sql = "SELECT * FROM bank_account";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        int counter=0;
                        while (rs.next()) {
                            counter++;
                            System.out.println("-----------------------------------------------------------");
                            System.out.println("-------------Account number "+counter+"---------------------");
                            System.out.println("Account name: " + rs.getString("account_name"));
                            System.out.println("Account number: " + rs.getString("account_number"));
                            System.out.println("Account type: " + rs.getString("account_type"));
                            System.out.println("Account amount: " + rs.getString("account_amount"));
                        }
                        System.out.println("------------------------------------------------------------");
                        System.out.println();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    System.out.print("Which account do you want to delete? (By account number): ");
                    String accnumber = sc.nextLine();
                    String select="SELECT * FROM bank_account WHERE account_number=?";// Query for selecting all account columns data of all accounts with the entered account number
                    String sql = "DELETE FROM bank_account WHERE account_number=?";// Query  for deletion

                    try {
                        Connection con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);


                        //==========================================================================
                        //----------------------Asking the user to confirm deletion-----------------------------\
                        DBMSnames names=new DBMSnames();
                        PreparedStatement show= con.prepareStatement(select);
                        show.setString(1,accnumber);
                        ResultSet rs=show.executeQuery();
                        System.out.println("CONFIRM DELETION!");
                        System.out.println("Account(s) found with the account number: "+accnumber);
                        int counter=0;

                        while(rs.next()){
                            counter++;
                            System.out.println("-------------Acount number "+counter+"---------------------");
                            System.out.println("----------------------------------------------------------");
                            System.out.println("Account names: "+rs.getString(names.account_name));
                            System.out.println("Account number: "+rs.getString(names.account_number));
                            System.out.println("Account type: "+rs.getString(names.account_type));
                            System.out.println("Account amount: "+rs.getDouble(names.account_amount));
                            System.out.println("----------------------------------------------------------");
                        }
                        System.out.println("===========================================================");
                        System.out.println();
                        System.out.print("Do you want to confirm deletion the above account(s) (YES/NO): " );
                        String answer=sc.nextLine();
                        val.validateUserInputYesOrNo(answer);
                        if(answer.equalsIgnoreCase("YES")){
                            //----------------------Proceeding with deletion-----------------------------
                            PreparedStatement ps = con.prepareStatement(sql);

                            ps.setString(1, accnumber);
                            int row = ps.executeUpdate();
                            if (row >= 0) {
                                System.out.println("✅ One account successfully deleted!");
                            } else {
                                System.out.println("❌ No deleted account!");
                            }
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 0:
                    System.out.println("Thank you for visiting Jean Luc's banking System");
                    System.exit(0);
            }
        }
    }
}
