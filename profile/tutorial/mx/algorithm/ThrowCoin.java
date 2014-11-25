package mx.algorithm;

import java.util.Random;

/**
 * Created by hxiong on 6/25/14.
 */
public class ThrowCoin {

    static Random r = new Random();

    static int random(){
        return r.nextInt(2);
    }

    static int play(int n, int max){
        if(0 == random() % 2){
            return n % max;
        }else{
            return play(++n, max);
        }
    }

    static void winRate(){
        int PLAYER_COUNT = 10;
        int ROUND = 100;

        int PLAYER = 0;
        int win = 0;

        for(int i = 0; i < ROUND; i++){
            if(PLAYER == play(PLAYER, PLAYER_COUNT)){
                win ++;
            }
        }

        System.out.println("player[" + PLAYER + "]'s win rate is: " + ( ((double)win) / ROUND));
    }

    public static void main(String[] args) {
           winRate();
    }


}
