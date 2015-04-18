package com.startingfocus.digitalpuzzle;

import java.util.ArrayList;
import java.util.Collections;


import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	Button b[]=new Button[9];       //��Ϸ��ť����
	Button start;                   //��ʼ��ť
	Button n;                       //����������ʾΪ�յİ�ť
//	OnClickListener o1=null;        //��ʼ��ť���¼�������
//	OnClickListener o2=null;        //��Ϸ��ť���¼�������
	TextView show;                  //��Ϣ��ʾ���
	int counter=0;                  //������
	int BestStep=0;
	boolean isStarted=false;
	boolean isSoundOn=true;
	private SoundPool snd;
	private int soundhit;
	private int sounderror;
	private int soundfinished;
	
	private long waitTime = 2000;
	private long touchTime = 0;
	

	 //�����˵���ť
    protected static final int MENU_Setting=Menu.FIRST;
    protected static final int MENU_About=Menu.FIRST+1;
    protected static final int MENU_Exit=Menu.FIRST+2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        //ʹ��SoundPool������Ч
        //ָ��
        snd = new SoundPool(3,AudioManager.STREAM_SYSTEM,5);
        //
        soundhit = snd.load(this,R.raw.s_hit,0);
        sounderror = snd.load(this,R.raw.s_error,0);
        soundfinished = snd.load(this,R.raw.s_finished,0);
        
        //setTitle("DigitalPuzzle");
        start=(Button)findViewById(R.id.start);      //����id��ȡ�������
        b[0]=(Button)findViewById(R.id.b0);
        b[1]=(Button)findViewById(R.id.b1);
        b[2]=(Button)findViewById(R.id.b2);
        b[3]=(Button)findViewById(R.id.b3);
        b[4]=(Button)findViewById(R.id.b4);
        b[5]=(Button)findViewById(R.id.b5);
        b[6]=(Button)findViewById(R.id.b6);
        b[7]=(Button)findViewById(R.id.b7);
        b[8]=(Button)findViewById(R.id.b8);
        n=b[8];
        show=(TextView)findViewById(R.id.show);
        
 
        start.setOnClickListener(o1);      //����ʼ��ť����¼�����
        
        for(int i=0;i<9;i++)
        {
         b[i].setOnClickListener(o2);		//��9����Ϸ��ť����¼�����
         b[i].setTextSize((float)50.0);		//���ð�ť�����С
         b[i].setTextColor(Color.rgb(0,0,255));
        }
        
       
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);


		menu.add(0, MENU_Setting, 0, "Sound On/Off");
		//.setIcon(R.drawable.menu_quit_icon);	
		menu.add(0, MENU_About, 0, "About");
		menu.add(0, MENU_Exit, 0, "Exit");
		//.setIcon(R.drawable.menu_back_icon);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		Toast t;
		if (item.getItemId() == MENU_Setting){	
			//DialogSetting.Show();
			isSoundOn = !isSoundOn;
			
			if (isSoundOn){
				t = Toast.makeText(this,"Sound On", Toast.LENGTH_SHORT);
			}
			else{
				t = Toast.makeText(this,"Sound Off", Toast.LENGTH_SHORT);
			}
			t.show();
		}
		else if (item.getItemId() == MENU_About){
			new AlertDialog.Builder(this)
			.setTitle("ABOUT")
			.setMessage("Digit Puzzle V3.2\n" +
					"Copyright (c) 2015\n\n" +
					"rdryan@sina.com\n")
			.setPositiveButton("OK",null)
			.show();
		}		
		else if (item.getItemId() == MENU_Exit){
			//onBackPressed();
			super.onBackPressed();
		}		
		return true;
	}	

     
	@Override  
	public void onBackPressed() {  
	    long currentTime = System.currentTimeMillis();  
	    if((currentTime-touchTime) >= waitTime) {  
	        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();  
	        touchTime = currentTime;  
	    }else {
	    	super.onBackPressed();  
	    }  
	}  
	
    //==========================================================
    private Button.OnClickListener o1=new Button.OnClickListener(){ //��ʼ��ť�¼�����
		public void onClick(View v)
        {   
		 ArrayList<Integer> al=new ArrayList<Integer>();    //һ������������������ʾ������      
         //int gamedata[]= new int[8];	//��ʼ������
         
         for(int i=1;i<9;i++)
         {
         	 al.add(i);                  //��1-8�����ֱ�����������
         	 //gamedata[i-1]=i;
         }
         //Collections.shuffle(al);     //����shuffle�����������������ֵ�˳��	     
		 PrepareGameData(al);			//�������gamedata����
	     
         for(int i=0;i<8;i++)
         {
        	 b[i].setText(""+al.get(i));    //����������ַ����ڸ�����Ϸ��ť��
        	 //b[i].setText(""+gamedata[i]);
         }
         
         b[8].setText("");            //���һ����ť��ʾΪ���ַ�
         n=b[8];                      //��ס�հ�ť��λ��
         counter=0;                   //����������Ϊ0
         show.setText("Step:"+counter);    //��ʾ����
         start.setText("Restart");
         isStarted = true;
        }

       };
       
       
       private Button.OnClickListener o2=new Button.OnClickListener(){
		public void onClick(View v)
    	   {
    		   Button t=(Button)v;     //��ȡ������İ�ť   
  	   		   
    		   if (!isStarted)	//�����û�㡰��ʼ�����ͷ���
    		   {
    			   ShowStart();
    			   return;
    		   }
    		   
    		   PlayHitSound();	//���Ű�����Ч
    			   
    		   switch (v.getId()){
    		   case R.id.b0://���ݲ�ͬ�İ�ť���ж���Ӧ�İ�ť
    		   {
    			   if(n==b[1]||n==b[3])
    			   {
    				   swap(t);
    			   }
    			   break;
    		   }
    		   
    		   case R.id.b1:
    		   {
    			   if(n==b[0]||n==b[2]||n==b[4])
    			   {
    				   swap(t);
    			   }
    			   break;
    		   }
         
    		   case R.id.b2:
    		   {
    			   if(n==b[1]||n==b[5])
    			   {
    				   swap(t);
    			   }
    			   break;
    		   }
    		   
    		   case R.id.b3:
    		   {
    			   if(n==b[0]||n==b[6]||n==b[4])
    			   {
    				   swap(t);
    			   }
    			   break;
    		   }
    		   
    		   case R.id.b4:
    		   {
    			   if(n==b[1]||n==b[3]||n==b[5]||n==b[7])
    			   {
    				   swap(t);
    			   }
    			   break;
    		   }
    		   
    		   case R.id.b5:
    		   {
    			   if(n==b[2]||n==b[8]||n==b[4])
    			   {
    				   swap(t);
    			   }
    			   break;
    		   }
    		   
    		   case R.id.b6:
    		   {
    			   if(n==b[3]||n==b[7])
    			   {
    				   swap(t);
    			   }
    			   break;
    		   }
    		   
    		   case R.id.b7:
    		   {
    			   if(n==b[6]||n==b[8]||n==b[4])
    			   {
    				   swap(t);
    			   }    			   
    			   break;
    		   }
    		   
    		   case R.id.b8:
    		   {
    			   if(n==b[5]||n==b[7])
    			   {
    				   swap(t);
    			   }
    			   break;
    		   } 
    		   default:
    		  }//end case
    		 
    		 //�ж��Ƿ����    		   
    		 if((b[0].getText().toString().contentEquals("1") &&
    			b[1].getText().toString().contentEquals("2") &&
    			b[2].getText().toString().contentEquals("3") &&
    			b[3].getText().toString().contentEquals("4") &&
    			b[4].getText().toString().contentEquals("5") &&
    			b[5].getText().toString().contentEquals("6") &&
    			b[6].getText().toString().contentEquals("7") &&
    			b[7].getText().toString().contentEquals("8") ) ||	//��һ������
       		 	  ( b[0].getText().toString().contentEquals("1") &&
         			b[1].getText().toString().contentEquals("2") &&
         			b[2].getText().toString().contentEquals("3") &&
         			b[5].getText().toString().contentEquals("4") &&
         			b[8].getText().toString().contentEquals("5") &&
         			b[7].getText().toString().contentEquals("6") &&
         			b[6].getText().toString().contentEquals("7") &&
         			b[3].getText().toString().contentEquals("8") )	//�ڶ�������  			
    			&& isStarted)
    		 {
    			ShowComplete();
    			start.setText("Start");
    			isStarted = false;
    		 } 
    		   
        }

       };
       
    public void ShowStart()
    {
       	Toast t = Toast.makeText(this,"Please press Start Button to start!", Toast.LENGTH_SHORT);
       	t.show();
       	
       	PlayErrorSound();
    }
           
    public void ShowComplete()
    {
    	//Toast t = Toast.makeText(this,"Finished!", Toast.LENGTH_SHORT);
    	//t.show();
    		
    	//Open BestStep    	
        SharedPreferences prefs = getSharedPreferences("myDataStor", MODE_PRIVATE);
        String SavedStep = prefs.getString("SavedStep", "");
        if (SavedStep.isEmpty()) 
        {
        	BestStep = counter;
        }
        else
        {
        	BestStep = Integer.valueOf(SavedStep).intValue();
        }
        
        
        //Play sound
    	if (isSoundOn)
    	{
    		//snd.play(soundID, leftVolume, rightVolume, priority, loop, rate);
    		snd.play(soundfinished,0.8f,0.8f,0,0,1);
    	}

    	//Show MsgBox
		new AlertDialog.Builder(this)
		.setTitle("Congratulations!")
		.setMessage("You have used "+ counter + " step to finish the game.\n\nYour best step is: " + BestStep + "\n")
		.setPositiveButton("OK",null)
		.show();
		
		
    	//Save BestStep
        if (counter < BestStep)		//save Best Step to newest
        {
        	BestStep = counter;
        }
        
    	SavedStep = String.valueOf(BestStep);
        	
        Editor mEditor = prefs.edit();
        mEditor.putString("SavedStep", SavedStep);
        mEditor.commit();
        
    }
    
    public void PlayErrorSound()
    {
       	if (isSoundOn)
       	{
       		//snd.play(soundID, leftVolume, rightVolume, priority, loop, rate);
       		snd.play(sounderror,0.8f,0.8f,0,0,1);
       	}	
    }
    
    public void PlayHitSound()
    {    
	   if (isSoundOn)
	   {
		   //snd.play(soundID, leftVolume, rightVolume, priority, loop, rate);
		   snd.play(soundhit,0.3f,0.3f,0,0,1);
	   }
    }
    
    public void swap(Button t)       //���������������ѵ���İ�ť�Ϳհ�ť���н�����ʾ������
    {
    	n.setText(t.getText());
    	t.setText("");
    	n=t;
    	show.setText("Step:"+(++counter));        
    }
    
    public void PrepareGameData(ArrayList<Integer> arrayList)
    {
    	int a[] = new int[8];
    	
    	Collections.shuffle(arrayList);     //����shuffle�����������������ֵ�˳��	
    	for (int i=0;i<8;i++)
    	{
    		a[i]= arrayList.get(i);
    	}
    	
    	while (CalcReverseNum(a,8) != 0) 
    	{
        	Collections.shuffle(arrayList);     //�ٴ�����shuffle�����������������ֵ�˳��	
        	for (int i=0;i<8;i++)
        	{
        		a[i]= arrayList.get(i);
        	}
    	}    	
    }
    
    public int CalcReverseNum(int a[], int num)	//�������е�������(��/ż����)
    {
    	int count=0;
    	int n=num;
 
    	for (int i=0;i<n;i++)
    	{
    		for(int j=i+1;j<n;j++)
    		{
    			if (a[i] > a[j])
    				count++;
    		}
    	}
    	
    	return count%2;
    }

	

}
