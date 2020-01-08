import Bee.Community;
import common.Params;

import java.util.Arrays;
import java.util.function.Function;

public class Main {
    private static Function<Params, Double> f = params -> Math.pow(params.args.get(0), 2.0) + Math.pow(params.args.get(1), 2.0);

    public static void main(String[] args) {
        Community community = new Community(new Double[][] {{-20.0, 20.0}, {-20.0, 20.0}}, 10, 5, f, 2, 2.0);
        community.find(60);
        System.out.println(Arrays.toString(community.getBestPoint()));
        System.out.println(community.getBestResult());
    }
}
