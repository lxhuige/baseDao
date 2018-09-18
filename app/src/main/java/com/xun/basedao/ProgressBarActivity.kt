package com.xun.basedao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_progress_bar.*

class ProgressBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar)
        ProgressBar.setOnClickListener {
            Thread(Runnable {
                while (ProgressBar.progress<ProgressBar.max){
                    ProgressBar.progress +=2
                    Thread.sleep(1*1000)
                }
                }
            ).start()


        }
    }
}
