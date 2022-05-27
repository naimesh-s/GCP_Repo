package com.externalmodeller.gcp.model;

import java.io.Serializable;

public class DedicatedResources  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private MachineSpec machineSpec;
	private String startingReplicaCount;
	private String maxReplicaCount;
	
	public String getMaxReplicaCount() {
		return maxReplicaCount;
	}
	public void setMaxReplicaCount(String maxReplicaCount) {
		this.maxReplicaCount = maxReplicaCount;
	}
	public MachineSpec getMachineSpec() {
		return machineSpec;
	}
	public void setMachineSpec(MachineSpec machineSpec) {
		this.machineSpec = machineSpec;
	}
	public String getStartingReplicaCount() {
		return startingReplicaCount;
	}
	public void setStartingReplicaCount(String startingReplicaCount) {
		this.startingReplicaCount = startingReplicaCount;
	}
	
}
