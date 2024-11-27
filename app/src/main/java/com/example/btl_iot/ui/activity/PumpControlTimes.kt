package com.example.btl_iot.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.btl_iot.adapter.TableAdapter
import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivityPumpControlTimesBinding

class PumpControlTimes : BaseActivity<ActivityPumpControlTimesBinding>() {
    override fun createBinding(): ActivityPumpControlTimesBinding {
        return ActivityPumpControlTimesBinding.inflate(layoutInflater)
    }

    override fun initMain() {
        initView()
    }

    private fun initView() {
        val sampleData = listOf(
            TableAdapter.RowData("1", "08:00 AM", "10:00 AM"),
            TableAdapter.RowData("2", "09:30 AM", "11:00 AM"),
            TableAdapter.RowData("3", "07:45 AM", "09:15 AM")
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = TableAdapter(sampleData)

        binding.tb.setNavigationOnClickListener {
            finish()
        }
    }
}