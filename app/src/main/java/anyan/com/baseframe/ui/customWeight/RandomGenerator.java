package anyan.com.baseframe.ui.customWeight;

import java.util.Random;

/**
 * Created by Administrator on 2018-2-7.
 */

public class RandomGenerator {
    Random random;

    public RandomGenerator() {
        random = new Random();
    }

    //0-max之间的随机整数
    public int getRandom(int max) {
        return (int) (Math.random() * max) + 1;
    }

    //min-max之间的随机整数
    public int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    //0-max之间的随机浮点数
    public float getRandom(float max) {
        return (float) (Math.random() * max);
    }

    //0-max之间的随机浮点数
    public float getRandom(float min, float max) {
        return (float) (Math.random() * (max - min)) + min;
    }


}
