package com.dreamsoftware.blockchaingateway.utils;

import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author ssanchez
 */
public interface OnWordFoundCallback {

    void onWordFoundInRun(XWPFRun run);

    void onWordFoundInPreviousCurrentNextRun(List<XWPFRun> runs, int currentRun);
}
