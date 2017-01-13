package edu.bu.cns.SynapseProofOfConcept;


import java.util.*;

public class Neuron implements NervousSystemCell{

	// Debugging
	private boolean reporting = false;
	
	// Fast and slow values, v = membrane potential in volts.
	private double[] v;
	private double[] u; 

	// ##Parameters ##
	private double C = 100.0;

	private double vr = -60.0;

	private double vt = -40.0;

	private double k = 0.7;

	// ## neocortical pyramidal neuron parameters ##
	private double a = 0.03;

	private double b = -0.2;

	private double c = -50.0;

	private double d = 100.0;

	// ## spike cutoff ##
	private int vpeak = 35;

	// ## Time span and step ##
	private int tau = 1;

	private int t = 0;

	private int pulse = 70; // #in pA

	private ArrayList<Synapse> inputs = new ArrayList<Synapse>();

	int numSpikes = 0;
	
	public Neuron(int n)
	{
		// ## Initial values ##
		v = new double[n+1];
		for (int i = 0; i < n+1; i++) {
			v[i] = vr;
		}

		u = new double[n+1];
		for (int i = 0; i < n+1; i++) {
			u[i] = 0;
		}
	}


	public void step() 
	{
		pulse = getPulse();

		v[t + 1] = v[t] + tau
				* (k * (v[t] - vr) * (v[t] - vt) - u[t] + pulse) / C;
		u[t + 1] = u[t] + tau * a * (b * (v[t] - vr) - u[t]);
		if (v[t + 1] >= vpeak) {
			numSpikes++;
			v[t] = vpeak;
			v[t + 1] = c;
			u[t + 1] = u[t + 1] + d;
		}
		t++;
		if(reporting) System.out.print("\n"+t+": pulse="+pulse+", voltage="+v[t]);
	}

	public int getPulse() {
		int tempPulse = 0;
		if (inputs.size() > 0) {
			for (int i = 0; i < inputs.size(); i++) {
				tempPulse += inputs.get(i).getDepolarization();
			}
			return tempPulse;
		} else
			return pulse;
	}
	
	public void addInput(Synapse s)
	{
		inputs.add(s);
	}
	
	public ArrayList<Synapse> getInputs()
	{
		return inputs;
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public double getC() {
		return C;
	}

	public double getD() {
		return d;
	}

	public double getK() {
		return k;
	}

	public double getVr() {
		return vr;
	}

	public double getVoltage() {
		return v[t];
	}
	
	public int getNumSpikes() {
		return numSpikes;
	}
	
	public boolean getReporting(){
		return reporting;
	}
	
	public void setVr(double vr) {
		this.vr = vr;
	}
	public void setVpeak(int vpeak) {
		this.vpeak = vpeak;
	}
	public void setVt(double vt) {
		this.vt = vt;
	}
	public void setPulse(int pulse) {
		this.pulse = pulse;
	}
	public void setReporting(boolean reporting) {
		this.reporting = reporting;
	}
	public void setA(double a) {
		this.a = a;
	}
	public void setB(double b) {
		this.b = b;
	}
	public void setC(double c) {
		C = c;
	}
	public void setD(double d) {
		this.d = d;
	}
	public void setK(double k) {
		this.k = k;
	}
}
