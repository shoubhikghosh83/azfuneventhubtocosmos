package com.srt.azure;


import com.microsoft.azure.functions.annotation.Cardinality;
import com.microsoft.azure.functions.annotation.CosmosDBOutput;
import com.microsoft.azure.functions.annotation.EventHubOutput;
import com.microsoft.azure.functions.annotation.EventHubTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.srt.azure.apptranslog.model.TransLogObject;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;

public class Function {

    @FunctionName("pushEvent")
    @EventHubOutput(
        name = "event",
        eventHubName = "", // blank because the value is included in the connection string
        connection = "EventHubConnectionString")
    public TransLogObject pushEvent(
        @TimerTrigger(
            name = "timerInfo",
            schedule = "*/10 * * * * *") // every 10 seconds
            String timerInfo,
        final ExecutionContext context) {

        context.getLogger().info("Java Timer trigger function executed at: "
            + java.time.LocalDateTime.now());
        
        return TransLogObject.builder().transactionId(Double.toString(Math.random())).transactionType("A").build();
    }

    @FunctionName("getEvent")
    public void getEvent(
        @EventHubTrigger(
            name = "msg",
            eventHubName = "", // blank because the value is included in the connection string
            cardinality = Cardinality.ONE,
            connection = "EventHubConnectionString")
        TransLogObject lObj,
        @CosmosDBOutput(
            name = "databaseOutput",
            databaseName = "transaction-log-db",
            collectionName = "app-transaction-data",
            connectionStringSetting = "CosmosDBConnectionString")
            OutputBinding<TransLogObject> document,
        final ExecutionContext context) {

        context.getLogger().info("Event hub message received: " + lObj.toString());

        

        document.setValue(lObj);
    }
}