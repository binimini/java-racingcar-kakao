package racingcar.domain;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
    private List<Car> cars;
    private int leftRoundCnt;

    private static final int RANDOM_BOUND = 10;
    private static final int NO_DISTANCE = -1;

    public GameInfo init(List<String> names, int roundCnt) {
        this.cars = names
                .stream()
                .map(Car::new)
                .collect(Collectors.toList());
        this.leftRoundCnt = roundCnt;
        return new GameInfo(cars, leftRoundCnt);
    }

    public GameInfo runRound() {
        for (Car car : cars) {
            car.move(new Random().nextInt(RANDOM_BOUND));
        }
        leftRoundCnt--;
        return new GameInfo(cars, leftRoundCnt);
    }

    public List<CarInfo> getWinners(GameInfo gameInfo) {
        int maxDistance = getMaxDistance(gameInfo.getCarInfos());

        List<CarInfo> winners = gameInfo.getCarInfos()
                .stream()
                .filter(carInfo -> carInfo.getDistance()==maxDistance)
                .collect(Collectors.toList());
        return winners;
    }

    private int getMaxDistance(List<CarInfo> carInfos) {
        return carInfos.stream()
                .mapToInt(CarInfo::getDistance)
                .max()
                .orElseGet(()->NO_DISTANCE);
    }
}
