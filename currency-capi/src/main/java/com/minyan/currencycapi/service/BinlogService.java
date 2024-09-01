package com.minyan.currencycapi.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @decription binlog处理
 * @author minyan.he
 * @date 2024/8/30 9:37
 */
public interface BinlogService {
    void handleInsert(List<Serializable[]> datas);
    void handleUpdate(List<Map.Entry<Serializable[], Serializable[]>> datas);
    void handleDelete(List<Serializable[]> datas);
}
