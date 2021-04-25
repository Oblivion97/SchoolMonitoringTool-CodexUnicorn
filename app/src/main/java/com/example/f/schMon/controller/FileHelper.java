/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.controller;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 05/08/2017.
 */
public class FileHelper {
    public static String getStringFromFile(String location, String filename) {
        StringBuilder sb = new StringBuilder();
        File file = new File(location + File.separator + filename);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                sb.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (br != null)
                try {
                    br.close();
                    if (fr != null)
                        fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return sb.toString();
    }

    public static boolean saveStringtoFile(String dataToSave, String fileLocation, String fileName) {
        boolean success = false;
        File file = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileLocation + File.separator + fileName);
            pw = new PrintWriter(fileWriter);
            pw.print(dataToSave);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (pw != null)
                    pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public static String getTextFileFromAssets(Context context, String filename) {
        BufferedReader reader = null;
        StringBuilder sb = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));
            // do reading, usually loop until end of file reading
            sb = new StringBuilder();
            String mLine = reader.readLine();
            while (mLine != null) {
                sb.append(mLine); // process line
                mLine = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
