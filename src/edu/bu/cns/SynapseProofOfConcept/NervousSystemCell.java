package edu.bu.cns.SynapseProofOfConcept;

import java.util.ArrayList;


public interface NervousSystemCell 
{
	public double getVoltage();
	public void step();
	public ArrayList<Synapse> getInputs();
}
