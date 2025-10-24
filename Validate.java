// Validating user inputs
package projects.bankProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Validate {

    // VARIABLE DECLARATIONS
    Scanner sc=new Scanner(System.in);
    String changedName;
    DBMSnames db=new DBMSnames();
    String newType;




    public void validateUserInputYesOrNo(String answer){
        int counter=0;
        do {
            if (!answer.equalsIgnoreCase("YES") && !answer.equalsIgnoreCase("NO")) {
                counter++;
                if (counter >= 3) {
                    System.out.println("You have exceeded three trial times, You have to wait for 15 minutes!");
                    break;
                }
                System.out.print("Invalid choice, Enter either a Yes or No: ");
                answer = sc.nextLine();
            } else {
                break; // valid input, exit loop
            }
        } while (true);

    }

    public void elseIf(String accnumber){
        Scanner sc=new Scanner(System.in);




            try {
                String answer;
                Connection con=DriverManager.getConnection(db.jdbcUrl,db.dbUsername, db.dbPassword);


                // ASKING THE USER IF THEY WANT TO UPDATE THE WHOLE ACCOUNT NAME
                System.out.print("Do you want to change the account name(YES/NO): ");
                String decision=sc.nextLine();
                validateUserInputYesOrNo(decision);
                if(decision.equalsIgnoreCase("YES")) {
                    System.out.print("Enter the new account name: ");
                    changedName = sc.nextLine();
                    String sqlName="UPDATE bank_account SET account_name=? where account_number=?";
                    PreparedStatement ps=con.prepareStatement(sqlName);
                    ps.setString(1,changedName);
                    ps.setString(2,accnumber);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("✅ Account's name updated successfully!");
                    } else {
                        System.out.println("❌ Account not updated!");
                    }
                } else if (decision.equalsIgnoreCase("NO")) {
                    System.out.println("Account name kept the same!");
                }
                System.out.println("-----------------------------------------");


                // ASKING THE USER IF THEY WANT TO UPDATE THE WHOLE ACCOUNT TYPE
                System.out.print("Do want to update the account type?(YES/NO): ");
                answer=sc.nextLine();
                validateUserInputYesOrNo(answer);

                if(answer.equalsIgnoreCase("yes")){
                    System.out.print("Please enter the new account type you want: ");
                    newType=sc.nextLine();
                    String sqlType="UPDATE bank_account SET account_type=? WHERE account_number=?";
                    PreparedStatement ps=con.prepareStatement(sqlType);
                    ps.setString(1,newType);
                    ps.setString(2,accnumber);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("✅ Account's type updated successfully!");
                    } else {
                        System.out.println("❌ Account not updated!");
                    }
                }else if (decision.equalsIgnoreCase("NO")) {
                    System.out.println("Account type kept the same!");
                }
                System.out.println("-----------------------------------------");

                // ASKING THE USER IF THEY WANT TO UPDATE THE WHOLE ACCOUNT AMOUNT
                System.out.print("Do you want to update the bank_amount?(YES/NO):  ");
                answer=sc.nextLine();
                validateUserInputYesOrNo(answer);
                if(answer.equalsIgnoreCase("YES")){
                    System.out.print("Please enter the new amount you want to deposit?: ");
                    Double newAmount=sc.nextDouble();
                    String sqlAmount="UPDATE bank_account SET account_amount=? where account_number=?";
                    PreparedStatement ps=con.prepareStatement(sqlAmount);
                    ps.setDouble(1,newAmount);
                    ps.setString(2,accnumber);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("✅ Account's amount updated successfully!");
                    } else {
                        System.out.println("❌ Account not updated!");
                    }

                }else if (decision.equalsIgnoreCase("NO")) {
                    System.out.println("Account amount kept the same!");
                }
                System.out.println("-----------------------------------------");



            } catch (Exception e) {
                e.printStackTrace();
            }





    }

     public void changeTheWholeAccount(String accnumber){

        //PROMPTING THE USER TO UPDATE THE ACCOUNT NAME

         try {Connection con=DriverManager.getConnection(db.jdbcUrl,db.dbUsername,db.dbPassword);
             System.out.print("Enter the new account name: ");
         changedName = sc.nextLine();
         String sqlName="UPDATE bank_account SET account_name=? where account_number=?";
         PreparedStatement psName=con.prepareStatement(sqlName);
         psName.setString(1,changedName);
         psName.setString(2,accnumber);
         int rows = psName.executeUpdate();
         if (rows > 0) {
             System.out.println("✅ Account updated successfully!");
         } else {
             System.out.println("❌ Account not updated!");
         }


         //PROMPTING THE USER TO ENTER THE NEW ACCOUNT TYPE
             System.out.print("Please enter the new account type you want: ");
             newType=sc.nextLine();
             String sqlType="UPDATE bank_account SET account_type=? WHERE account_number=?";
             PreparedStatement psType=con.prepareStatement(sqlType);
             psType.setString(1,newType);
             psType.setString(2,accnumber);
             int rowsType = psType.executeUpdate();
             if (rows > 0) {
                 System.out.println("✅ Account updated successfully!");
             } else {
                 System.out.println("❌ Account not updated!");
             }


             //PROMPTING THE USER TO ENTER A NEW BALANCE
             System.out.print("Please enter the new amount you want to deposit?: ");
             Double newAmount=sc.nextDouble();
             String sqlAmounts="UPDATE bank_account SET account_amount=? where account_number=?";
             PreparedStatement psAmount=con.prepareStatement(sqlAmounts);
             psAmount.setDouble(1,newAmount);
             psAmount.setString(2,accnumber);
             int rowsAmount = psAmount.executeUpdate();
             if (rows > 0) {
                 System.out.println("✅ Account updated successfully!");
             } else {
                 System.out.println("❌ Account not updated!");
             }

         } catch (Exception e) {
             e.printStackTrace();
         }

     }

}
