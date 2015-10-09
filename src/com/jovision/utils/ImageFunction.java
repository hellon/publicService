package com.jovision.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * 处理上传图片
 * 
 * @author Administrator
 * 
 */
public class ImageFunction {

	/**
	 * 图片缩放，大、小图均要
	 * 
	 * @param src
	 * @param dest
	 * @param width
	 */
	public static boolean cutImage(String src, int width,int height) {

		try { 

			
			BufferedImage srcBi = ImageIO.read(new File(src));
			
			int srcW = srcBi.getWidth();
			int srcH = srcBi.getHeight();
			
			if(srcW>width){
				
				srcW=width;
			}
			
			if(srcH>height){
				
				srcH=height;
			}
			
			ResampleOp resampleOp = new ResampleOp(DimensionConstrain
					.createMaxDimension(srcW, srcH, true));
			
			BufferedImage rescaled = resampleOp.filter(srcBi, null);

			ImageIO.write(rescaled, "png", new File(src));
			
			return Boolean.TRUE;

		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return Boolean.FALSE;
	}
	
	/**
	 * 图片缩放，大、小图均要
	 * 
	 * @param src
	 * @param dest
	 * @param width
	 */
	public static boolean cutImage(String src, String dest, int width) {

		try { 

			BufferedImage srcBi = ImageIO.read(new File(src));
			


			int srcW = srcBi.getWidth();
			int srcH = srcBi.getHeight();

			if (width < srcW) {

				int height = (width * srcH) / srcW;

				ResampleOp resampleOp = new ResampleOp(DimensionConstrain
						.createMaxDimension(width, height, true));
				BufferedImage rescaled = resampleOp.filter(srcBi, null);
				
				//调整高度
				int h=(3*width)/5;
				if(height>h){
					
					BufferedImage bi=new BufferedImage(width,h,rescaled.getType());
					Graphics g=bi.getGraphics();
					g.drawImage(rescaled, 0, 0, null);
					
					g.dispose();
					
					ImageIO.write(bi, "png", new File(dest));
					
				}else{
					
					ImageIO.write(rescaled, "png", new File(dest));
				}

				return Boolean.TRUE;
			}

		} catch (Exception e) {}

		return Boolean.FALSE;
	}
	
	/**
	 * 50*30
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean saveIconImage(String src, String dest) {
		
		try {
			
			BufferedImage srcBi = ImageIO.read(new File(src));

			int srcW = srcBi.getWidth();
			int srcH = srcBi.getHeight();
			
			int width=52;
			int height = (width * srcH) / srcW;
			    
			ResampleOp resampleOp = new ResampleOp(DimensionConstrain
					.createMaxDimension(width, height, true));
			BufferedImage rescaled = resampleOp.filter(srcBi, null);
			
			if(height>30){
				
				BufferedImage bi=new BufferedImage(50,30,rescaled.getType());
				Graphics g=bi.getGraphics();
				g.drawImage(rescaled, -1, -1, null);
				g.dispose();
				
				ImageIO.write(bi, "png", new File(dest));
				
			}else{
				
				ImageIO.write(rescaled, "png", new File(dest));
			}

			return Boolean.TRUE;
			
			
		} catch (Exception e) {}
		
		return Boolean.FALSE;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="可以发图片了，先来尝尝鲜<br><img src='http://www.cloudsee.net//upload/photo/2015/4/20/125dad14-f685-49de-b9ed-5d971420782d_small.jpg' onclick='showPhoto(\"http://www.cloudsee.net//upload/photo/2015/4/20/125dad14-f685-49de-b9ed-5d971420782d.jpg\")'>";
		s=s.replaceAll("<[\\S\\s]*?>", "");
		System.out.println(s);
	}

}
