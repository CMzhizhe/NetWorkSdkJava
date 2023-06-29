package com.gxx.networkproject.mvvm.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gxx.networkproject.R
import com.gxx.networkproject.mvvm.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        val TAG = "MainActivity";
    }
    private var mMainViewModel :MainViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        this.findViewById<Button>(R.id.bt_get_list).setOnClickListener(this)
        this.findViewById<Button>(R.id.bt_get_list_second).setOnClickListener(this)
        this.findViewById<Button>(R.id.bt_get_list_second_v2).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_get_list -> {//第一个域名，测试get请求，获取集合
                mMainViewModel?.readPosts()
            }

            R.id.bt_get_list_second->{//查询快递
                mMainViewModel?.readkuaidi("yuantong","2222222")
            }

            R.id.bt_get_list_second_v2->{//customApi，自定义API
                mMainViewModel?.readCutomApiKuaiDi("yuantong","2222222")
            }
        }
    }
}