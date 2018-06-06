package it.polito.tdp.rivers.db;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

public class TestRiversDAO {

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();
		//System.out.println(dao.getAllRivers());
		
		River r = dao.getRiverById(1);
		System.out.println("Selected river: "+r);
		System.out.println("Flow list: ");
		for(Flow f: r.getFlows())
			System.out.format("%d %s %f\n", f.getId(), f.getDay().toString(), f.getFlow());
		
		System.out.format("Start date: %s End date: %s Flow average: %f \n", r.getStartDate(), r.getEndDate(), r.getFlowAvg());
	}

}
