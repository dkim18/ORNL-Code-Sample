/*MIT License

Copyright (c) [2020] [Daniel Kim]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package org.ornl.codesample.controller.rest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.axis.NumberAxis;

import org.jfree.chart.plot.PlotOrientation;

import org.jfree.chart.plot.XYPlot;

import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.Drawable;

import org.ornl.codesample.service.CircleDrawer;
import org.ornl.codesample.service.GrowthChartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST service to create chart.
 *
 * @author Daniel Kim
 * @since 1.8
 * @version 0.0.1-SNAPSHOT 05/10/2020
 */
@RestController
@RequestMapping(path = "/chart")
public class GrowthChartRestService {

	 @Autowired
	 private GrowthChartService growthChartService;
	 
	 private final String X_AXIS_LABEL = "Age in Months"; 	 
	 private final String Y_AXIS_LABEL = "Height(cm)"; 	
	 private final String SUB_TITLE_HT_PERT = "Height-for-age percentiles: "; 	 
	 private final String SUB_TITLE_2_20_YRS = "2 to 20 years old"; 	 
	 private final String BOY = "boy";	 
	 private final String GIRL = "girl";	 

	 /**
	  * Build a PNG line chart image for growth chart.
	  *
	  * @param ageInMonth - age of child
	  * @param height - Height of child
	  * @param sex - Sex of child
	  * @param response - HTTP Response
	  * @throws IOException
	  */
    @RequestMapping(path = "/{ageInMonth}/{height}/{sex}", method = RequestMethod.GET)
    public void buildLineChart(@PathVariable("ageInMonth")  @NotBlank @Size(min=24, max=240)  int ageInMonth, 
    		@PathVariable("height") @NotBlank @Size(min=75, max = 190)   int height,  
    		@PathVariable("sex") @NotBlank @Size(min=1, max = 2)  int sex, HttpServletResponse response) throws IOException {    	    	
    	
    	//createDataset(1,  2) --> second parameter 1--> boy, 2--> girl
    	 JFreeChart jfreechart = ChartFactory.createXYLineChart(null, X_AXIS_LABEL, Y_AXIS_LABEL, growthChartService.createDataset(sex), PlotOrientation.VERTICAL, true, true, false);
    	 TextTitle textTitle = new TextTitle("Growth Charts", new Font("SansSerif", 1, 14));
    	 
    	 String sexStr = (sex == 1) ? BOY : GIRL;
    	 TextTitle subTitle = new TextTitle(SUB_TITLE_HT_PERT + sexStr + ", " + SUB_TITLE_2_20_YRS, new Font("SansSerif", 0, 11));
    	 jfreechart.addSubtitle(textTitle);
    	 jfreechart.addSubtitle(subTitle);    	     
    	 
    	 XYPlot xyplot = (XYPlot)jfreechart.getPlot();
    	 
    	 //adding domain Marker for age and range marker for height
    	 growthChartService.addDomainRangeMarker(xyplot, ageInMonth, height);
    	 
    	 NumberAxis numberaxis = (NumberAxis)xyplot.getDomainAxis();
    	 numberaxis.setUpperMargin(0.12D);//X - axix right side margin        	
    	 
    	 numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());        	
    	 NumberAxis numberaxis1 = (NumberAxis)xyplot.getRangeAxis();
    	 numberaxis1.setAutoRangeIncludesZero(false);
    	 
         xyplot = growthChartService.getXyTextAnnotationLoc(xyplot, sex);      
    	     	         	
    	 CircleDrawer cd = new CircleDrawer(Color.black, new BasicStroke(1.5f), null);
    	     					 					
    	 XYAnnotation	plot = new XYDrawableAnnotation(ageInMonth, height, 2.0, 2.0, (Drawable) cd);//3rd and 4th arguments controls size of circle
            		       		        		            	
    	 xyplot.addAnnotation(plot);

    	 final BufferedImage bufferedImage = jfreechart.createBufferedImage(800, 800);

    	 response.setContentType(MediaType.IMAGE_PNG_VALUE);
    	 ChartUtils.writeBufferedImageAsPNG(response.getOutputStream(), bufferedImage);    			
			 
    }  
}
