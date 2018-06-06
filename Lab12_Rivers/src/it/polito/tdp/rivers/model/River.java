package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class River {
	
	private int id;
	private String name;
	private double flowAvg;
	private List<Flow> flows;
	private RiversDAO rdao = new RiversDAO();
	
	public River(int id) {
		this.id = id;
		this.flows = new ArrayList<>();
		this.flowAvg = -1.0;
	}

	public River(int id, String name) {
		this.id = id;
		this.name = name;
		this.flows = new ArrayList<>();
		this.flowAvg = -1.0;
	}

	public River(int id, String name, double flowAvg, List<Flow> flows, RiversDAO rdao) {
		super();
		this.id = id;
		this.name = name;
		this.flowAvg = flowAvg;
		this.flows = flows;
		this.rdao = rdao;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	public double getFlowAvg() {
		if(this.flowAvg == -1.0) {
			this.flows = this.getFlows();
			this.flowAvg = this.calculateAvg();
		}
		return flowAvg;
	}


	public List<Flow> getFlows() {
		return flows;
	}
	
	public void updateAllData(List<Flow>flows) {
		this.flows = flows;
		this.flowAvg = this.calculateAvg();
	}

	private double calculateAvg() {
		if(this.flows == null || flows.isEmpty())
			return -0.1;
		double sum= 0.0;
		for(Flow f: this.flows) {
			sum += f.getFlow();
		}
		return sum/this.flows.size();
	}
	
	public LocalDate getStartDate() {
		return this.flows.get(0).getDay();
	}

	public LocalDate getEndDate() {
		return this.flows.get(this.flows.size()-1).getDay();
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		River other = (River) obj;
		if (id != other.id)
			return false;
		return true;
	}



}
