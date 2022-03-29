package dk.easv;

import javafx.concurrent.Task;

public class DisplayTask extends Task<String> {

    private final boolean[] displays;

    public DisplayTask(boolean[] displays) {
        this.displays = displays;
    }

    @Override
    protected String call() throws Exception {
        return null;
    }
}
