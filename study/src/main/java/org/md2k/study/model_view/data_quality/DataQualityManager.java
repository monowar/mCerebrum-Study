package org.md2k.study.model_view.data_quality;

import org.md2k.datakitapi.source.datasource.DataSource;
import org.md2k.datakitapi.source.datasource.DataSourceClient;
import org.md2k.study.Status;
import org.md2k.study.controller.ModelManager;
import org.md2k.study.model_view.Model;
import org.md2k.utilities.Report.Log;
import org.md2k.utilities.data_format.DATA_QUALITY;

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
public class DataQualityManager extends Model {
    private static final String TAG = DataQualityManager.class.getSimpleName();
    ArrayList<DataQuality> dataQualities;
    ArrayList<DataQualityInfo> dataQualityInfos;

    public DataQualityManager(ModelManager modelManager, String id, int rank) {
        super(modelManager, id, rank);
        Log.d(TAG, "constructor..id=" + id + " rank=" + rank);
        dataQualityInfos = new ArrayList<>();
        dataQualities = new ArrayList<>();
    }

    @Override
    public void set() {
        Log.d(TAG, "set()...");
        clear();
        dataQualities.clear();
        dataQualityInfos.clear();
        final ArrayList<DataSource> dataQuality = modelManager.getConfigManager().getConfig().getData_quality();
        if (dataQuality == null || dataQuality.size() == 0) return;
        for (int i = 0; i < dataQuality.size(); i++) {
            dataQualityInfos.add(new DataQualityInfo());
            final int finalI = i;
            dataQualities.add(new DataQuality(modelManager.getContext(), dataQuality.get(i),dataQualityInfos.get(i),  new ReceiveCallBack() {
                @Override
                public void onReceive(DataSourceClient dataSourceClient, int sample) {
                    if (dataQualityInfos == null || dataQualityInfos.size() <= finalI) return;
                    dataQualityInfos.get(finalI).set(dataSourceClient, translate(sample));
                }
            }));
        }
        for (int i = 0; i < dataQuality.size(); i++)
            dataQualities.get(i).start();
        status = new Status(rank, Status.SUCCESS);
    }

    @Override
    public void clear() {
        Log.d(TAG, "clear()...");
        status = new Status(rank, Status.NOT_DEFINED);
        if (dataQualities != null) {
            for (int i = 0; i < dataQualities.size(); i++)
                dataQualities.get(i).stop();
            dataQualities.clear();
        }
        if (dataQualityInfos != null)
            dataQualityInfos.clear();
    }

    int translate(int value) {
        switch (value) {
            case DATA_QUALITY.GOOD:
                return Status.DATAQUALITY_GOOD;
            case DATA_QUALITY.BAND_OFF:
                return Status.DATAQUALITY_OFF;
            case DATA_QUALITY.BAND_LOOSE:
                return Status.DATAQUALITY_LOOSE;
            case DATA_QUALITY.NOISE:
            case DATA_QUALITY.MISSING:
                return Status.DATAQUALITY_LOOSE;
            case DATA_QUALITY.NOT_WORN:
            case DATA_QUALITY.BAD:
                return Status.DATAQUALITY_NOT_WORN;
            default:
                return Status.DATAQUALITY_OFF;
        }
    }
}
