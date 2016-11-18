package client;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Scanner;

import common.CashPayment;
import common.ParkingGarageController;

public class ParkingGarageClient {
	static Scanner input = new Scanner(System.in);
	static ParkingGarageController controller;
	
	public static void main(String[] args) {
		controller = new ParkingGarageController("rmi://"+ args[0] + ":" + args[1] + "/ParkingGarageServer");
		
		if(controller.addCarToGarage())
		{
			System.out.println("Ticket: " + controller.getLastTicket().getUniqueID());
			System.out.println("Garage Status: " + controller.getGarageOccupancyStatus());
		}else
		{
			System.out.println("FULL GARAGE");
		}
		
		LocalDateTime today = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour(), 0);
		System.out.println("Car Exited: " + controller.removeCarFromGarage(controller.getLastTicket().getUniqueID(), 10.00, today));
		System.out.println("Garage Status: " + controller.getGarageOccupancyStatus());


		System.out.println("\n\nReports:\n" + controller.runReports(LocalDateTime.of(2016, 9, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0)));
	}
}
