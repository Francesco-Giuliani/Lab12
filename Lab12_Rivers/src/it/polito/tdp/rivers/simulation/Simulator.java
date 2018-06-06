package it.polito.tdp.rivers.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.River;

public class Simulator {
	
	//WORLD
	private River river;
	private List<Double> dailyLevels; 
	private List<Double> dailyOutputs; 
	private double prevDayLevel;

	
	//SIMULATION PARAM
	private final double MIN_OUT_COEFF = 0.8;
	private final double PROB_HIGHER_FLUX_OUT_DEMAND = 0.05;
	private final double HIGHER_FLUX_OUT_DEMAND_COEFF = 10.0;
	private final int SIMULATION_PERIOD_IN_DAYS = 365;
	private double maxLevel;
	private double avgFlowIn; //m^3/gg
	private double START_LEVEL;
	private double MIN_OUT_FLUX;
	
	
	//EVENTS
	private PriorityQueue<Day> queue;

	//RESULTS
	private int numFailures; //failure = day with no flux out min
	private double avgLevel;
	
	public void inti(double scaleFactor, River river) {
		
		//WORLD
		this.river = river;
		this.dailyLevels = new ArrayList<>();
		
		
		//SIMULATION PARAM
		this.avgFlowIn = river.getFlowAvg()*60*24;
		this.maxLevel = scaleFactor * this.avgFlowIn * 30;
		this.START_LEVEL = this.maxLevel/2;
		this.prevDayLevel = this.START_LEVEL;
		this.MIN_OUT_FLUX = this.avgFlowIn*this.MIN_OUT_COEFF;
		
		//EVENTS
		this.queue = new PriorityQueue<>();
		this.fillQueue();
		
		//RESULTS
		this.numFailures =0;
		this.avgLevel = 0.0;
		this.dailyLevels= new ArrayList<>();
		this.dailyOutputs= new ArrayList<>();
	}
	
	public void run() {
		for(Day d: this.queue) {
			double diffFlux = this.avgFlowIn - d.getOutFluxDemand();
			if(diffFlux>=0) {
				this.dailyOutputs.add(d.getOutFluxDemand());
				this.prevDayLevel = this.prevDayLevel + diffFlux;
				this.dailyLevels.add(this.prevDayLevel);
			}else {
				
				if(this.prevDayLevel+diffFlux>=0) {
					this.prevDayLevel = this.prevDayLevel+diffFlux;
					this.dailyLevels.add(this.prevDayLevel);
					this.dailyOutputs.add(d.getOutFluxDemand());
				}
				else {
					this.dailyOutputs.add(this.avgFlowIn+this.prevDayLevel);
					this.prevDayLevel = 0.0;
					this.dailyLevels.add(this.prevDayLevel);
					this.numFailures++;
				}
			}
		}
		this.avgLevel = 0.0;
		for(Double d: this.dailyLevels) {
			this.avgLevel += d;
		}
		this.avgLevel= this.avgLevel/this.dailyLevels.size();
	}
	
	private void fillQueue() {
		for(int i=0; i<this.SIMULATION_PERIOD_IN_DAYS; i++) {
			if(Math.random()>this.PROB_HIGHER_FLUX_OUT_DEMAND)
				this.queue.add((new Day(i, this.MIN_OUT_FLUX)));
			else
				this.queue.add(new Day(i, this.MIN_OUT_FLUX*this.HIGHER_FLUX_OUT_DEMAND_COEFF));
		}
	}

	public PriorityQueue<Day> getQueue() {
		return this.queue;
	}

	public List<Double> getDailyLevels() {
		return dailyLevels;
	}

	public List<Double> getDailyOutputs() {
		return dailyOutputs;
	}

	public int getNumFailures() {
		return numFailures;
	}

	public double getAvgLevel() {
		return avgLevel;
	}

	public double getSTARTLEVEL() {
		return this.START_LEVEL;
	}

	public double getAvgInFlux() {
		return this.avgFlowIn;
	}
	
	

}
