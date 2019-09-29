package com.thalmic.myo.Threads;

import kuusisto.tinysound.Music;

import static java.lang.Thread.sleep;

// This thread is in charge of adding a new beat to the currently playing one, so they'll both start playing in a synchronized fashion.
// IE - adding a bass tune to an already playing drum tune.
// Parameter A for constructor should be the "other tune", parameter B should be the tune you're adding.

public class threadAddition implements Runnable {
    private Music tune_bg;
    private Music tune_new;

    public threadAddition(Music A, Music B)
    {
        tune_bg = A;
        tune_new = B;
    }

    public void run()
    {
        tune_new.rewind();
        tune_bg.setLoop(false);
        while(tune_bg.playing())
        {
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tune_bg.stop();
        tune_bg.play(true);
        tune_new.play(true);
        return;
    }
}
