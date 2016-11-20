package cs414.a5.rbetten.server;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import cs414.a5.rbetten.common.Ticket;

public interface IParkingGarage extends java.rmi.Remote {

	public boolean checkGarageSpace() throws RemoteException;

	public Ticket addCarToGarage() throws RemoteException;

	public void removeCarFromGarage(Ticket ticket) throws RemoteException;

	public String runReports(LocalDateTime begin, LocalDateTime end) throws RemoteException;

	/**
	 * Creates the Occupation Report for a period of dates
	 * 
	 * @param begin
	 *            beginning date
	 * @param end
	 *            ending date
	 * @return Occupation Report
	 */
	public String runOccupationReports(LocalDateTime begin, LocalDateTime end) throws RemoteException;

	/**
	 * Creates the Financial Report for a period of dates
	 * 
	 * @param begin
	 *            beginning date
	 * @param end
	 *            ending date
	 * @return Financial Report
	 */
	public String runFinancialReports(LocalDateTime begin, LocalDateTime end) throws RemoteException;

	public ArrayList<Ticket> getTickets() throws RemoteException;

	public int getCarOccupancy() throws RemoteException;

	public int getMaxCarOccupancy() throws RemoteException;

	public IRecordManager getRecordManager() throws RemoteException;

	public void addFinancialRecordCash(Ticket ticket, double amountPaid, LocalDateTime ldt) throws RemoteException;

	public void addFinancialRecordCredit(Ticket ticket, String ccNumber, LocalDateTime expDate, double amountPaid,
			LocalDateTime ldt) throws RemoteException;

	public void addFinancialRecordAdmin(String userAddress, String userName, String userPhoneNumber, double amountOwed,
			LocalDateTime dateOwed) throws RemoteException;
}