package com.minyan.currencycapi.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.currencycapi.service.BinlogService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @decription binlog处理
 * @author minyan.he
 * @date 2024/8/30 9:37
 */
@Service
public class BinlogServiceImpl implements BinlogService {
  public static final Logger logger = LoggerFactory.getLogger(BinlogServiceImpl.class);

  @Override
  public void handleInsert(List<Serializable[]> datas) {
    logger.info(
        "[BinlogServiceImpl][handleInsert]处理binlog数据-新增，数据请求体：{}", JSONObject.toJSONString(datas));
  }

  @Override
  public void handleUpdate(List<Map.Entry<Serializable[], Serializable[]>> datas) {
    logger.info(
        "[BinlogServiceImpl][handleUpdate]处理binlog数据-变更，数据请求体：{}", JSONObject.toJSONString(datas));
  }

  @Override
  public void handleDelete(List<Serializable[]> datas) {
    logger.info(
        "[BinlogServiceImpl][handleDelete]处理binlog数据-删除，数据请求体：{}", JSONObject.toJSONString(datas));
  }
}
