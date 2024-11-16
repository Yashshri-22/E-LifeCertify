package com.example.project.global;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by software 121216 on 4/4/2018.
 */

public class Config {

    //OLD
//    public static final String AUA_KEY = "MBni88mRNM18dKdiVyDYCuddwXEQpl68dZAGBQ2nsOlGMzC9DkOVL5s";
//    public static final String ASA_KEY = "MMxNu7a6589B5x5RahDW-zNP7rhGbZb5HsTRwbi-VVNxkoFmkHGmYKM";

    //NEW
//    public static final String AUA_KEY = "MBni88mRNM18dKdiVyDYCuddwXEQpl68dZAGBQ2nsOlGMzC9DkOVL5s";
//    public static final String ASA_KEY = "MMxNu7a6589B5x5RahDW-zNP7rhGbZb5HsTRwbi-VVNxkoFmkHGmYKM";

//    public static final String AUA_KEY = "MJH2iRjF-rB_cXU3gTMlgJrqPJdGMPSwtiKQiSWQUQc6cC4itXC8ziE";
//    public static final String ASA_KEY = "MLDzmmbuqzveoN_233dZ9J5qL3K1n_qp0qpdx7Ghp5pqLEARDRlZnkY";

    //TEST
//    public static final String AUA_KEY = "MAElpSz56NccNf11_wSM_RrXwa7n8_CaoWRrjYYWouA1r8IoJjuaGYg";
//    public static final String ASA_KEY = "MEY2cG1nhC02dzj6hnqyKN2A1u6U0LcLAYaPBaLI-3qE-FtthtweGuk";

// TEST  -04-2022
//    public static final String AUA_KEY = "MHRrzmcngDxKNoIqioMOcHbrcVKsPuGYA9RpTWIBJVKw2qik6dR8Th4";
//    public static final String ASA_KEY = "MMT2L3qcGDdaUN7Jb9xZeWcsVTbpfkTuMQ_Bc4OJfs1x0ve2NR7Ep_I";

// TEST  -04-2023
    public static final String AUA_KEY = "MAvSQG0jKTW4XxQc2cI-oXZYxYH-zi7IWmsQY1q3JNLlC8VOWOHYGj8";
    public static final String ASA_KEY = "MCNYL7FpPgjEhx7HBp9tu59Vdm4FnYGlxuqHctfAeNNaCufVafshqzQ";


    //    private static final String DATA_PATH = Environment.getExternalStorageDirectory() + "/RDSample";
    public static void DataLog(Context context, String strLog, String filename, String exts) {
        try {
            String Dir = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) ?
                    context.getExternalFilesDir(null) + "//RDSample//" :
                    Environment.getExternalStorageDirectory() + "/RDSample";
            boolean isNewFile = false;
            //String FileNameFormate = this.getCurrentDateTime("yyyy-MM-dd HH");
            String FileName = Dir + "/" + filename + exts;
            String logTime = getCurrentDateTime("yyyy-MM-dd HH-mm-ss");
            File dir = new File(Dir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File logFile = new File(FileName);
            if (!logFile.exists()) {

                isNewFile = true;
                logFile.createNewFile();
            }

            FileOutputStream fOut = new FileOutputStream(logFile, false);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            //if(isNewFile) {
            if (filename.equalsIgnoreCase("MC")) {
                myOutWriter.append(strLog);
            } else {
                myOutWriter.append("\n\n" + logTime + " :: \n" + strLog + "\n\n");
            }
            /*}
            else {
                myOutWriter.append("\n\n" + logTime + " :: \n" + strLog + "\n\n");
            }*/

            myOutWriter.flush();
            myOutWriter.close();
        } catch (Exception var11) {
            Log.e("ErrorLog", "" + var11);
        }

    }

    public static String getCurrentDateTime(@NonNull String dateTimeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat, Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        return sdf.format(cal.getTime());
    }
}
