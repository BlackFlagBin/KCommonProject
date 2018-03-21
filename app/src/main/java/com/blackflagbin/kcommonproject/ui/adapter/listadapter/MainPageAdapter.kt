package com.blackflagbin.kcommonproject.ui.adapter.listadapter

import com.blackflagbin.kcommonproject.R
import com.blackflagbin.kcommonproject.common.entity.net.DataItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MainPageAdapter(
        data: List<DataItem>?) :
        BaseQuickAdapter<DataItem, BaseViewHolder>(R.layout.item_list_data, data) {

    override fun convert(
            helper: BaseViewHolder, item: DataItem) {
        helper.setText(R.id.tv_name, item.desc)
    }
}