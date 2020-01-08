import Bee.Community;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Main {
    private static Function<List<Double>, Double> f = params -> Math.abs(params.get(0)) + Math.abs(params.get(1));

    public static void main(String[] args) {
        Community community = new Community(new Double[][] {{-20.0, 20.0}, {-20.0, 20.0}}, 10, 5, f, 2, 2.0);
        community.find(60);
        System.out.println(Arrays.toString(community.getBestPoint()));
        System.out.println(community.getBestResult());
    }
}
