package it.polito.tdp.rivers.simulation;

public class Day implements Comparable<Day>{

	private int day;
	private double outFluxDemand;
	
	public Day(int day, double outFluxDemand) {
		super();
		this.day = day;
		this.outFluxDemand = outFluxDemand;
	}
	public int getDay() {
		return day;
	}
	public double getOutFluxDemand() {
		return outFluxDemand;
	}
	@Override
	public String toString() {
		return "Day [day=" + day + ", outFluxDemand=" + outFluxDemand + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
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
		Day other = (Day) obj;
		if (day != other.day)
			return false;
		return true;
	}
	@Override
	public int compareTo(Day o) {
		return this.day-o.day;
	}
}
