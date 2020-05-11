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
package org.ornl.codesample.service;

/**
 * Growth chart (height to age - age 2 ~ 20 yrs)
 * https://www.cdc.gov/growthcharts/data/zscore/statage.csv
 *
 * @author Daniel Kim
 * @since 1.8
 * @version 0.0.1-SNAPSHOT 05/10/2020
 */
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Service;

@Service
public class GrowthChartService
{

	private final String[] pert = {"3rd", "5th", "10th", "25th", "50th", "75th", "90th", "95th", "97th"};

	/**
	 * Build a PNG line chart image for growth chart.
	 *	 
	 * @param sex - Sex of child
	 * @param response - HTTP Response.
	 * @throws IOException
	 */
	public XYSeriesCollection createDataset(int sex) throws IOException 
	{
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();

		InputStream is = null; 
		BufferedReader bufferedreader = null;

		try
		{                	

			is =Thread.currentThread().getContextClassLoader().getResourceAsStream("data/statage");
			bufferedreader = new BufferedReader(new InputStreamReader(is));
			
			//Sex  Age	L	M	S	P3	P5	P10	 P15	P25	....'
			String s = bufferedreader.readLine();

			XYSeries xyseries = new XYSeries("P3", true, false);
			XYSeries xyseries1 = new XYSeries("P5", true, false);
			XYSeries xyseries2 = new XYSeries("P10", true, false);
			XYSeries xyseries3 = new XYSeries("P25", true, false);
			XYSeries xyseries4 = new XYSeries("P50", true, false);
			XYSeries xyseries5 = new XYSeries("P75", true, false);
			XYSeries xyseries6 = new XYSeries("P90", true, false);
			XYSeries xyseries7 = new XYSeries("P95", true, false);
			XYSeries xyseries8 = new XYSeries("P97", true, false);
			
			for (s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine())
			{
				//regex to detect spaces and tabs
				String[] strArr = s.split("\\s+|\\t");
				int i = 0;
				float age, f1, f2, f3, f4, f5, f6, f7, f8, f9 = 0.0f;
				
				i = Integer.parseInt(strArr[1]);//sex
				age = Float.parseFloat(strArr[2]);//age in month
				f1 = Float.parseFloat(strArr[6]);
				f2 = Float.parseFloat(strArr[7]);
				f3 = Float.parseFloat(strArr[8]);
				f4 = Float.parseFloat(strArr[9]);
				f5 = Float.parseFloat(strArr[10]);
				f6 = Float.parseFloat(strArr[11]);
				f7 = Float.parseFloat(strArr[12]);
				f8 = Float.parseFloat(strArr[13]);
				f9 = Float.parseFloat(strArr[14]);

				if (i == sex)
				{
					xyseries.add(age, f1);
					xyseries1.add(age, f2);
					xyseries2.add(age, f3);
					xyseries3.add(age, f4);
					xyseries4.add(age, f5);
					xyseries5.add(age, f6);
					xyseries6.add(age, f7);
					xyseries7.add(age, f8);
					xyseries8.add(age, f9);
				}
			}//end of for loop

			xyseriescollection.addSeries(xyseries);
			xyseriescollection.addSeries(xyseries1);
			xyseriescollection.addSeries(xyseries2);
			xyseriescollection.addSeries(xyseries3);
			xyseriescollection.addSeries(xyseries4);
			xyseriescollection.addSeries(xyseries5);
			xyseriescollection.addSeries(xyseries6);
			xyseriescollection.addSeries(xyseries7);
			xyseriescollection.addSeries(xyseries8);

			bufferedreader.close();
		}
		catch (FileNotFoundException filenotfoundexception)
		{
			System.err.println(filenotfoundexception);
		}
		catch (IOException ioexception)
		{
			System.err.println(ioexception);
		} finally {
			if(bufferedreader != null)
				bufferedreader.close();

			if(is != null)
				is.close();
		}

		return  xyseriescollection;
	}

	/**
	 * Add percentile labels for each XY plot
	 *	 
	 * @param sex - Sex of child	
	 */
	public  XYPlot getXyTextAnnotationLoc(XYPlot xyplot, int sex) {

		
		double[] labelDamain;

		//To place label for each XY plot - 240 plus some space 
		Double labelDamainLoc = 243.0D;				

		if(sex == 1)				
			labelDamain = new double[] {163.1D,  165.2D, 168.0D, 172.5D, 177.5D,  182.0D, 186.0D, 188.5D, 190.5D};
		else
			labelDamain = new double[] {151.2D, 153.0D, 155.0D, 159.0D,  163.5D, 168.0D, 172.0D, 174.0D, 176.0D};		

		XYTextAnnotation xytextannotation = null;
		Font font = new Font("SansSerif", 0, 9);

		for(int i=0; i < pert.length; i++) {
			xytextannotation = new XYTextAnnotation(pert[i], labelDamainLoc, labelDamain[i]);
			xytextannotation.setFont(font);
			xytextannotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
			xyplot.addAnnotation(xytextannotation);
		}

		return xyplot;
	}

	/**
	 * Draw range markers for domain(X-axis) and range(Y-axis)
	 *	
	 * @param xyplot - XYPlot
	 * @param ageInMonth - Age of child in month 
	 * @param height - Height of child in CM
	 */
	public  XYPlot addDomainRangeMarker(XYPlot xyplot, int ageInMonth, int height) {
		
		//Position is the value on the axis
		ValueMarker domainMarker = new ValueMarker(ageInMonth);  
		ValueMarker rangeMarker = new ValueMarker(height);
		domainMarker.setPaint(Color.red);
		rangeMarker.setPaint(Color.red);
		xyplot.addDomainMarker(domainMarker);
		xyplot.addRangeMarker(rangeMarker);

		return xyplot;
	}
}
