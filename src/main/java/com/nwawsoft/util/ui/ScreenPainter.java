package com.nwawsoft.util.ui;

import com.nwawsoft.util.datastructures.List;
import com.nwawsoft.util.exceptions.FileNotFoundOrEmptyException;
import com.nwawsoft.util.ui.resizablerectangles.RectangleResizeMouseAdapter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;

public class ScreenPainter {
  private Rectangle[] rectangles = null;
  private final Rectangle2D r = new Rectangle2D.Double();
  RectangleResizeMouseAdapter rrma;
  private final Rectangle2D[] points;

  public ScreenPainter() {
    Window w = new Window(null) {
      @Override
      public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Rectangle2D point : points) {
          g2d.fill(point);
        }
        r.setRect(points[0].getMinX(),
            points[0].getMinY(),
            Math.abs(points[1].getMaxX() - points[0].getMinX()),
            Math.abs(points[1].getMaxY() - points[0].getMinY()));
        g2d.draw(r);
        g2d.fill(r);
        try {
          rectangles = load();
        } catch (FileNotFoundOrEmptyException e) {
          e.printStackTrace();
        }
        for (Rectangle r : rectangles) {
          g.fillRect(r.x, r.y, r.width, r.height);
        }
      }

      @Override
      public void update(Graphics g) {
        paint(g);
      }
    };
    rrma = new RectangleResizeMouseAdapter(w);
    points = rrma.getPoints();
    w.addMouseListener(rrma);
    w.addMouseMotionListener(rrma);
    w.setAlwaysOnTop(true);
    w.setBounds(w.getGraphicsConfiguration().getBounds());
    w.setBackground(new Color(0, true));
    w.setVisible(true);
  }

  private static void save(final Rectangle[] rectangles) {
    try {
      File d = new File(System.getProperty("user.home") + "/.nwju");
      if (!d.exists()) {
        if (!d.mkdir()) {
          throw new IOException();
        }
      }
      File f = new File(System.getProperty("user.home") + "/.nwju/screenpainter.ini");
      FileWriter fw = new FileWriter(f);
      BufferedWriter bw = new BufferedWriter(fw);
      for (Rectangle r : rectangles) {
        bw.write(r.x + "," + r.y + "-" + r.width + "+" + r.height + "\n");
      }
      bw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Rectangle[] load() throws FileNotFoundOrEmptyException {
    Rectangle[] rectangles = null;
    try {
      File f = new File(System.getProperty("user.home") + "/.nwju/screenpainter.ini");
      FileReader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);
      String currentLine;
      List rectangleList = new List();
      while ((currentLine = br.readLine()) != null) {
        rectangleList.append(parseRectangle(currentLine));
      }
      rectangles = toArray(rectangleList);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (rectangles == null) {
      throw new FileNotFoundOrEmptyException();
    } else {
      return rectangles;
    }
  }

  private static Rectangle[] toArray(final List rectangleList) {
    Rectangle[] rectangles = new Rectangle[rectangleList.getLength()];
    rectangleList.toFirst();
    int i = 0;
    while (rectangleList.hasAccess()) {
      rectangles[i] = (Rectangle) rectangleList.getObject();
      rectangleList.next();
      i++;
    }
    return rectangles;
  }

  private static Rectangle parseRectangle(final String rectString) {
    Rectangle r;
    int x, y, width, height;
    x = Integer.parseInt(rectString.substring(0, rectString.indexOf(",")));
    y = Integer.parseInt(rectString.substring(rectString.indexOf(",") + 1, rectString.indexOf("-")));
    width = Integer.parseInt(rectString.substring(rectString.indexOf("-") + 1, rectString.indexOf("+")));
    height = Integer.parseInt(rectString.substring(rectString.indexOf("+") + 1));
    r = new Rectangle(x, y, width, height);
    return r;
  }

  public static void main(String[] args) {
    new ScreenPainter();
  }
}