package com.hongfeiyu.memory_hfy;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by 红绯鱼 on 2015/11/5 0005.
 */


public class wave1 extends Fragment {

    private MySinkingView mSinkingView;

    private float percent = 0;
    int[] block = new int[258];
    boolean[] sfflag = new boolean[260];//默认没有被释放false
    int programnum = 0;
    int start=1;                  //当前分区开始区域


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View wave1view = inflater.inflate(R.layout.wave_layout,container,false);
        return wave1view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Intent i = new Intent();
//        i.setClass(getActivity().getApplicationContext(), MainActivity.class);
//        startActivity(i);
//        getActivity().finish();

        //setContentView(R.layout.wave_layout);
        mSinkingView = (MySinkingView) view.findViewById(R.id.sinking);

        view.findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("tag", "刷新内存执行了");
                test();
            }
        });

        view.findViewById(R.id.btn_sjfp).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("tag", "分配空间了");
                suijifenpei();
            }
        });

        view.findViewById(R.id.btn_sjsf).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("tag", "释放空间了哦");
                suijishifang();
            }
        });

        //percent = 0.56f;//直接跳转
        mSinkingView.setPercent(percent);
    }


    private void test() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                programnum=0;   //清空所有程序

                for(int i=1;i<=256;i++){
                    block[i]=0;//清除内存
                    sfflag[i]=false;
                }

                percent = 0;
                while (percent <= 1) {
                    mSinkingView.setPercent(percent);
                    percent += 0.01f;
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                while (percent >= 0.01) {
                    mSinkingView.setPercent(percent);
                    percent -= 0.01f;
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                mSinkingView.setPercent(percent);
                // mSinkingView.clear();
            }
        });
        thread.start();
    }

    private void levelto(final float newlevel) {			//变换水位
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                if (percent < newlevel) {		//水位上升
	                	while (percent <= newlevel) {
		                    mSinkingView.setPercent(percent);
		                    percent += 0.01f;
		                    try {
		                        Thread.sleep(40);
		                    } catch (InterruptedException e) {
		                        e.printStackTrace();
	                    }
	                }
                }
                else if (percent > newlevel) {
                	while (percent >= newlevel) {
	                    mSinkingView.setPercent(percent);
	                    percent -= 0.01f;
	                    try {
	                        Thread.sleep(40);
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
					}
                }
                mSinkingView.setPercent(percent);
            }
        });
        thread.start();
    }

    private float getpercent(){
    	float sum=0;
    	for(int i=1;i<=256;i++){
            if(block[i]!=0)sum++;
        }
    	return sum/(float)256;
    }

    //算法部分开始

    private void fenpei(int shenqing){                  //分配空间算法


        block[0]=1;//双端标记
        block[257]=1;

        programnum++;       //启动一个新的程序


        switch (NavigationDrawerFragment.mCurrentSelectedPosition) {            //选择算法开始
            case 1:
                first_fit(shenqing);
                break;
            case 2:
                next_fit(shenqing);
                break;
            case 3:
                best_fit(shenqing);
                break;
            case 4:
                worst_fit(shenqing);
                break;
        }

    }

    private void suijifenpei(){
        int suiji=(int)(Math.random()*100);

        Toast toast = Toast.makeText(Drawer.context,"随机到的数为"+suiji, Toast.LENGTH_SHORT);//带图片的Toast
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(Drawer.context);
        imageCodeProject.setImageResource(R.drawable.dice);
        toastView.addView(imageCodeProject, 0);
        toast.show();

    	fenpei(suiji);
        Log.e("tag", "随机" + suiji);
    }       //随机分配空间算法

    private void shifang(int xiaohui){                 //释放空间算法

        for(int i=1;i<=256;i++){                    //销毁一个特定的程序占用内存区间
            if(block[i]==xiaohui)block[i]=0;
        }

        Toast toast = Toast.makeText(Drawer.context,"成功销毁第"+xiaohui+"个程序的内存空间", Toast.LENGTH_SHORT);//带图片的Toast
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(Drawer.context);
        imageCodeProject.setImageResource(R.drawable.cgxh);
        toastView.addView(imageCodeProject, 0);
        toast.show();
                 	//申请成功

        levelto(getpercent());
    }


    private void suijishifang(){                 //随机释放空间算法
        int sfsuiji;            //释放随机数
        boolean sfover=true;

        for(int i=1;i<=programnum;i++){
            if(!sfflag[i])sfover=false;     //检查空间是否已全部释放完毕
        }
        if(sfover){
            toast_sfover();

            return;
        }

        while(true){
            sfsuiji = (int) (Math.random() * (programnum)) + 1;       //巧妙的生成随机数
            Log.e("tag", "释放随机数为" + sfsuiji);
            if(sfflag[sfsuiji])continue;
            sfflag[sfsuiji]=true;
            shifang(sfsuiji);
            return;
        }
    }

    private void first_fit(int shenqing){

        int open=1,close,length;				//空闲块位置
        boolean flag=false;
        for(int i=1;i<=257;i++){
            if((block[i-1]!=0)&&(block[i]==0)){
                open=i;		//空闲块开始
                flag=true;     //开关开启
                Log.e("tag","开始在"+open+"块");
            }
            else if(flag&&(block[i-1]==0)&&(block[i]!=0)){
                close=i;							 //结束
                flag=false;     //开关结束
                length=close-open;
                if(length<shenqing){
                    toast_yichu();
                    return;
                }
            }

            else if(flag&&i==open+shenqing){
                for (int j=open;j<open+shenqing;j++ )block[j]=programnum;       //标记为第几个程序的使用内存

                Toast toast = Toast.makeText(Drawer.context,"成功使用首次适应算法为第"+programnum+"个程序申请第"+open+"块到第"+(open+shenqing-1)+"块的内存", Toast.LENGTH_SHORT);//带图片的Toast
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastView = (LinearLayout) toast.getView();
                ImageView imageCodeProject = new ImageView(Drawer.context);
                imageCodeProject.setImageResource(R.drawable.success1);
                toastView.addView(imageCodeProject, 0);
                toast.show();
                levelto(getpercent());

                return;         	//申请成功
            }
        }
    }

    private void next_fit(int shenqing){

        int open=1,close,length;				//空闲块位置
        boolean flag=false;
        for(int i=start;i<=257;i++){
            if((block[i-1]!=0)&&(block[i]==0)){
                open=i;		//空闲块开始
                flag=true;     //开关开启
                Log.e("tag","开始在"+open+"块");
            }
            else if(flag&&(block[i-1]==0)&&(block[i]!=0)){
                close=i;							 //结束
                flag=false;     //开关结束
                length=close-open;
                if(length<shenqing){
                    continue;
                }
            }

            else if(flag&&i==open+shenqing){
                for (int j=open;j<open+shenqing;j++ )block[j]=programnum;       //标记为第几个程序的使用内存
                start=open+shenqing;            //这样当前分区后移了
                Log.e("tag","当前分区起始位置为"+start);
                Toast toast = Toast.makeText(Drawer.context,"成功使用下次适应算法为第"+programnum+"个程序申请第"+open+"块到第"+(open+shenqing-1)+"块的内存", Toast.LENGTH_SHORT);//带图片的Toast
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastView = (LinearLayout) toast.getView();
                ImageView imageCodeProject = new ImageView(Drawer.context);
                imageCodeProject.setImageResource(R.drawable.success1);
                toastView.addView(imageCodeProject, 0);
                toast.show();
                levelto(getpercent());

                return;         	//申请成功
            }
        }

        for(int i=1;i<=start;i++){
            if((block[i-1]!=0)&&(block[i]==0)){
                open=i;		//空闲块开始
                flag=true;     //开关开启
                Log.e("tag","开始在"+open+"块");
            }
            else if(flag&&(block[i-1]==0)&&(block[i]!=0)){
                close=i;							 //结束
                flag=false;     //开关结束
                length=close-open;
                if(length<shenqing){
                    continue;
                }
            }

            else if(flag&&i==open+shenqing){
                for (int j=open;j<open+shenqing;j++ )block[j]=programnum;       //标记为第几个程序的使用内存
                start=open+shenqing;            //这样当前分区后移了
                Log.e("tag","当前分区起始位置为"+start);
                Toast toast = Toast.makeText(Drawer.context,"成功为第"+programnum+"个程序申请第"+open+"块到第"+(open+shenqing-1)+"块的内存", Toast.LENGTH_SHORT);//带图片的Toast
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastView = (LinearLayout) toast.getView();
                ImageView imageCodeProject = new ImageView(Drawer.context);
                imageCodeProject.setImageResource(R.drawable.success1);
                toastView.addView(imageCodeProject, 0);
                toast.show();
                levelto(getpercent());

                return;         	//申请成功
            }
        }

        toast_yichu();
        return;

    }

    private void best_fit(int shenqing){

        int min_len=257,min_start=1;              //空闲块最小的长度及其开始的位置
        int open=1,close,length;				//空闲块位置
        boolean flag=false;
        for(int i=1;i<=257;i++){
            if((block[i-1]!=0)&&(block[i]==0)){
                open=i;		//空闲块开始
                flag=true;     //开关开启
                Log.e("tag","开始在"+open+"块");
            }
            else if(flag&&(block[i-1]==0)&&(block[i]!=0)){
                close=i;							 //结束
                flag=false;     //开关结束
                length=close-open;
                if(length<shenqing){
                    continue;
                }
                if(length<min_len){
                    min_len=length;
                    min_start=open;            //记录位置
                    Log.e("tag","这里有一个"+min_len+"块的空闲在"+min_start+"位置");
                }
            }
        }

        if(min_len==257) {
            toast_yichu();
            return;
        }
        else{
            for (int j=min_start;j<min_start+shenqing;j++ )block[j]=programnum;       //标记为第几个程序的使用内存

            Toast toast = Toast.makeText(Drawer.context,"成功使用最好适应算法为第"+programnum+"个程序申请第"+open+"块到第"+(open+shenqing-1)+"块的内存", Toast.LENGTH_SHORT);//带图片的Toast
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageCodeProject = new ImageView(Drawer.context);
            imageCodeProject.setImageResource(R.drawable.success1);
            toastView.addView(imageCodeProject, 0);
            toast.show();
            levelto(getpercent());
            Log.e("tag","百分比转为"+getpercent());
            return;         	//申请成
        }
    }

    private void worst_fit(int shenqing){
        int max_len=0,max_start=1;              //空闲块最大的长度及其开始的位置
        int open=1,close,length;				//空闲块位置
        boolean flag=false;
        for(int i=1;i<=257;i++){
            if((block[i-1]!=0)&&(block[i]==0)){
                open=i;		//空闲块开始
                flag=true;     //开关开启
                Log.e("tag","开始在"+open+"块");
            }
            else if(flag&&(block[i-1]==0)&&(block[i]!=0)){
                close=i;							 //结束
                flag=false;     //开关结束
                length=close-open;
                if(length<shenqing){
                    continue;
                }
                if(length>max_len){
                    max_len=length;
                    max_start=open;            //记录位置
                    Log.e("tag", "这里有一个" + max_len + "块的空闲在" + max_start + "位置");
                }
            }
        }

        if(max_len==0) {
            toast_yichu();
            return;
        }
        else{
            for (int j=max_start;j<max_start+shenqing;j++ )block[j]=programnum;       //标记为第几个程序的使用内存

            Toast toast = Toast.makeText(Drawer.context,"成功使用最坏适应算法为第"+programnum+"个程序申请第"+open+"块到第"+(open+shenqing-1)+"块的内存", Toast.LENGTH_SHORT);//带图片的Toast
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageCodeProject = new ImageView(Drawer.context);
            imageCodeProject.setImageResource(R.drawable.success1);
            toastView.addView(imageCodeProject, 0);
            toast.show();
            levelto(getpercent());
            Log.e("tag","百分比转为"+getpercent());
            return;         	//申请成
        }
    }

    private void toast_yichu(){
        programnum--;               //当前程序废弃掉
        Toast toast = Toast.makeText(Drawer.context,"没有更多的连续可用内存", Toast.LENGTH_LONG);//带图片的Toast
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(Drawer.context);
        imageCodeProject.setImageResource(R.drawable.error1);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    private void toast_sfover(){
        Toast toast = Toast.makeText(Drawer.context,"空间已全部释放完毕！", Toast.LENGTH_SHORT);//带图片的Toast
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(Drawer.context);
        imageCodeProject.setImageResource(R.drawable.warn);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }


}
