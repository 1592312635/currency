package com.minyan.currencycapi.service;

import com.minyan.param.AccountQueryParam;
import com.minyan.vo.ApiResult;
import com.minyan.vo.CurrencyAccountVO;

/**
 * @decription
 * @author minyan.he
 * @date 2024/6/24 14:37
 */
public interface AccountService {
    CurrencyAccountVO getAccount(AccountQueryParam param);
}
