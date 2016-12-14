package Scientific_Submission_Calculator;

import javax.swing.*;
import java.awt.*;

public class PlotFunctionPanel extends JPanel{

    int size;
    static double maxValue;

    public PlotFunctionPanel(int s, double v){
        size = s;
        maxValue = v;
        setPreferredSize(new Dimension(size, size));
    }

    public void paintComponent(Graphics g){
        g.drawLine(size/2, 0, size/2, size);
        g.drawLine(0, size/2, size, size/2);
        double[] tick = getTicks();
        int x = size/10, y = size/2, vA = 8;
        g.setFont(new Font("Sansserif", Font.PLAIN, size/30));
        for(int i = 0; i < 9; i++){
            g.drawLine(x, y+5, x, y-5);
            if(i != 4 && vA != 4){
                if(i > 4)
                    g.drawString(tick[i]+"", x-size/40, y+size/21);
                else
                    g.drawString(tick[i]+"", x-size/30, y+size/21);
                if(vA > 4)
                    g.drawString(tick[vA]+"", y-size/13, x+size/60);
                else
                    g.drawString(tick[vA]+"", y-size/12, x+size/60);
            }
            g.drawLine(y+5, x, y-5, x);
            x+=size/10;
            vA--;
        }
        g.setColor(Color.RED);
        int min = (int)Math.floor(-maxValue);
        int max = (int)Math.ceil(maxValue);
        int ratio = size / (2* max);
        int x1 = min, x2 = min;
        int y1 = x1 * x1 * x1*x1+2*x1+1, y2 = x2 * x2 * x2; // here is the equation y = x^3
        for(; x2 <= max; x2++) {
            y2 = x2 * x2 * x2*x2+2*x1+1;
            g.drawLine(size / 2 + (x1 * ratio), size / 2 - (y1 * ratio), size / 2 + (x2 * ratio), size / 2 - (y2 * ratio));
            x1 = x2;
            y1 = y2;
        }

        }

    //finds the values of the ticks on the axis e.g. -2.0, -1.5, -1.0, -0.5, 0.0, etc
    private static double[] getTicks(){
        double increment = maxValue / 5, currentTick = -1*(maxValue);
        double[] tick = new double[9];
        for(int i = 0; i < 9; i++){
            currentTick+=increment;
            tick[i] = Math.round(currentTick*100.0)/100.0;
        }
        return tick;
    }
}