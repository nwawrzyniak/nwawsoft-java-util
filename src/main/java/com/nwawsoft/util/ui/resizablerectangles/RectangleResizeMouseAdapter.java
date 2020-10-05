package com.nwawsoft.util.ui.resizablerectangles;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class RectangleResizeMouseAdapter extends MouseAdapter {
  private final int CORNER_SIZE = 8;
  private final int MIN_SIZE = CORNER_SIZE * 2;
  private int pos = -1;
  private final Window w;
  private final Rectangle2D[] points = {
      new Rectangle2D.Double(150, 50, CORNER_SIZE, CORNER_SIZE),
      new Rectangle2D.Double(250, 100, CORNER_SIZE, CORNER_SIZE)
  };

  public RectangleResizeMouseAdapter(final Window w) {
    this.w = w;
  }

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
    w.repaint();
  }

  public Rectangle2D[] getPoints() {
    return points;
  }

  public Window getWindow() {
    return w;
  }
}
