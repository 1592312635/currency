package com.minyan.currencycapi.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.minyan.currencycapi.service.BinlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @decription 监听binlog实现
 * @author minyan.he
 * @date 2024/8/30 9:26
 */
@Component
public class BinlogEventListener implements BinaryLogClient.EventListener {

    @Autowired
    private BinlogService binlogService;

    @Override
    public void onEvent(Event event) {
        if (event.getData() instanceof WriteRowsEventData) {
            WriteRowsEventData data = (WriteRowsEventData) event.getData();
            binlogService.handleInsert(data.getRows());
        } else if (event.getData() instanceof UpdateRowsEventData) {
            UpdateRowsEventData data = (UpdateRowsEventData) event.getData();
            binlogService.handleUpdate(data.getRows());
        } else if (event.getData() instanceof DeleteRowsEventData) {
            DeleteRowsEventData data = (DeleteRowsEventData) event.getData();
            binlogService.handleDelete(data.getRows());
        }
    }
}
