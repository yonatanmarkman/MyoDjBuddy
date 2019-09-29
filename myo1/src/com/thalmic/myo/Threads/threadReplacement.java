package com.thalmic.myo.Threads;

import kuusisto.tinysound.Music;

import static java.lang.Thread.sleep;

// This thread is in charge of replacing an old beat with a new one of the same kind, so the new one will start playing
// immidiately when the old one stops. (instead of interrupting it in the middle).

// Parameter A for constructor should be the "other tune", parameter B should be the new one.
public class threadReplacement implements Runnable {
    private Music oldTune;
    private Music newTune;

    public threadReplacement(Music A, Music B)
    {
        oldTune = A;
        newTune = B;
    }

    public void run()
    {
        oldTune.setLoop(false);
        while (oldTune.playing())
        {
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        oldTune.stop();
        newTune.play(true);
        return;
    }

}
