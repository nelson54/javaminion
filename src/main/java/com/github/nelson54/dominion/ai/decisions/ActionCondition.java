package com.github.nelson54.dominion.ai.decisions;

import java.util.List;

public class ActionCondition<T> {
    List<Boolean> decisions;
    T obj;

    public ActionCondition(T obj) {
        this.obj = obj;
    }

    public List<Boolean> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<Boolean> decisions) {
        this.decisions = decisions;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
