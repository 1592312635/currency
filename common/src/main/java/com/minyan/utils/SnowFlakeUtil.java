package com.minyan.utils;

import cn.hutool.core.lang.Singleton;

/**
 * @decription 雪花算法工具类
 * @author minyan.he
 * @date 2024/9/10 22:12
 */
public class SnowFlakeUtil {
  private static final long START_STMP = 1420041600000L;
  private static final long SEQUENCE_BIT = 9L;
  private static final long MACHINE_BIT = 2L;
  private static final long DATACENTER_BIT = 2L;
  private static final long MAX_SEQUENCE = 511L;
  private static final long MAX_MACHINE_NUM = 3L;
  private static final long MAX_DATACENTER_NUM = 3L;
  private static final long MACHINE_LEFT = 9L;
  private static final long DATACENTER_LEFT = 11L;
  private static final long TIMESTMP_LEFT = 13L;

  private long datacenterId;
  private long machineId;
  private long sequence = 0L;
  private long lastStmp = -1L;

  public SnowFlakeUtil(long datacenterId, long machineId) {
    if (datacenterId <= MAX_DATACENTER_NUM && datacenterId >= 0L) {
      if (machineId <= MAX_MACHINE_NUM && machineId >= 0L) {
        this.datacenterId = datacenterId;
        this.machineId = machineId;
      } else {
        throw new IllegalArgumentException(
            "machineId can't be greater than MAX_MACHINE_NUM or less than 0");
      }
    } else {
      throw new IllegalArgumentException(
          "datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
    }
  }

  public synchronized long nextId() {
    long currStmp = getNewstmp();
    if (currStmp < lastStmp) {
      throw new RuntimeException("Clock moved backwards. Refusing to generate id");
    } else {
      if (currStmp == lastStmp) {
        sequence = (sequence + 1L) & MAX_SEQUENCE;
        if (sequence == 0L) {
          currStmp = getNextMill();
        }
      } else {
        sequence = 0L;
      }
      lastStmp = currStmp;
      return (currStmp - START_STMP) << TIMESTMP_LEFT
          | datacenterId << DATACENTER_LEFT
          | machineId << MACHINE_LEFT
          | sequence;
    }
  }

  private long getNextMill() {
    long mill;
    for (mill = getNewstmp(); mill <= lastStmp; mill = getNewstmp()) {}
    return mill;
  }

  private long getNewstmp() {
    return System.currentTimeMillis();
  }

  public static Long getDefaultSnowFlakeId() {
    return ((SnowFlakeUtil) Singleton.get(SnowFlakeUtil.class, new Object[] {1L, 1L})).nextId();
  }

  public static void main(String[] args) {
    for (int i = 0; i < 10; ++i) {
      System.out.println(getDefaultSnowFlakeId());
      System.out.println(getDefaultSnowFlakeId().toString().length());
    }
  }
}
