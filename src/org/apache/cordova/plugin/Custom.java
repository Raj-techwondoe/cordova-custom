package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Custom extends CordovaPlugin {

@Override
public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("enableUSBDebugging")) {
            enableUSBDebugging(callbackContext);
            return true;
        } else if (action.equals("disableUSBDebugging")) {
            disableUSBDebugging(callbackContext);
            return true;
        } else if(action.equals("getDebuggingStatus")) {
            getDebuggingStatus(callbackContext);
            return true;
        }

        return false;
    }

     private int runShell(String script) {
        int result;
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(script);
            os.flush();
            os.close();
            process.waitFor();
            result = process.exitValue();
        } catch (Exception e) {
            result = 1;
        }
        return result;
    }

    private void enableUSBDebugging(CallbackContext callbackContext) {
        try {
            int result;
            result = runShell("settings put global adb_enabled 1");
            callbackContext.success(result);
        } 
         catch (Exception e) {
            callbackContext.error("Error enabling USB debugging");
        }
    }

    private void disableUSBDebugging(CallbackContext callbackContext) {
        try {
            int result;
            result = runShell("settings put global adb_enabled 0");
            callbackContext.success(result);
        } catch (Exception e) {
            callbackContext.error("Error enabling USB debugging");
        }
    } 

    private void getDebuggingStatus(CallbackContext callbackContext) {
        int result;
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("settings get global adb_enabled");
            os.flush();
            os.close();

            // Get the output stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            // Read the output
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            result = process.exitValue();
            callbackContext.success(output.toString());
        } catch (Exception e) {
            callbackContext.error("Error checking USB Status");
        }
    } 

}
