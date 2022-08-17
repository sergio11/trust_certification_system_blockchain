package com.dreamsoftware.tcs.utils;

import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author ssanchez
 */
public interface OnWordFoundCallback {

    /**
     *
     * @param run
     */
    void onWordFoundInRun(XWPFRun run);

    /**
     *
     * @param runs
     * @param currentRun
     */
    void onWordFoundInPreviousCurrentNextRun(List<XWPFRun> runs, int currentRun);
}
