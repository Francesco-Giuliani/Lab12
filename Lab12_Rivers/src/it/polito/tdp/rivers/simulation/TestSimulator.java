package it.polito.tdp.rivers.simulation;

import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.River;

public class TestSimulator {

	public static void main(String[] args) {
		Simulator sim = new Simulator();
		
		sim.inti(0.5, new River(1, "Po'", 42.0, null, null));
		PriorityQueue<Day> queue = sim.getQueue();
		
		Day[] arr = new Day[10];
		for(Day d: queue.toArray(arr))
			System.out.println(d);
	
		sim.run();
		System.out.format("K factor: %f AVEGARE FLUX IN: %f\n", sim.getSTARTLEVEL(), sim.getAvgInFlux());
		System.out.format("AVERAGE LEVEL: %f NUM FAILURES: %d", sim.getAvgLevel(), sim.getNumFailures());
	}
}
