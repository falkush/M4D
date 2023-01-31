import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class M4D extends JPanel implements KeyListener, FocusListener {
	 static public char c;
	 static public boolean focus;
	 
	 static BufferedImage invcursimg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	 static Cursor invcurs = Toolkit.getDefaultToolkit().createCustomCursor(invcursimg, new Point(0, 0), "");
	 
	 static final JFrame frame = new JFrame("Press ESC to stop, click to resume");
	 static final JLabel label = new JLabel();
	 
	 static int centralx, centraly;
	 
	 static boolean holdw=false;
	 static boolean holdq=false;
	 static boolean holde=false;
	 static boolean holda=false;
	 static boolean holds=false;
	 static boolean holdd=false;
	 static boolean holdr=false;
	 static boolean holdf=false;
	 static boolean holdz=false;
	 static boolean holdx=false;
	 static boolean holdc=false;
	 static boolean holdv=false;
	 static boolean resetcm=false;
	 static boolean resettot=false;

	
	public static void main(String[] args) throws AWTException, InterruptedException {
		
		
		int height=180;
		int width=320;
		
		float dist=(float)1;
		float sqsz=(float)0.01;
		
		int i,j,k,l,pw,ph,tmp;
		
		int mult=1;
		
		int height2=height*mult;
		int width2=width*mult;
		
		byte r,g,b,a;
		
		int currentpix;
		
		focus=true;
		
		final Robot robot = new Robot();
	    
	    
	    BufferedImage image = new BufferedImage(width2,height2,BufferedImage.TYPE_4BYTE_ABGR);
	    
	    ///////////////////////////////////////////////////////////////////////////////////
		
		
		float msqsz=-sqsz;
		float multy=((float)(1-width))*sqsz/(float)2;
		float multz=((float)(height-1))*sqsz/(float)2;
		
		float[] addy = new float[4];
		float[] addz = new float[4];
		float[] vectmp = new float[4];
		float[] vecn = new float[4];
		float[][] vecl = new float[height][width];
		
			
		float[] pos = new float[4];
		float[] inv = new float[4];
		
		int[] fin = new int[4];
		
		int mousx=0;
		int mousy=0;
		
		int[] t1= {1,0,3,2,5,4,7,6,9};
		int ptmp;
		
		boolean fblock;
		
		float[] x = new float[4];
		float[] y = new float[4];
		float[] z = new float[4];
		float[] w = new float[4];
		
		float[] newx = new float[4];
		float[] newy = new float[4];
		float[] newz = new float[4];
		float[] neww = new float[4];
		
		float[] vec = new float[4];
		
		byte min;
		
		
		byte[][][] pixtmp = new byte[height][width][4];
		
		float[] coll = new float[4];
		
		boolean[][][][][] bounds = new boolean[4][11][11][11][11];
		byte[] tmpcolor = new byte[3];
		byte[][][][][] colors = new byte[11][11][11][11][3];
		int[] current = new int[4];
		int[] currentini = new int[4];
		
		int[] index = new int[4];
		int[] ngt = new int[4];
		int[] ngt2 = new int[4];
		
		float rsin = (float)Math.sin(Math.PI/32.0);
		float rcos = (float)Math.cos(Math.PI/32.0);
		
		
		x[0]=1;
		y[1]=1;
		z[2]=1;
		w[3]=1;
		
		for(i=0;i<10;i++)
		{
			for(j=0;j<10;j++)
			{
				for(k=0;k<10;k++)
				{
					for(l=0;l<10;l++)
					{
						tmpcolor[0]=(byte)(Math.random()*256);
						tmpcolor[1]=(byte)(Math.random()*256);
						tmpcolor[2]=(byte)(Math.random()*256);
						while(tmpcolor[0]>=0 && tmpcolor[1]>=0 && tmpcolor[2]>=0) {
							tmpcolor[0]=(byte)(Math.random()*256);
							tmpcolor[1]=(byte)(Math.random()*256);
							tmpcolor[2]=(byte)(Math.random()*256);
						}
						colors[i][j][k][l][0]=tmpcolor[0];
						colors[i][j][k][l][1]=tmpcolor[1];
						colors[i][j][k][l][2]=tmpcolor[2];
					}
				}
			}
		}
		
		colors[5][5][5][5][0]=(byte)255;
		colors[5][5][5][5][1]=(byte)255;
		colors[5][5][5][5][2]=(byte)255;
		
		bounds[0][5][5][5][5]=true;
		
		//
		vec[0]=1;
		
		pos[0]=(float) 5.5;
		pos[1]=(float) 5.5;
		pos[2]=(float) 5.5;
		pos[3]=(float) 5.5;
		current[0]=(int)pos[0];
		current[1]=(int)pos[1];
		current[2]=(int)pos[2];
		current[3]=(int)pos[3];
		currentini[0]=current[0];
		currentini[1]=current[1];
		currentini[2]=current[2];
		currentini[3]=current[3];
		//
		
		for(i=0;i<4;i++)
		{
			vec[i]=dist*x[i]+multy*y[i]+multz*z[i];
			addy[i]=sqsz*y[i];
			addz[i]=msqsz*z[i];
		}
		
		
		for(ph=0;ph<height;ph++)
		{
			for(i=0;i<4;i++) vectmp[i]=vec[i];
			for(pw=0;pw<width;pw++)
			{
				vecl[ph][pw]=(float)1/((float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]+vec[3]*vec[3]));
				for(i=0;i<4;i++) vec[i]+=addy[i];
			}
			for(i=0;i<4;i++) vec[i]=vectmp[i]+addz[i];
		}
	    ///////////////////////////////////////////////////////////////////////////////////
		
		for(i=1;i<=9;i++)
		{
			current[0]=5;
			current[1]=5;
			current[2]=5;
			current[3]=5;
			ptmp=8;
			for(j=0;j<i;j++)
			{
				tmp=(int)(Math.random()*8);
				while(tmp==t1[ptmp] || (tmp==0 && current[0]==9) || (tmp==1 && current[0]==0) || 
				(tmp==2 && current[1]==9) || (tmp==3 && current[1]==0) || (tmp==4 && current[2]==9) ||
				(tmp==5 && current[2]==0) || (tmp==6 && current[3]==9) || (tmp==7 && current[3]==0))
				{
					tmp=(int)(Math.random()*8);
				}
				if(tmp==0)
				{
					current[0]++;
					bounds[0][current[0]][current[1]][current[2]][current[3]]=true;
				}
				else if(tmp==1)
				{
					bounds[0][current[0]][current[1]][current[2]][current[3]]=true;
					current[0]--;
				}
				else if(tmp==2)
				{
					current[1]++;
					bounds[1][current[0]][current[1]][current[2]][current[3]]=true;
				}
				else if(tmp==3)
				{
					bounds[1][current[0]][current[1]][current[2]][current[3]]=true;
					current[1]--;
				}
				else if(tmp==4)
				{
					current[2]++;
					bounds[2][current[0]][current[1]][current[2]][current[3]]=true;
				}
				else if(tmp==5)
				{
					bounds[2][current[0]][current[1]][current[2]][current[3]]=true;
					current[2]--;
				}
				else if(tmp==6)
				{
					current[3]++;
					bounds[3][current[0]][current[1]][current[2]][current[3]]=true;
				}
				else if(tmp==7)
				{
					bounds[3][current[0]][current[1]][current[2]][current[3]]=true;
					current[3]--;
				}
				ptmp=tmp;
			}
		}
		fin[0]=current[0];
		fin[1]=current[1];
		fin[2]=current[2];
		fin[3]=current[3];
		
		
		///////////////////////////////////////////////////////////////////////////////////
	    
	    frame.setResizable(false);
	    frame.getContentPane().add(new M4D());
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    //frame.setUndecorated(true);
	    frame.setSize(width2, height2);
	    
	    frame.getContentPane().addMouseListener(new MouseAdapter() {            
	    	   @Override
	    	   public void mouseClicked(MouseEvent e) {
	    		  
	    	      focus=true;
	    	      centralx=frame.getX()+(width2/2);
	    	      centraly=frame.getY()+(height2/2);
	    	      frame.getContentPane().setCursor(invcurs);
	    	   }
	    	});
	    
	    frame.getContentPane().setCursor(invcurs);
	    frame.getContentPane().setBackground( Color.BLACK );
	    
	    final byte[] pixels =((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	 
    

       label.setIcon(new ImageIcon(image));
       frame.getContentPane().add(label,BorderLayout.CENTER);
       frame.setLocationRelativeTo(null);
       frame.pack();
       frame.setVisible(true);
	   
       centralx=frame.getX()+(width2/2);
       centraly=frame.getY()+(height2/2);
       
	    while(true)
	    {
	    	Thread.sleep(10);
	    	
	    	if(focus)
	    	{
		    		colors[fin[0]][fin[1]][fin[2]][fin[3]][0]+=10;
			    	colors[fin[0]][fin[1]][fin[2]][fin[3]][1]+=10;
			    	colors[fin[0]][fin[1]][fin[2]][fin[3]][2]+=10;
			    	if(fin[0]==currentini[0] && fin[1]==currentini[1] && fin[2]==currentini[2] && fin[3]==currentini[3])
			    	{
			    		break;
			    	}
			    	if(resetcm)
			    	{
			    		x[0]=1;
			    		x[1]=0;
			    		x[2]=0;
			    		x[3]=0;
			    		
			    		y[0]=0;
			    		y[1]=1;
			    		y[2]=0;
			    		y[3]=0;
			    		
			    		z[0]=0;
			    		z[1]=0;
			    		z[2]=1;
			    		z[3]=0;
			    		
			    		w[0]=0;
			    		w[1]=0;
			    		w[2]=0;
			    		w[3]=1;
			    		resetcm=false;
			    	}
			    	if(resettot)
			    	{
			    		x[0]=1;
			    		x[1]=0;
			    		x[2]=0;
			    		x[3]=0;
			    		
			    		y[0]=0;
			    		y[1]=1;
			    		y[2]=0;
			    		y[3]=0;
			    		
			    		z[0]=0;
			    		z[1]=0;
			    		z[2]=1;
			    		z[3]=0;
			    		
			    		w[0]=0;
			    		w[1]=0;
			    		w[2]=0;
			    		w[3]=1;
			    		
			    		pos[0]=(float) 5.5;
			    		pos[1]=(float) 5.5;
			    		pos[2]=(float) 5.5;
			    		pos[3]=(float) 5.5;
			    		resettot=false;
			    	}
		    		if(holdw)
		    		{
		    			fblock=true;
		    			for(i=0;i<4;i++)
		 				{
		 					current[i]=currentini[i];
		 					ngt[i]=(2*(Float.floatToIntBits(x[i])>>>31)-1)*-1;
		 					ngt2[i]=((Float.floatToIntBits(x[i])>>>31)+1)%2;
		 					inv[i]=1/x[i];
		 					coll[i]=inv[i]*((current[i]+ngt2[i])-pos[i]);
		 				}
		 				
		 				
		 				if(coll[0]<coll[1]) min=0;
		 				else min=1;
		 				if(coll[2]<coll[min]) min=2;
		 				if(coll[3]<coll[min]) min=3;
		 				
		 				index[0]=current[0];
		 				index[1]=current[1];
		 				index[2]=current[2];
		 				index[3]=current[3];
		 				
		 				index[min]+=ngt2[min];
		 				
		 				while(coll[min]<0.05)
		 				{
		 					if(!bounds[min][index[0]][index[1]][index[2]][index[3]])
		 					{
		 						fblock=false;
		 						coll[min]+=1.0;
		 					}
		 					else
		 					{
			 					current[min]+=ngt[min];
			 					index[min]+=ngt[min];
			 					index[min]-=ngt2[min];
			 					
			 					coll[min]+=1.0;
			 					
			 					if(coll[0]<coll[1]) min=0;
			 					else min=1;
			 					if(coll[2]<coll[min]) min=2;
			 					if(coll[3]<coll[min]) min=3;
			 					
			 					index[min]+=ngt2[min];
		 					}
		 				}
		 				
		    			if(fblock) for(i=0;i<4;i++) pos[i]+=x[i]/100.0;
		    		}
		    		if(holds)
		    		{
		    			fblock=true;
		    			for(i=0;i<4;i++)
		 				{
		 					current[i]=currentini[i];
		 					ngt[i]=2*(Float.floatToIntBits(x[i])>>>31)-1;
		 					ngt2[i]=(Float.floatToIntBits(x[i])>>>31)%2;
		 					inv[i]=-1/x[i];
		 					coll[i]=inv[i]*((current[i]+ngt2[i])-pos[i]);
		 				}
		 				
		 				
		 				if(coll[0]<coll[1]) min=0;
		 				else min=1;
		 				if(coll[2]<coll[min]) min=2;
		 				if(coll[3]<coll[min]) min=3;
		 				
		 				index[0]=current[0];
		 				index[1]=current[1];
		 				index[2]=current[2];
		 				index[3]=current[3];
		 				
		 				index[min]+=ngt2[min];
		 				
		 				while(coll[min]<0.05)
		 				{
		 					if(!bounds[min][index[0]][index[1]][index[2]][index[3]])
		 					{
		 						fblock=false;
		 						coll[min]+=1.0;
		 					}
		 					else
		 					{
			 					current[min]+=ngt[min];
			 					index[min]+=ngt[min];
			 					index[min]-=ngt2[min];
			 					
			 					coll[min]+=1.0;
			 					
			 					if(coll[0]<coll[1]) min=0;
			 					else min=1;
			 					if(coll[2]<coll[min]) min=2;
			 					if(coll[3]<coll[min]) min=3;
			 					
			 					index[min]+=ngt2[min];
		 					}
		 				}
		 				
		    			if(fblock) for(i=0;i<4;i++) pos[i]-=x[i]/100.0;
		    		}
		    		if(holda)
		    		{
		    			fblock=true;
		    			for(i=0;i<4;i++)
		 				{
		 					current[i]=currentini[i];
		 					ngt[i]=2*(Float.floatToIntBits(y[i])>>>31)-1;
		 					ngt2[i]=(Float.floatToIntBits(y[i])>>>31)%2;
		 					inv[i]=-1/y[i];
		 					coll[i]=inv[i]*((current[i]+ngt2[i])-pos[i]);
		 				}
		 				
		 				
		 				if(coll[0]<coll[1]) min=0;
		 				else min=1;
		 				if(coll[2]<coll[min]) min=2;
		 				if(coll[3]<coll[min]) min=3;
		 				
		 				index[0]=current[0];
		 				index[1]=current[1];
		 				index[2]=current[2];
		 				index[3]=current[3];
		 				
		 				index[min]+=ngt2[min];
		 				
		 				while(coll[min]<0.05)
		 				{
		 					if(!bounds[min][index[0]][index[1]][index[2]][index[3]])
		 					{
		 						fblock=false;
		 						coll[min]+=1.0;
		 					}
		 					else
		 					{
			 					current[min]+=ngt[min];
			 					index[min]+=ngt[min];
			 					index[min]-=ngt2[min];
			 					
			 					coll[min]+=1.0;
			 					
			 					if(coll[0]<coll[1]) min=0;
			 					else min=1;
			 					if(coll[2]<coll[min]) min=2;
			 					if(coll[3]<coll[min]) min=3;
			 					
			 					index[min]+=ngt2[min];
		 					}
		 				}
		    			if(fblock) for(i=0;i<4;i++) pos[i]-=y[i]/100.0;
		    		}
		    		if(holdd) 
		    		{
		    			fblock=true;
		    			for(i=0;i<4;i++)
		 				{
		 					current[i]=currentini[i];
		 					ngt[i]=(2*(Float.floatToIntBits(y[i])>>>31)-1)*-1;
		 					ngt2[i]=((Float.floatToIntBits(y[i])>>>31)+1)%2;
		 					inv[i]=1/y[i];
		 					coll[i]=inv[i]*((current[i]+ngt2[i])-pos[i]);
		 				}
		 				
		 				
		 				if(coll[0]<coll[1]) min=0;
		 				else min=1;
		 				if(coll[2]<coll[min]) min=2;
		 				if(coll[3]<coll[min]) min=3;
		 				
		 				index[0]=current[0];
		 				index[1]=current[1];
		 				index[2]=current[2];
		 				index[3]=current[3];
		 				
		 				index[min]+=ngt2[min];
		 				
		 				while(coll[min]<0.05)
		 				{
		 					if(!bounds[min][index[0]][index[1]][index[2]][index[3]])
		 					{
		 						fblock=false;
		 						coll[min]+=1.0;
		 					}
		 					else
		 					{
			 					current[min]+=ngt[min];
			 					index[min]+=ngt[min];
			 					index[min]-=ngt2[min];
			 					
			 					coll[min]+=1.0;
			 					
			 					if(coll[0]<coll[1]) min=0;
			 					else min=1;
			 					if(coll[2]<coll[min]) min=2;
			 					if(coll[3]<coll[min]) min=3;
			 					
			 					index[min]+=ngt2[min];
		 					}
		 				}
		    			
		    			if(fblock) for(i=0;i<4;i++) pos[i]+=y[i]/100.0;
		    		}
		    		if(holdr)
		    		{
		    			fblock=true;
		    			for(i=0;i<4;i++)
		 				{
		 					current[i]=currentini[i];
		 					ngt[i]=(2*(Float.floatToIntBits(z[i])>>>31)-1)*-1;
		 					ngt2[i]=((Float.floatToIntBits(z[i])>>>31)+1)%2;
		 					inv[i]=1/z[i];
		 					coll[i]=inv[i]*((current[i]+ngt2[i])-pos[i]);
		 				}
		 				
		 				
		 				if(coll[0]<coll[1]) min=0;
		 				else min=1;
		 				if(coll[2]<coll[min]) min=2;
		 				if(coll[3]<coll[min]) min=3;
		 				
		 				index[0]=current[0];
		 				index[1]=current[1];
		 				index[2]=current[2];
		 				index[3]=current[3];
		 				
		 				index[min]+=ngt2[min];
		 				
		 				while(coll[min]<0.05)
		 				{
		 					if(!bounds[min][index[0]][index[1]][index[2]][index[3]])
		 					{
		 						fblock=false;
		 						coll[min]+=1.0;
		 					}
		 					else
		 					{
			 					current[min]+=ngt[min];
			 					index[min]+=ngt[min];
			 					index[min]-=ngt2[min];
			 					
			 					coll[min]+=1.0;
			 					
			 					if(coll[0]<coll[1]) min=0;
			 					else min=1;
			 					if(coll[2]<coll[min]) min=2;
			 					if(coll[3]<coll[min]) min=3;
			 					
			 					index[min]+=ngt2[min];
		 					}
		 				}
		    			if(fblock) for(i=0;i<4;i++) pos[i]+=z[i]/100.0;
		    		}
		    		if(holdf)
		    		{
		    			fblock=true;
		    			for(i=0;i<4;i++)
		 				{
		 					current[i]=currentini[i];
		 					ngt[i]=2*(Float.floatToIntBits(z[i])>>>31)-1;
		 					ngt2[i]=(Float.floatToIntBits(z[i])>>>31)%2;
		 					inv[i]=-1/z[i];
		 					coll[i]=inv[i]*((current[i]+ngt2[i])-pos[i]);
		 				}
		 				
		 				
		 				if(coll[0]<coll[1]) min=0;
		 				else min=1;
		 				if(coll[2]<coll[min]) min=2;
		 				if(coll[3]<coll[min]) min=3;
		 				
		 				index[0]=current[0];
		 				index[1]=current[1];
		 				index[2]=current[2];
		 				index[3]=current[3];
		 				
		 				index[min]+=ngt2[min];
		 				
		 				while(coll[min]<0.05)
		 				{
		 					if(!bounds[min][index[0]][index[1]][index[2]][index[3]])
		 					{
		 						fblock=false;
		 						coll[min]+=1.0;
		 					}
		 					else
		 					{
			 					current[min]+=ngt[min];
			 					index[min]+=ngt[min];
			 					index[min]-=ngt2[min];
			 					
			 					coll[min]+=1.0;
			 					
			 					if(coll[0]<coll[1]) min=0;
			 					else min=1;
			 					if(coll[2]<coll[min]) min=2;
			 					if(coll[3]<coll[min]) min=3;
			 					
			 					index[min]+=ngt2[min];
		 					}
		 				}
		    			if(fblock) for(i=0;i<4;i++) pos[i]-=z[i]/100.0;
		    		}
		    		if(holdq)
		    		{
		    			fblock=true;
		    			for(i=0;i<4;i++)
		 				{
		 					current[i]=currentini[i];
		 					ngt[i]=2*(Float.floatToIntBits(w[i])>>>31)-1;
		 					ngt2[i]=(Float.floatToIntBits(w[i])>>>31)%2;
		 					inv[i]=-1/w[i];
		 					coll[i]=inv[i]*((current[i]+ngt2[i])-pos[i]);
		 				}
		 				
		 				
		 				if(coll[0]<coll[1]) min=0;
		 				else min=1;
		 				if(coll[2]<coll[min]) min=2;
		 				if(coll[3]<coll[min]) min=3;
		 				
		 				index[0]=current[0];
		 				index[1]=current[1];
		 				index[2]=current[2];
		 				index[3]=current[3];
		 				
		 				index[min]+=ngt2[min];
		 				
		 				while(coll[min]<0.05)
		 				{
		 					if(!bounds[min][index[0]][index[1]][index[2]][index[3]])
		 					{
		 						fblock=false;
		 						coll[min]+=1.0;
		 					}
		 					else
		 					{
			 					current[min]+=ngt[min];
			 					index[min]+=ngt[min];
			 					index[min]-=ngt2[min];
			 					
			 					coll[min]+=1.0;
			 					
			 					if(coll[0]<coll[1]) min=0;
			 					else min=1;
			 					if(coll[2]<coll[min]) min=2;
			 					if(coll[3]<coll[min]) min=3;
			 					
			 					index[min]+=ngt2[min];
		 					}
		 				}
		    			if(fblock) for(i=0;i<4;i++) pos[i]-=w[i]/100.0;
		    		}
		    		if(holde) 
		    		{
		    			fblock=true;
		    			for(i=0;i<4;i++)
		 				{
		 					current[i]=currentini[i];
		 					ngt[i]=(2*(Float.floatToIntBits(w[i])>>>31)-1)*-1;
		 					ngt2[i]=((Float.floatToIntBits(w[i])>>>31)+1)%2;
		 					inv[i]=1/w[i];
		 					coll[i]=inv[i]*((current[i]+ngt2[i])-pos[i]);
		 				}
		 				
		 				
		 				if(coll[0]<coll[1]) min=0;
		 				else min=1;
		 				if(coll[2]<coll[min]) min=2;
		 				if(coll[3]<coll[min]) min=3;
		 				
		 				index[0]=current[0];
		 				index[1]=current[1];
		 				index[2]=current[2];
		 				index[3]=current[3];
		 				
		 				index[min]+=ngt2[min];
		 				
		 				while(coll[min]<0.05)
		 				{
		 					if(!bounds[min][index[0]][index[1]][index[2]][index[3]])
		 					{
		 						fblock=false;
		 						coll[min]+=1.0;
		 					}
		 					else
		 					{
			 					current[min]+=ngt[min];
			 					index[min]+=ngt[min];
			 					index[min]-=ngt2[min];
			 					
			 					coll[min]+=1.0;
			 					
			 					if(coll[0]<coll[1]) min=0;
			 					else min=1;
			 					if(coll[2]<coll[min]) min=2;
			 					if(coll[3]<coll[min]) min=3;
			 					
			 					index[min]+=ngt2[min];
		 					}
		 				}
		    			if(fblock) for(i=0;i<4;i++) pos[i]+=w[i]/100.0;
		    		}
		    		
		    		mousx=MouseInfo.getPointerInfo().getLocation().x-centralx;
		    		mousy=MouseInfo.getPointerInfo().getLocation().y-centraly;
		    		
		    		if(mousx>0)
		    		{
		    			
		    			if(holdz || holdc || holdv || holdx)
		    			{
		    				//
		    			}
		    			else
		    			{
			    			for(i=0;i<4;i++)
			    			{
			    				newx[i]=x[i]*rcos+rsin*y[i];
			    				newy[i]=y[i]*rcos-rsin*x[i];
			    				x[i]=newx[i];
			    				y[i]=newy[i];
			    			}
		    			}
		    		}
		    		else if(mousx<0)
		    		{
		    			
		    			if(holdz || holdc || holdv || holdx)
		    			{
		    				//
		    			}
		    			else
		    			{
			    			for(i=0;i<4;i++)
			    			{
			    				newx[i]=x[i]*rcos-rsin*y[i];
			    				newy[i]=y[i]*rcos+rsin*x[i];
			    				x[i]=newx[i];
			    				y[i]=newy[i];
			    			}
		    			}
		    		}
		    		
		    		if(mousy<0)
		    		{
		    			if(holdx)
		    			{
		    				for(i=0;i<4;i++)
			    			{
			    				newy[i]=y[i]*rcos-rsin*z[i];
			    				newz[i]=z[i]*rcos+rsin*y[i];
			    				y[i]=newy[i];
			    				z[i]=newz[i];
			    			}
		    			}
		    			if(holdz)
		    			{
		    				for(i=0;i<4;i++)
			    			{
			    				newx[i]=x[i]*rcos+rsin*w[i];
			    				neww[i]=w[i]*rcos-rsin*x[i];
			    				x[i]=newx[i];
			    				w[i]=neww[i];
			    			}
		    			}
		    			else if(holdv)
		    			{
		    				for(i=0;i<4;i++)
			    			{
			    				newz[i]=z[i]*rcos+rsin*w[i];
			    				neww[i]=w[i]*rcos-rsin*z[i];
			    				z[i]=newz[i];
			    				w[i]=neww[i];
			    			}
		    			}
		    			else if(holdc)
		    			{
		    				for(i=0;i<4;i++)
			    			{
			    				newy[i]=y[i]*rcos-rsin*w[i];
			    				neww[i]=w[i]*rcos+rsin*y[i];
			    				y[i]=newy[i];
			    				w[i]=neww[i];
			    			}
		    			}
		    			else
		    			{
			    			for(i=0;i<4;i++)
			    			{
			    				newx[i]=x[i]*rcos+rsin*z[i];
			    				newz[i]=z[i]*rcos-rsin*x[i];
			    				x[i]=newx[i];
			    				z[i]=newz[i];
			    			}
		    			}
		    		}
		    		else if(mousy>0)
		    		{
		    			if(holdx)
		    			{
		    				for(i=0;i<4;i++)
			    			{
			    				newy[i]=y[i]*rcos+rsin*z[i];
			    				newz[i]=z[i]*rcos-rsin*y[i];
			    				y[i]=newy[i];
			    				z[i]=newz[i];
			    			}
		    			}
		    			if(holdz)
		    			{
		    				for(i=0;i<4;i++)
			    			{
			    				newx[i]=x[i]*rcos-rsin*w[i];
			    				neww[i]=w[i]*rcos+rsin*x[i];
			    				x[i]=newx[i];
			    				w[i]=neww[i];
			    			}
		    			}
		    			else if(holdv)
		    			{
		    				for(i=0;i<4;i++)
			    			{
			    				newz[i]=z[i]*rcos-rsin*w[i];
			    				neww[i]=w[i]*rcos+rsin*z[i];
			    				z[i]=newz[i];
			    				w[i]=neww[i];
			    			}
		    			}
		    			else if(holdc)
		    			{
		    				for(i=0;i<4;i++)
			    			{
			    				newy[i]=y[i]*rcos+rsin*w[i];
			    				neww[i]=w[i]*rcos-rsin*y[i];
			    				y[i]=newy[i];
			    				w[i]=neww[i];
			    			}
		    			}
		    			else
		    			{
			    			for(i=0;i<4;i++)
			    			{
			    				newx[i]=x[i]*rcos-rsin*z[i];
			    				newz[i]=z[i]*rcos+rsin*x[i];
			    				x[i]=newx[i];
			    				z[i]=newz[i];
			    			}
		    			}
		    		}
			    	 
			    	currentpix=0;
			    	for(i=0;i<4;i++)
			    	{
			    		vec[i]=dist*x[i]+multy*y[i]+multz*z[i];
			    		addy[i]=sqsz*y[i];
			    		addz[i]=msqsz*z[i];
			    	}
			    	
			    	current[0]=(int)pos[0];
					current[1]=(int)pos[1];
					current[2]=(int)pos[2];
					current[3]=(int)pos[3];
					currentini[0]=current[0];
					currentini[1]=current[1];
					currentini[2]=current[2];
					currentini[3]=current[3];
			    	
			 	    for(ph=0;ph<height;ph++)
			 	    {
			 	    	for(i=0;i<4;i++) vectmp[i]=vec[i];
			 	    	for(pw=0;pw<width;pw++)
			 	    	{
			 	    		for(i=0;i<4;i++)
			 				{
			 					vecn[i]=vec[i]*vecl[ph][pw];
			 					current[i]=currentini[i];
			 					ngt[i]=(2*(Float.floatToIntBits(vecn[i])>>>31)-1)*-1;
			 					ngt2[i]=((Float.floatToIntBits(vecn[i])>>>31)+1)%2;
			 					inv[i]=1/vecn[i];
			 					coll[i]=inv[i]*((current[i]+ngt2[i])-pos[i]);
			 					inv[i]*=ngt[i];
			 				}
			 				
			 				
			 				if(coll[0]<coll[1]) min=0;
			 				else min=1;
			 				if(coll[2]<coll[min]) min=2;
			 				if(coll[3]<coll[min]) min=3;
			 				
			 				index[0]=current[0];
			 				index[1]=current[1];
			 				index[2]=current[2];
			 				index[3]=current[3];
			 				
			 				index[min]+=ngt2[min];
			 				
			 				while(bounds[min][index[0]][index[1]][index[2]][index[3]])
			 				{
			 					current[min]+=ngt[min];
			 					index[min]+=ngt[min];
			 					index[min]-=ngt2[min];
			 					
			 					coll[min]+=inv[min];
			 					
			 					if(coll[0]<coll[1]) min=0;
			 					else min=1;
			 					if(coll[2]<coll[min]) min=2;
			 					if(coll[3]<coll[min]) min=3;
			 					
			 					index[min]+=ngt2[min];
			 				}
			 	    		
			 				//no upscale
			 	    		if(coll[min]>2.55) pixels[currentpix]=(byte)0;
			 	    		else pixels[currentpix]=(byte)(255-100*coll[min]);
			 	    		currentpix++;
			 	    		pixels[currentpix]=colors[current[0]][current[1]][current[2]][current[3]][0];
			 	    		currentpix++;
			 	    		pixels[currentpix]=colors[current[0]][current[1]][current[2]][current[3]][1];
			 	    		currentpix++;
			 	    		pixels[currentpix]=colors[current[0]][current[1]][current[2]][current[3]][2];
			 	    		currentpix++;
			 				
			 				/*
			 				if(coll[min]>2.55) pixtmp[ph][pw][0]=(byte)0;
			 	    		else pixtmp[ph][pw][0]=(byte)(255-100*coll[min]);
			 				pixtmp[ph][pw][1]=colors[current[0]][current[1]][current[2]][current[3]][0];
			 				pixtmp[ph][pw][2]=colors[current[0]][current[1]][current[2]][current[3]][1];
			 				pixtmp[ph][pw][3]=colors[current[0]][current[1]][current[2]][current[3]][2];
			 				*/
			 	    		
			 	    		
			 	    		for(i=0;i<4;i++) vec[i]+=addy[i];
			 	    	}
			 	    	
			 	    	for(i=0;i<4;i++) vec[i]=vectmp[i]+addz[i];
			 	    }
			 	    /*
			 	    for(ph=0;ph<height;ph++)
			 	    {
				 	    for(j=0;j<mult;j++)
				 	    {
					 	    for(pw=0;pw<width;pw++)	
					 	    {
					 	    	for(i=0;i<mult;i++)
					 	    	{
					 	    		pixels[currentpix]=pixtmp[ph][pw][0];
					 	    		currentpix++;
					 	    		pixels[currentpix]=pixtmp[ph][pw][1];
					 	    		currentpix++;
					 	    		pixels[currentpix]=pixtmp[ph][pw][2];
					 	    		currentpix++;
					 	    		pixels[currentpix]=pixtmp[ph][pw][3];
					 	    		currentpix++;
					 	    	}
					 	    }
				 	    }
			 	    }*/

			       label.setIcon(new ImageIcon(image));
			       frame.getContentPane().add(label,BorderLayout.CENTER);
			       robot.mouseMove(centralx,centraly);
	    	}
	    }
	    JOptionPane.showMessageDialog(null, "Congratulations!", "Maze 4D", JOptionPane.INFORMATION_MESSAGE);
	    System.exit(0);
	}
	
	@Override
	public void focusLost(FocusEvent e) {
        focus=false;
        frame.getContentPane().setCursor(Cursor.getDefaultCursor());
    }
	
	public M4D() {
        addKeyListener(this);
        addFocusListener(this);
    }
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }
	
	@Override
	public void keyPressed(KeyEvent e) { }
	@Override
	public void keyReleased(KeyEvent e) {
		c = e.getKeyChar();
		if(c==119) {holdw=false;}
		else if(c==113) {holdq=false;}
		else if(c==101) {holde=false;}
		else if(c==97) {holda=false;}
		else if(c==115) {holds=false;}
		else if(c==100) {holdd=false;}
		else if(c==114) {holdr=false;}
		else if(c==102) {holdf=false;}
		else if(c==122) {holdz=false;}
		else if(c==120) {holdx=false;}
		else if(c==99) {holdc=false;}
		else if(c==118) {holdv=false;}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		c = e.getKeyChar();
		if(c==27) { focus=false;frame.getContentPane().setCursor(Cursor.getDefaultCursor());}
		else if(c==119) {holdw=true;}
		else if(c==113) {holdq=true;}
		else if(c==101) {holde=true;}
		else if(c==97) {holda=true;}
		else if(c==115) {holds=true;}
		else if(c==100) {holdd=true;}
		else if(c==114) {holdr=true;}
		else if(c==102) {holdf=true;}
		else if(c==116) {resetcm=true;}
		else if(c==103) {resettot=true;}
		else if(c==122) {holdz=true;}
		else if(c==120) {holdx=true;}
		else if(c==99) {holdc=true;}
		else if(c==118) {holdv=true;}
	}

	@Override
	public void focusGained(FocusEvent arg0) {}
}
