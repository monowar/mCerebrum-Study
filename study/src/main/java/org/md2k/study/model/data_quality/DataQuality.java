package org.md2k.study.model.data_quality;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.md2k.datakitapi.DataKitAPI;
import org.md2k.datakitapi.datatype.DataType;
import org.md2k.datakitapi.datatype.DataTypeIntArray;
import org.md2k.datakitapi.messagehandler.OnReceiveListener;
import org.md2k.datakitapi.source.datasource.DataSource;
import org.md2k.datakitapi.source.datasource.DataSourceBuilder;
import org.md2k.datakitapi.source.datasource.DataSourceClient;
import org.md2k.datakitapi.source.datasource.DataSourceType;
import org.md2k.datakitapi.source.platform.PlatformId;
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
public class DataQuality {
    private static final String TAG = DataQuality.class.getSimpleName();
    DataSource dataSource;
    DataSourceClient dataSourceClient;
    DataKitAPI dataKitAPI;
    int lastSample;
    Context context;
    String name;

    public DataQuality(Context context, DataKitAPI dataKitAPI, DataSource dataSource) {
        this.dataKitAPI = dataKitAPI;
        this.dataSource = dataSource;
        this.context = context;
        if (dataSource.getType() != null && dataSource.getType().equals(DataSourceType.RESPIRATION))
            name = "Respiration";
        if (dataSource.getType() != null && dataSource.getType().equals(DataSourceType.ECG))
            name = "ECG";
        if (dataSource.getPlatform().getId() != null && dataSource.getPlatform().getId().equals(PlatformId.LEFT_WRIST))
            name = "Wrist(L)";
        if (dataSource.getPlatform().getId() != null && dataSource.getPlatform().getId().equals(PlatformId.RIGHT_WRIST))
            name = "Wrist(R)";
    }

    public DataSource createDataSource(DataSource dataSource) {
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(dataSource);
        dataSourceBuilder.setType(DataSourceType.STATUS);
        return dataSourceBuilder.build();
    }

    int getLastSample(int[] sample) {
        if (dataSource.getPlatform().getId() != null)
            return sample[0];
        if (dataSource.getType().equals(DataSourceType.ECG))
            return sample[1];
        return sample[0];
    }

    public void start(final ReceiveCallBack receiveCallBack) {
        ArrayList<DataSourceClient> dataSourceClientArrayList = dataKitAPI.find(new DataSourceBuilder(createDataSource(dataSource)));
        Log.d(TAG, "start: platformType=" + dataSource.getPlatform().getType()+" dataSource="+dataSource.getType()+" size="+dataSourceClientArrayList.size());

        if (dataSourceClientArrayList.size() == 1) {
            dataSourceClient = dataSourceClientArrayList.get(0);
            Log.d(TAG, "id=" + dataSourceClient.getDs_id() + " platformType=" + dataSourceClient.getDataSource().getPlatform().getType());
            dataKitAPI.subscribe(dataSourceClient, new OnReceiveListener() {
                @Override
                public void onReceived(DataType dataType) {
                    Log.d(TAG, "onReceive .. id=" + dataSourceClient.getDs_id() + " platformType=" + dataSource.getPlatform().getType()+" dataSource="+dataSource.getType());
                    //lastSample = getLastSample(((DataTypeIntArray) dataType).getSample());
//                    receiveCallBack.onReceive(dataSource, lastSample);
                    receiveCallBack.onReceive(dataSource,((DataTypeIntArray) dataType).getSample());
                }
            });
        }
    }

    public void stop() {
        if (dataSourceClient != null)
            dataKitAPI.unsubscribe(dataSourceClient);
    }
    public String getName(){
        return name;
    }
}
