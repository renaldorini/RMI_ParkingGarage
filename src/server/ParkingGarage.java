package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import common.Ticket;

public class ParkingGarage extends java.rmi.server.UnicastRemoteObject implements IParkingGarage, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Ticket> ticketsInGarage = new ArrayList<Ticket>();
	private IRecordManager recordManager;
	private int maxOccupancy;

	public ParkingGarage(int maxOccu) throws RemoteException
	{
		recordManager = new RecordManager();
		this.maxOccupancy = maxOccu;
	}

	public boolean checkGarageSpace() throws RemoteException
	{
		if(ticketsInGarage.size() == maxOccupancy)
		{
			return true;
		}
		return false;
	}

	public Ticket addCarToGarage() throws RemoteException
	{
		Ticket t = null;
		try {
			if( !checkGarageSpace() )
			{
				t = new Ticket(LocalDateTime.now());
				ticketsInGarage.add(t);
				
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
	public void removeCarFromGarage(Ticket ticket) throws RemoteException
	{
		ticketsInGarage.remove(ticket);	
	}
	
	public String runReports(LocalDateTime begin, LocalDateTime end) throws RemoteException
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(runOccupationReports(begin,end));
		sb.append("\n");
		sb.append(runFinancialReports(begin,end));
		
		return sb.toString();
	}
	
	/**
	 * Creates the Occupation Report for a period of dates
	 * @param begin beginning date
	 * @param end ending date
	 * @return Occupation Report
	 */
	public String runOccupationReports(LocalDateTime begin, LocalDateTime end) throws RemoteException
	{
		return recordManager.getOccupationRecords(begin, end);
	}
	
	/**
	 * Creates the Financial Report for a period of dates
	 * @param begin beginning date
	 * @param end ending date
	 * @return Financial Report
	 */
	public String runFinancialReports(LocalDateTime begin, LocalDateTime end) throws RemoteException
	{

		System.out.println("Returned Parking Garage Reports");
		return recordManager.getFinancialRecords(begin, end);
	}
	
	public IRecordManager getRecordManager() throws RemoteException
	{
		return recordManager;
	}
	
	public int getMaxCarOccupancy() throws RemoteException
	{
		return maxOccupancy;
	}
	
	public int getCarOccupancy() throws RemoteException
	{
		return ticketsInGarage.size();
	}
	
	public ArrayList<Ticket> getTickets() throws RemoteException
	{
		return ticketsInGarage;
	}
	
}
