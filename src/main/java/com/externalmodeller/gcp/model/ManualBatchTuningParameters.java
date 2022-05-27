package com.externalmodeller.gcp.model;

import java.io.Serializable;

public class ManualBatchTuningParameters  implements Serializable {

	private static final long serialVersionUID = 1L;
	private String batch_size;

	public String getBatch_size() {
		return batch_size;
	}

	public void setBatch_size(String batch_size) {
		this.batch_size = batch_size;
	}
	
}
