/**
 * 
 */
package com.server.data;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author lenovo
 *
 */
public class LoadImages {

	public LoadImages() {
		// TODO Auto-generated constructor stub
	}

    public Image loadImage(String name) {
	       /* String filename = "image/res/" + name;
	       // return new ImageIcon(filename).getImage();
	        * 
	        */
    	//String str =
    	File file= new File("image/Chat/"+name+".png");
    	Image img=null;
    	try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return img;
	}
    public ImageIcon LoadImageIcon(String name){
    
        ImageIcon icon = new ImageIcon(loadImage(name));
		return icon;
    }
}
 
