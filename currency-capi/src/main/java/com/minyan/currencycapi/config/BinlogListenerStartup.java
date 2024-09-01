package com.minyan.currencycapi.config;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.minyan.currencycapi.listener.BinlogEventListener;
import java.util.Objects;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @decription
 * @author minyan.he
 * @date 2024/8/30 9:30
 */
@Component
public class BinlogListenerStartup implements ApplicationListener<ApplicationReadyEvent> {

  @Value("${binlog.client.host}")
  private String binlogHost;

  @Value("${binlog.client.port}")
  private Integer binlogPort;

  @Value("${binlog.client.username}")
  private String binlogUsername;

  @Value("${binlog.client.password}")
  private String binlogPassword;

  @Value("${binlog.client.serverId}")
  private String binlogServerId;

  @Autowired private BinlogEventListener binlogEventListener;

  @SneakyThrows
  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    BinaryLogClient client =
        new BinaryLogClient(binlogHost, binlogPort, binlogUsername, binlogPassword);
    client.setServerId(Long.parseLong(Objects.requireNonNull(binlogServerId)));
    client.registerEventListener(binlogEventListener);
    client.connect();
  }
}
