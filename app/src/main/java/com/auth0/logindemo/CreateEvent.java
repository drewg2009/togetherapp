package com.auth0.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

public class CreateEvent extends AppCompatActivity {

    private static int NUM_PERIODS = 392;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_event);
        //set up sample strings

        int i=0;
        String one = "00001000110011100000111110000011111000001111100000111000" +
                     "11111000001111100000111110000011111000001111100000111000" +
                     "11111000001111100000111110000011111000001111100000111000" +
                     "11111000001111100000111110000011111000001111100000111000" +
                     "11111000001111100000111110000011111000001111100000111000" +
                     "11111000001111100000111110000011111000001111100000111000" +
                     "11111000001111100000111110000011111000001111100000111000";

        String two = "00000111110000011111000001111100000111110000011111000111" +
                     "00000111110000011111000001111100000111110000011111000111" +
                     "00000111110000011111000001111100000111110000011111000111" +
                     "00000111110000011111000001111100000111110000011111000111" +
                     "00000111110000011111000001111100000111110000011111000111" +
                     "00000111110000011111000001111100000111110000011111000111" +
                     "00000111110000011111000001111100000111110000011111000111";

        ArrayList<Pair<Integer,Integer>> suggestedEvents = findHoles(one,two);
        int a=3;
    }

    /*Takes two strings of zeros and ones, converts them into 2D arrays,
    * and adds them together.  Then iteratively detects strings of zeros,
    * converts them into real times, then returns an ArrayList of Integer
    * pairs that represent the starting point and duration of the "runs"
    * of zeros representing free time.*/

    private ArrayList<Pair<Integer,Integer>> findHoles(String person1, String person2){

        //NOTE: This assumes that the two users' "day0" are the same.


        //Add the two strings together
        //7 * 56 = 392 1's and 0's in each string
        int i;
        int[] added = new int[NUM_PERIODS];
        for(i=0; i<NUM_PERIODS; i++){
            int busyness = (int)person1.charAt(i) + (int)person2.charAt(i);
            added[i] = busyness;
        }

        ArrayList<Pair<Integer,Integer>> openings = new ArrayList<Pair<Integer, Integer>>();
        boolean isNewRun = true;
        int j=0;  //j=beginning of current "run"
        int k=0;  //k=length of current run

        for(i=0;i<NUM_PERIODS;i++){
            if(i==0){
                //the slot at i is free
                if(isNewRun){
                    //if it's a new run, set j and update isNewRun
                    j = i;
                    isNewRun = false;
                }
                k++; //regardless, increment the length of current run
            } else {
                Pair<Integer,Integer> pair = new Pair<>(j,k);
                openings.add(pair);
                k = 0;
                isNewRun = true;
            }
        }
        return openings;
    }
}
