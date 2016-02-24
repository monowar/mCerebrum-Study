package org.md2k.study.model.app_service;

import android.content.Context;

import org.md2k.datakitapi.DataKitAPI;
import org.md2k.study.Status;
import org.md2k.study.config.Application;
import org.md2k.study.config.ConfigManager;
import org.md2k.study.config.Operation;
import org.md2k.study.model.Model;
import org.md2k.utilities.Report.Log;

import java.util.ArrayList;

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
public class AppServiceManager extends Model {
    private static final String TAG = AppServiceManager.class.getSimpleName();
    public ArrayList<AppService> appServiceList;

    public AppServiceManager(Context context, DataKitAPI dataKitAPI, Operation operation) {
        super(context, dataKitAPI, operation);
        ArrayList<Application> applications= ConfigManager.getInstance(context).getConfig().getApplication();
        appServiceList=new ArrayList<>();
        for(int i=0;i<applications.size();i++){
            if(applications.get(i).getService()!=null) {
                AppService appService = new AppService(applications.get(i).getName(), applications.get(i).getPackage_name(), applications.get(i).getService());
                appServiceList.add(appService);
            }
        }
        lastStatus=new Status(Status.SUCCESS);
    }
    public int size(){
        return appServiceList.size();
    }
    public void start(){
        for(int i=0;i< appServiceList.size();i++)
            appServiceList.get(i).start(context);
    }
    public void stop(){
        for(int i=0;i< appServiceList.size();i++)
            appServiceList.get(i).stop(context);
    }
    public Status getStatus(){
        return lastStatus;
    }

    @Override
    public void reset() {
    }
}
