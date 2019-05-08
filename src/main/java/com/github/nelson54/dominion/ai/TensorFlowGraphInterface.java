package com.github.nelson54.dominion.ai;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Session;

import java.util.List;

public class TensorFlowGraphInterface {
    public final Graph graph;
    public final Session session;
    public final Operation turnNumberOp;
    public final Operation actionsOp;
    public final Operation cardOp;
    public final Operation playOperation;

    public TensorFlowGraphInterface() {
        graph = new Graph();

        session = new Session(graph);

        turnNumberOp = graph.opBuilder("Placeholder", "turnNumber")
                .setAttr("dtype", DataType.fromClass(Long.class))
                .build();


        actionsOp = graph.opBuilder("Placeholder", "actions")
                .setAttr("dtype", DataType.fromClass(Long.class))
                .build();

        cardOp = graph.opBuilder("Placeholder", "card")
                .setAttr("dtype", DataType.fromClass(String.class))
                .build();

        playOperation = graph.opBuilder("Placeholder", "play")
                .addInput(cardOp.output(0))
                .addInput(actionsOp.output(0))
                .addInput(turnNumberOp.output(0))
                .build();
    }
}
