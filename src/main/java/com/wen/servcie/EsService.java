package com.wen.servcie;

import com.wen.pojo.MyFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface EsService {
    boolean addData(List<MyFile> list);

    boolean addData(MyFile file);

    boolean updateData(MyFile file);

    boolean delDate(String id);

    List<Map<String, Object>> searchData(int storeId, String keyword) throws IOException;

    boolean esWarmUp();
}
