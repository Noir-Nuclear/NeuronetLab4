package Bee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Community {
    private Double[][] interval;
    private Double intervalLength;
    private Double[] goodResults;
    private Double[][] goodPoints;
    private Double[] bestPoint;
    private Double bestResult;
    private Function<List<Double>, Double> function;
    private Integer countOfScouts;
    private Integer countOfWorkers;
    private Integer variableCount;
    private Integer countOfBestPoints;

    public Community(Double[][] interval, Integer countOfScouts, Integer countOfWorkers, Function<List<Double>, Double> function, Integer variableCount, Double intervalLength) {
        this.interval = interval;
        this.function = function;
        this.variableCount = variableCount;
        this.countOfScouts = countOfScouts;
        this.countOfWorkers = countOfWorkers;
        countOfBestPoints = 5;
        goodResults = new Double[countOfBestPoints];
        for (int i = 0; i < countOfBestPoints; i++) {
            goodResults[i] = Double.MAX_VALUE;
        }
        goodPoints = new Double[countOfBestPoints][variableCount];
        this.intervalLength = intervalLength;
    }

    public Double getRandomValue(Double begin, Double end) {
        return Math.random() * (end - begin) + begin;
    }

    public Double[] getBestPoint() {
        return bestPoint;
    }

    public Double getBestResult() {
        return bestResult;
    }

    public void scout() {
        ArrayList<Double> results = new ArrayList<>();
        ArrayList<List<Double>> points = new ArrayList<>();
        IntStream.range(0, countOfScouts).mapToObj(i -> getNewPoint(null)).forEach(newPoint -> {
            points.add(newPoint);
            results.add(function.apply(newPoint));
        });
        getBestPoints(results, points);
    }

    public void handleIntervals() {
        ArrayList<Double> results = new ArrayList<>();
        ArrayList<List<Double>> points = new ArrayList<>();
        for (int i = 0; i < goodPoints.length; i++) {
            Double[][] newInterval = new Double[goodPoints[i].length][2];
            for (int j = 0; j < goodPoints[i].length; j++) {
                newInterval[j][0] = goodPoints[i][j] - intervalLength;
                newInterval[j][1] = goodPoints[i][j] + intervalLength;
            }
            IntStream.range(0, countOfWorkers).
                    mapToObj(j -> getNewPoint(newInterval)).
                    forEach(newPoint -> {
                points.add(newPoint);
                results.add(function.apply(newPoint));
            });
        }
        getBestPoints(results, points);
    }

    public void getBestPoints(ArrayList<Double> results, ArrayList<List<Double>> points) {
        for (int i = 0; i < countOfBestPoints; i++) {
            Integer minIndex = results.indexOf(Collections.min(results));
            Double[] goodPoint = points.remove(minIndex.intValue()).toArray(new Double[points.get(0).size()]);
            Double goodResult = results.remove(minIndex.intValue());
            for (int j = 0; j < goodResults.length; j++) {
                if (goodResults[j] > goodResult) {
                    goodPoints[j] = goodPoint;
                    goodResults[j] = goodResult;
                    break;
                }
            }
        }
    }

    public List<Double> getNewPoint(Double[][] overridedInterval) {
        List<Double> point = new ArrayList<>();
        if (overridedInterval != null) {
            for (int i = 0; i < variableCount; i++) {
                point.add(getRandomValue(overridedInterval[i][0], overridedInterval[i][1]));
            }
            return point;
        }
        for (int i = 0; i < variableCount; i++) {
            point.add(getRandomValue(interval[i][0], interval[i][1]));
        }
        return point;
    }

    public void find(int iterCount) {
        scout();
        for (int i = 0; i < iterCount; i++) {
            handleIntervals();
        }
        bestResult = goodResults[0];
        bestPoint = goodPoints[0];
        IntStream.range(1, goodResults.length).
                filter(i -> bestResult > goodResults[i]).
                forEach(i -> {
            bestResult = goodResults[i];
            bestPoint = goodPoints[i];
        });
    }
}
