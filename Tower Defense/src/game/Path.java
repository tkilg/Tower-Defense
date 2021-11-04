package game;

import java.util.Scanner;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;

public class Path 
{

	private int[] pathY = new int[126];
	private int[] pathX = new int[126];
  private double totalSegmentDistance;

	public Path (Scanner s)
    {
		
		int[] pathX = new int[126];
		    
		    
		int[] pathY = new int[126];
	    	
	    for (int i = 0; i < 126; i++) {
	        this.pathX[i] = s.nextInt();
	        this.pathY[i] = s.nextInt(); 
	    }
	    

  
       
          
    }
	 /** This constructor does the following:  
     *     - It reads a number of coordinates, n, from the scanner. 
     *     - It creates new array(s) (or an ArrayList) to hold the path coordinates,
     *          and stores it in the field in 'this' object.
     *     - It loops n times, each time scanning a coordinate x,y pair, creating an
     *         object to represent the coordinate (if needed), and stores the coordinate
     *         in the array(s) or ArrayList.
     * 
     * @param s  a Scanner set up by the caller to provide a list of coordinates
     */

    /**
      * Draws the current path to the screen (to wherever the graphics object points)
      * using a highly-visible color.
      * 
      * @param g   a graphics object
      */  
	public void draw(Graphics g) 
	{

	        for (int i = 0; i < 125; i++)
	        {
	          g.drawLine(this.pathX[i], this.pathY[i], this.pathX[i + 1], this.pathY[i + 1]);
	        }

	}

	/** 
	 * Returns the total length of the path. Since the path
	 * is specified using screen coordinates, the length is
	 * in pixel units (by default).
	 * 
	 * @return the length of the path
	 */
  public double getPathLength()
  {
    totalSegmentDistance = 0.0;

      
    
      for (int x = 0; x < this.pathX.length - 1; x++)
      {
          double segmentDistance = Math.sqrt( Math.pow( (pathX[x + 1] - pathX[x]), 2) + Math.pow ( (pathY[x + 1] - pathY[x]), 2));
          totalSegmentDistance += segmentDistance;
      }
      //Math.pow(2, 3) // 8.0
      return totalSegmentDistance;
  }

  public Point getPathPostion(double percentTraveled)
  {
      if (percentTraveled >= 1.0)
          return new Point (pathX[125], pathY[125]);
      else if (percentTraveled <= 0.0)
          return new Point (pathX[0], pathY[0]);

      double pointPositionLength = totalSegmentDistance * percentTraveled;

      double[] segmentLengths = new double[125];
      
      for (int x = 0; x < 125; x++)
      {
        segmentLengths[x] = Math.sqrt( Math.pow( (pathX[x + 1] - pathX[x]), 2) + Math.pow ( (pathY[x + 1] - pathY[x]), 2));

      }
      
      
      Point startSegmentPoint = new Point (pathX[0], pathY[0]);
      Point endSegmentPoint = new Point (pathX[1], pathY[1]);
      double foundSegmentLength = 0;

      //for loop, if segment has been found: break
      SegmentFinder: for (int x = 0; x < segmentLengths.length; x++)
      {
          if (pointPositionLength - segmentLengths[x] < 0)
          {
            foundSegmentLength = Math.sqrt( Math.pow( (pathX[x + 1] - pathX[x]), 2) + Math.pow ( (pathY[x + 1] - pathY[x]), 2));
            break SegmentFinder;
          }
            

          pointPositionLength -= segmentLengths[x];
          startSegmentPoint = new Point (pathX[x], pathY[x]);
          endSegmentPoint = new Point (pathX[x + 1], pathY[x + 1]);
      }


      double percentAcrossSegment = pointPositionLength / foundSegmentLength;

      int xValueOfNewPoint = (int)(startSegmentPoint.x * (1 - percentAcrossSegment) + endSegmentPoint.x * (percentAcrossSegment));
      int yValueOfNewPoint = (int)( (startSegmentPoint.y * (1 - percentAcrossSegment) + endSegmentPoint.y * (percentAcrossSegment)));
      
      Point newPointPosition = new Point (xValueOfNewPoint, yValueOfNewPoint);
      
      return newPointPosition;
  }
      
}
