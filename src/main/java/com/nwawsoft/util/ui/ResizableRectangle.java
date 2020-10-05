package com.nwawsoft.util.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class ResizableRectangle extends JPanel {
  private final int CORNER_SIZE = 8;
  private final int MIN_SIZE = CORNER_SIZE * 2;
  private Rectangle2D[] points = {
      new Rectangle2D.Double(150, 50, CORNER_SIZE, CORNER_SIZE),
      new Rectangle2D.Double(250, 100, CORNER_SIZE, CORNER_SIZE)
  };
  Rectangle2D s = new Rectangle2D.Double();
  ShapeResizeHandler ada = new ShapeResizeHandler();

  public ResizableRectangle() {
    addMouseListener(ada);
    addMouseMotionListener(ada);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    for (Rectangle2D point : points) {
      g2.fill(point);
    }
    s.setRect(points[0].getMinX(),
        points[0].getMinY(),
        Math.abs(points[1].getMaxX() - points[0].getMinX()),
        Math.abs(points[1].getMaxY() - points[0].getMinY()));
    g2.draw(s);
    //g2.fill(s);
  }

  private class ShapeResizeHandler extends MouseAdapter {
    private int pos = -1;

    public void mousePressed(MouseEvent event) {
      Point p = event.getPoint();
      for (int i = 0; i < points.length; i++) {
        if (points[i].contains(p)) {
          pos = i;
          return;
        }
      }
    }

    public void mouseReleased(MouseEvent event) {
      pos = -1;
    }

    public void mouseDragged(MouseEvent event) {
      if (pos == -1) {
        return;
      }
      points[pos].setRect(event.getPoint().x, event.getPoint().y, points[pos].getWidth(), points[pos].getHeight());
      repaint();
    }
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Resize Rectangle");
    frame.add(new ResizableRectangle());
    frame.setSize(300, 300);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}

