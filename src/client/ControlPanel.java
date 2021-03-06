package client;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import core.endpoints.Destination;
import service.DemandMatrix;
import service.ReportGenerator;
import service.SimulationClock;
import service.TrafficSignalScheduler;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 5379117713281763963L;
	
	private PolicyPanel policy_panel;
	private DemandMatrixPanel demand_matrix_panel;
	private ClockPanel clock_panel;

	public ControlPanel(Timer tm, SimulationClock simClock) {
		super();
		setLayout(new BorderLayout());
		
		policy_panel = new PolicyPanel();
		policy_panel.setClockTimer(tm);
		
		add(policy_panel,BorderLayout.CENTER);
		
		demand_matrix_panel = new DemandMatrixPanel();
		add(demand_matrix_panel,BorderLayout.EAST);
		
		clock_panel= new ClockPanel();
		clock_panel.setClock(tm, simClock);
		add(clock_panel,BorderLayout.SOUTH);
	}
	
	public void setDemandMatrixCars(DemandMatrix dm){
		demand_matrix_panel.setDemandMatrixCars(dm);
	}
	
	public void setDemandMatrixBuses(DemandMatrix dm){
		demand_matrix_panel.setDemandMatrixBuses(dm);
	}
	
	public void setReportGenerator(ReportGenerator generator)
	{
		clock_panel.setReportGenerator(generator);
	}

	public void addTrafficScheduler(TrafficSignalScheduler scheduler)
	{
		policy_panel.addLightScheduler(scheduler);
	}

	public void addDestinations(Destination d)
	{
		policy_panel.addDesitnation(d);
	}
}
