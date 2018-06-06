package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.simulation.Simulator;

public class Model {

	//IMPORTANTE IL  FLUSSO E IN m^3/s NON in m^3/gg
	
	private Map<Integer, River> rIdMap;
	private Map<Integer, Flow> fIdMap;
	
	private List<River> rivers;
	private List<Flow> flows;
	
	private River currentlySelectedRiver;
	
	private RiversDAO rdao;
	
	private Simulator sim;
	
	public Model() {
		this.rdao = new RiversDAO();
		this.rIdMap = new HashMap<>();
		this.fIdMap = new HashMap<>();
		this.rivers = new ArrayList<>();
		this.flows = new ArrayList<>();
	}

	public List<River> getAllRivers() {
		this.rivers = this.rdao.getAllRivers(rIdMap);
		return this.rivers;
	}
	public void upadateAllRiverFlaws() {
		for(River r: this.rivers) {
			r.updateAllData(this.rdao.getAllFlowsForRiver(r, rIdMap, fIdMap));
		}
	}
	
	public River getCurrentlySelectedRiver() {
		return currentlySelectedRiver;
	}

	public void setCurrentlySelectedRiver(River currentlySelectedRiver) {
		this.currentlySelectedRiver = currentlySelectedRiver;
		List<Flow> rFlows = this.currentlySelectedRiver.getFlows();
		if(rFlows == null || rFlows.isEmpty()) {
			this.currentlySelectedRiver.updateAllData(this.rdao.getAllFlowsForRiver(this.currentlySelectedRiver, rIdMap, fIdMap));
		}
	}

	public Map<Integer, River> getrIdMap() {
		return rIdMap;
	}

	public Map<Integer, Flow> getfIdMap() {
		return fIdMap;
	}

	public List<River> getRivers() {
		return rivers;
	}

	public List<Flow> getFlows() {
		return flows;
	}

	public RiversDAO getRdao() {
		return rdao;
	}

	public void simulate(double k) {
		sim = new Simulator();
		sim.inti(k, this.currentlySelectedRiver);
		sim.run();
	}

	public String printSimulationResults() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Start level: %f AVEGARE FLUX IN: %f\n", sim.getSTARTLEVEL(), sim.getAvgInFlux()));
		sb.append(String.format("AVERAGE LEVEL: %f NUM FAILURES: %d", sim.getAvgLevel(), sim.getNumFailures()));

		return sb.toString();
	}
	
	
	
}
