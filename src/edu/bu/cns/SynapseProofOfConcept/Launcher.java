package edu.bu.cns.SynapseProofOfConcept;

public class Launcher 
{
	public static void main(String[] args)
	{
		int t=0;
		int maxT = 1000;	
		
		// Create neuron 1
		Neuron n1 = new Neuron(maxT);
		// Create Synapse
		Synapse s1 = new Synapse();
		s1.setSource(n1);
		s1.setPoolRestore(50.0);
		s1.setChargePerQuanta(13);
		// Create neuron 2
		Neuron n2 = new Neuron(maxT);
		// Add Synapse
		n2.addInput(s1);
		// Turn on reporting
		n1.setReporting(true);
		n2.setReporting(true);
		// Run for 1000 ms
		System.out.println(",N1 Pulse,N1 Voltage,S1 Poolsize,S1 Depolarization,S1 nt,N2 Pulse,N2 Voltage");
		for(t=0;t<maxT;t++)
		{
			n1.setPulse(100);
			n1.step();
			s1.step();
			n2.step();
			System.out.println("");
		}
		System.out.println("Number of spikes from n1: "+n1.numSpikes);
		System.out.println("Number of spikes from n2: "+n2.numSpikes);
	}
}
