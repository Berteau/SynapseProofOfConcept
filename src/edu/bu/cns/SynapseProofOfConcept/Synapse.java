package edu.bu.cns.SynapseProofOfConcept;


import java.util.*;

import javax.print.attribute.SetOfIntegerSyntax;

public class Synapse 
{
	private boolean reporting = false;
	
	private int vMemory = 100;

	private int releaseVoltage = -15;
	
	private double[] vSource = new double[vMemory];
	
	private int t = 0;
	
	private double v = -60;
	private int nt = 0; // in quanta

	private double probOfOccupancy = 0.6;
	private double releaseRate = 0.2;
	private double availablePool = 1500;
	private double poolSize = availablePool * probOfOccupancy; 
	private double ntDecay = 0.1; // percent
	
	private double poolRestore = 100; // It is linear - slope of 1Pool/muM/sec  (hosoi et al, unpublished)
	
	private double ntBinding = 0.4; // percent
	private NervousSystemCell source;
	
	private double chargePerQuanta = 10.0;
	
	public Synapse()
	{
	}
	
	public Synapse(NervousSystemCell givenSource)
	{
		source = givenSource;
	}
	
	public double getDepolarization()
	{
		double charge = nt * ntBinding * chargePerQuanta;
		nt = nt - (int)(nt * ntBinding);
		if(reporting) System.out.print(","+ charge);
		return(charge); 
	}
	
	public void step()
	{
		// Increment t
		t++;
		
		// Get source values
		v = source.getVoltage(); 
//		System.out.println("v="+v);
		// Calculate nt decay
		nt *= (1 - ntDecay);
//		System.out.println("nt="+nt);
		// Calcualte release rate based on v
		releaseRate = 0.2 * ((v - releaseVoltage) / (v + 75));
		if(releaseRate < 0) releaseRate = 0;
//		if(t==499)
//			System.out.println("releaseRate="+((v + 30) / (v + 75))+"..."+releaseRate);
		// Update pool occupancy
		poolSize = availablePool * probOfOccupancy;
//		System.out.println("poolSize="+poolSize);
//		System.out.println("availablePool="+availablePool);
//		System.out.println("probOfOccupancy="+probOfOccupancy);
		// Calculate neurotransmitter release
		nt += poolSize * releaseRate;
		
		// Reduce poolsize by amount released
		availablePool -= (poolSize * releaseRate);
		
		availablePool += poolRestore;
		
		if(reporting) System.out.print(","+poolSize+","+nt);
	}
	
	// Accessors
	
	public int getReleaseVoltage() {
		return releaseVoltage;
	}
	public int getNt() {
		return nt;
	}
	public double getNtBinding() {
		return ntBinding;
	}
	public double getNtDecay() {
		return ntDecay;
	}
	public double getPreWeight() {
		return releaseRate;
	}
	public NervousSystemCell getSource() {
		return source;
	}
	public int getVMemory() {
		return vMemory;
	}
	public double[] getVSource() {
		return vSource;
	}
	public double getPoolSize() {
		return poolSize;
	}
	public double getAvailablePool() {
		return availablePool;
	}
	
	// Mutators
	public void setReleaseVoltage(int releaseVoltage) {
		this.releaseVoltage = releaseVoltage;
	}
	public void setNt(int nt) {
		this.nt = nt;
	}
	public void setNtBinding(double ntBinding) {
		this.ntBinding = ntBinding;
	}
	public void setNtDecay(double ntDecay) {
		this.ntDecay = ntDecay;
	}
	public void setPreWeight(double preWeight) {
		this.releaseRate = preWeight;
	}
	public void setSource(NervousSystemCell source) {
		this.source = source;
	}
	public void setVMemory(int memory) {
		vMemory = memory;
	}
	public void setVSource(double[] source) {
		vSource = source;
	}
	public void setChargePerQuanta(double chargePerQuanta) {
		this.chargePerQuanta = chargePerQuanta;
	}
	public void setPoolRestore(double poolRestore) {
		this.poolRestore = poolRestore;
	}
	public void setPoolSize(double poolSize) {
		this.poolSize = poolSize;
	}
	public void setProbOfOccupancy(double probOfOccupancy) {
		this.probOfOccupancy = probOfOccupancy;
	}
}
