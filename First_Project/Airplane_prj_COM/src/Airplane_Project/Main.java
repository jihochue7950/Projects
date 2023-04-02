package Airplane_Project;

import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Operation operation = new Operation();
        operation.Open();
//        operation.frontWindow();
//       operation.Insert_nonUser();
//        operation.member_joining();
        operation.findPW();
//        operation.FlightSchedule_nonUser();
//        operation.FlightSchedule();
//        operation.FightSelect();
//        operation.checkReservation();
//        operation.input_num(3);
        operation.Close();



    }
}