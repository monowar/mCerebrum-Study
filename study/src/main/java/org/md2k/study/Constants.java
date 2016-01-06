package org.md2k.study;

import android.content.Context;
import android.os.Environment;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p/>
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p/>
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class Constants{
    public static String FILENAME_APPINFO="app_info.json";
    public static String FILENAME_INSTALL="install.json";
    public static String FILENAME_SETTINGS="settings.json";

    public static String FILENAME_DEVICEINFO="device_info.json";
    public static String FILENAME_SENSORINFO="sensor_info.json";
    public static String PASSWORD="1234";
    public static String CONFIG_DIRECTORY= Environment.getExternalStorageDirectory().getAbsolutePath() + "/mCerebrum/config/";
    public static final String DEFAULT_FILENAME_PHONESENSOR = "default_config_phonesensor.json";

    public static String getInstallPath(Context context) {
        return Environment.getExternalStorageDirectory() + "/Android/data/" +context.getPackageName()+"/temp.apk";
    }
    public static String getInstallDir(Context context) {
        return Environment.getExternalStorageDirectory() + "/Android/data/" +context.getPackageName()+"/";
    }
    public static final long MISSING_MILLIS=5000;
}
