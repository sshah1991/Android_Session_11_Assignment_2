package com.example.sumeet.paralleldownload;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private Button start;
    ProgressBar one,two,three,four;
    MyTask task1,task2,task3,task4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=(Button)findViewById(R.id.startButton);
        one=(ProgressBar)findViewById(R.id.one_pg);
        two=(ProgressBar)findViewById(R.id.two_pg);
        three=(ProgressBar)findViewById(R.id.three_pg);
        four=(ProgressBar)findViewById(R.id.four_pg);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task1=new MyTask(one);
                StartAsyncTaskInParallel(task1);
                task2=new MyTask(two);
                StartAsyncTaskInParallel(task2);
                task3=new MyTask(three);
                StartAsyncTaskInParallel(task3);
                task4=new MyTask(four);
                StartAsyncTaskInParallel(task4);

            }
        });

    }

    private void StartAsyncTaskInParallel(MyTask task){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }

    public class MyTask extends AsyncTask<Void,Integer,Void>{

        ProgressBar progressBar;

        public MyTask(ProgressBar progressBar){
            this.progressBar=progressBar;
        }

        @Override
        protected Void doInBackground(Void... params) {
            int i=0;
            synchronized (this){
                while (i<10){
                    try {
                        wait(1500);
                        i++;
                        publishProgress(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress=values[0];
            progressBar.setProgress(progress);
            super.onProgressUpdate(values[0]);
        }
    }
}
