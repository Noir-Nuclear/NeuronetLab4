package common;

import java.util.ArrayList;
import java.util.List;

public class Params {
    public List<Double> args;
    public Params() {
        args = new ArrayList<>();
    }
    public Params(List<Double> args) {
        this.args = args;
    }
}
