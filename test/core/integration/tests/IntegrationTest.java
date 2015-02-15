package core.integration.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.endpoints.Destination;
import core.endpoints.EndPointException;
import core.network.Road;
import core.network.interfaces.InterfaceException;
import core.network.junction.Junction;
import core.network.junction.Junction.JUNCTION;
import core.network.junction.JunctionRouter;
import core.vehicle.Car;
import core.vehicle.Vehicle;

public class IntegrationTest {

	@Test
	public void test_junction_transfer_to_three_destinations() throws InterfaceException, EndPointException 
	{
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		Destination D = new Destination();
		
		Road r1 = new Road(1,3);
		Road r2 = new Road(1,3);
		Road r3 = new Road(1,3);
		Road r4 = new Road(1,3);
		
		Junction junc = new Junction();
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		Vehicle v3 = new Car();
		
		/*
		 * AM > Road-junction wiring
		 * 			  |B|
		 * 			   |
		 * 			   r2
		 * 			   |
		 * |A|--r1---|junc|---r3--|C|
		 * 			   |
		 * 			  r4
		 * 			   |
		 * 			  |D|
		 */
		r1.setSource(A);
		r1.setSink(junc, JUNCTION.WEST);
		r2.setSource(junc, JUNCTION.NORTH);
		r2.setSink(B);
		r3.setSource(junc, JUNCTION.EAST);
		r3.setSink(C);
		r4.setSource(junc, JUNCTION.SOUTH);
		r4.setSink(D);
		
		//AM > Setup routing table
		JunctionRouter juncRouter = new JunctionRouter();
		juncRouter.add(A, junc.getInterface(JUNCTION.WEST));
		juncRouter.add(B, junc.getInterface(JUNCTION.NORTH));
		juncRouter.add(C, junc.getInterface(JUNCTION.EAST));
		juncRouter.add(D,  junc.getInterface(JUNCTION.SOUTH));
		junc.setRoutingTable(juncRouter);
		
		//AM > Setup traffic signals
		junc.getInterface(JUNCTION.WEST).setSignal(junc.getInterface(JUNCTION.EAST), true);
		junc.getInterface(JUNCTION.WEST).setSignal(junc.getInterface(JUNCTION.NORTH), true);
		junc.getInterface(JUNCTION.WEST).setSignal(junc.getInterface(JUNCTION.SOUTH), true);
		
		//AM > Setup vehicle destinations
		v1.setDestination(B);
		v2.setDestination(C);
		v3.setDestination(D);
		
		A.addVehicle(v1);
		A.addVehicle(v2);
		A.addVehicle(v3);
		
		for(int i = 0; i < 7; i++)
		{
			r1.moveTraffic();
			r2.moveTraffic();
			r3.moveTraffic();
			r4.moveTraffic();
		}
		
		assertEquals(1, B.getQueueLength());
		assertEquals(1, C.getQueueLength());
		assertEquals(1, D.getQueueLength());
	}

}