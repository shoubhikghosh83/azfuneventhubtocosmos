package com.srt.azure.apptranslog.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransLogObject {
	
	private String transactionId;
	private String transactionType;
	

}
