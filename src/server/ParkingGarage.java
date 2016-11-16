package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.HashSet;

import common.CarStatus;
import common.Ticket;

public class ParkingGarage extends java.rmi.server.UnicastRemoteObject implements IParkingGarage, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashSet<Ticket> ticketsInGarage = new HashSet<Ticket>();
	private RecordManager recordManager = new RecordManager();
	private int maxOccupancy;

	public ParkingGarage(int maxOccu) throws RemoteException
	{
		super();
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

				recordManager.addOccupationRecord(t.getCheckinTime(), CarStatus.ENTER);
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
	
	/**
	 * Creates the Occupation Report for a period of dates
	 * @param begin beginning date
	 * @param end ending date
	 * @return Occupation Report
	 */
	public String runOccupationReports(LocalDateTime begin, LocalDateTime end)
	{
		return recordManager.getOccupationRecords(begin, end);
	}
	
	/**
	 * Creates the Financial Report for a period of dates
	 * @param begin beginning date
	 * @param end ending date
	 * @return Financial Report
	 */
	public String runFinancialReports(LocalDateTime begin, LocalDateTime end)
	{
		LocalDateTime beginLDT = LocalDateTime.of(begin.getYear(), begin.getMonth(), begin.getDayOfMonth(), 0, 0);

		LocalDateTime endLDT = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), 0, 0);

		return recordManager.getFinancialRecords(beginLDT, endLDT);
	}
	
	public RecordManager getRecordManager()
	{
		return recordManager;
	}
	
	public int getMaxCarOccupancy()
	{
		return maxOccupancy;
	}
	
	public int getCarOccupancy()
	{
		return ticketsInGarage.size();
	}
	
	public HashSet<Ticket> getTickets()
	{
		return ticketsInGarage;
	}
	
}
