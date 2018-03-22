package com.blackflagbin.kcommonproject.mvp.model

import com.blackflagbin.kcommon.base.BaseModel
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService
import com.blackflagbin.kcommonproject.mvp.contract.WebContract

class WebModel : BaseModel<ApiService, CacheService>(), WebContract.IWebModel