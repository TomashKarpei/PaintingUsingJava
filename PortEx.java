import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



class Port extends JPanel implements ActionListener {

    private final double pointsSea[][] = { 
        { 0, 300 }, {1280, 300},{ 1280, 720 }, {0, 720}
    };
    private final double pointsSpace[][] = { 
            { 0, 300 }, {1280, 300},{ 1280, 0 }, {0, 0}
        };
    private final int pointsShip[][] = {
            {100, 275}, {195, 275}, {195, 190}, {205,190}, {245, 200}, {205, 210}, {205, 275},
            {300, 275}, {260, 320}, {140, 320}, {100, 275}
        };
    private final int pointsShipRefl[][] = {
            {100, 635-275}, {195, 635-275}, {195, 635-190}, {205,635-190}, {245, 635-200}, {205, 635-210}, {205, 635-275},
            {300, 635-275}, {260, 635-320}, {140, 635-320}, {100,635-275}
        };
    private final int pointsRays[][] = {
    		{200, 80}, {280, 270}, {120, 270}
        };
    private final int DELAY = 150;
    private Timer timer;

    public Port() {

        initTimer();
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public Timer getTimer() {
        
        return timer;
    }
    private void doDrawing(Graphics g) {
    	
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                             RenderingHints.VALUE_RENDER_QUALITY);
        // kolor morza
        g2d.setPaint(new Color(63, 121, 186));
        // tworzenie morza
        GeneralPath sea = new GeneralPath();

        sea.moveTo(pointsSea[0][0], pointsSea[0][1]);

        for (int k = 1; k < pointsSea.length; k++)
            sea.lineTo(pointsSea[k][0], pointsSea[k][1]);

        sea.closePath();
        g2d.fill(sea);        
        
        
        g2d.setPaint(Color.BLACK);

        GeneralPath space = new GeneralPath();

        space.moveTo(pointsSpace[0][0], pointsSpace[0][1]);

        for (int k = 1; k < pointsSpace.length; k++)
            space.lineTo(pointsSpace[k][0], pointsSpace[k][1]);
        space.closePath();
        
        g2d.fill(space);       
        
        //tworzenie gwiazdek i ich odbica
        g2d.setPaint(Color.YELLOW);
        int w = getWidth();
        int h = 300;
        Random r = new Random();
        for (int i = 0; i < 150; i++) {
            int x = Math.abs(r.nextInt()) % w;
            int y = Math.abs(r.nextInt()) % h;
        	while (x >= 1000 && x <= 1200 && y >= 50 && y <= 250)
        	{
                x = Math.abs(r.nextInt()) % w;
                y = Math.abs(r.nextInt()) % h;
        	}
            g2d.drawLine(x, y, x, y);
            g2d.drawLine(x, 600-y, x, 600-y);
        }        
        //tworzenie statka
        g2d.setPaint(Color.orange);
        GeneralPath ship = new GeneralPath();
        ship.moveTo(pointsShip[0][0], pointsShip[0][1]);

        for (int k = 1; k < pointsShip.length; k++) {
            
            ship.lineTo(pointsShip[k][0], pointsShip[k][1]);
        }
        g2d.fill(ship);
        //tworzenie księzyca i jego kraterow
        g2d.setPaint(Color.WHITE);
        Area moon = new Area(new Ellipse2D.Double(1000, 50, 200, 200)); 
        g2d.fill(moon);

        
        g2d.setPaint(Color.LIGHT_GRAY);
        Area krater1 = new Area(new Ellipse2D.Double(1100, 70, 20, 20)); 
        g2d.fill(krater1);
        Area krater2 = new Area(new Ellipse2D.Double(1090, 130, 80, 80)); 
        g2d.fill(krater2);
        Area krater3 = new Area(new Ellipse2D.Double(1020, 110, 40, 40)); 
        g2d.fill(krater3);
        // rysowanie UFO
        g2d.setPaint(Color.yellow);
        Area UFO1 = new Area(new Ellipse2D.Double(180, 40, 40, 40));        
        g2d.fill(UFO1); 
        g2d.setPaint(Color.gray);
        Area UFO2 = new Area(new Ellipse2D.Double(150, 60, 100, 20));
        g2d.fill(UFO2); 
        
        
        //tworzenie odbicia księzyca i jego kraterow
        float alpha = 0.5f;
        g2d.setPaint(Color.WHITE);
        AlphaComposite reflectMoon = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        //Area reflectMoon = new Area(new Ellipse2D.Double(1000, 350, 200, 200));
        //g2d.fill(reflectMoon);
        g2d.setComposite(reflectMoon);
        g2d.fillOval(1000, 350, 200, 200);
        
        g2d.setPaint(Color.LIGHT_GRAY);
        //Area ReflectKrater1 = new Area(new Ellipse2D.Double(1100, 600-90, 20, 20)); 
        //g2d.fill(ReflectKrater1);
        AlphaComposite ReflectKrater1 = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(ReflectKrater1);
        g2d.fillOval(1100, 600-90, 20, 20);
        //Area ReflectKrater2 = new Area(new Ellipse2D.Double(1090, 600-210, 80, 80)); 
        //g2d.fill(ReflectKrater2);
        AlphaComposite ReflectKrater2 = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(ReflectKrater2);
        g2d.fillOval(1090, 600-210, 80, 80);
        //Area ReflectKrater3 = new Area(new Ellipse2D.Double(1020, 600-150, 40, 40)); 
        //g2d.fill(ReflectKrater3);
        AlphaComposite ReflectKrater3 = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(ReflectKrater3);
        g2d.fillOval(1020, 600-150, 40, 40);
        
        //tworzenie odbicia statka
        g2d.setPaint(Color.orange);
        GeneralPath shipRefl = new GeneralPath();
        shipRefl.moveTo(pointsShipRefl[0][0], pointsShipRefl[0][1]);

        for (int g1 = 1; g1 < pointsShipRefl.length; g1++) {
            
            shipRefl.lineTo(pointsShipRefl[g1][0], pointsShipRefl[g1][1]);
        }
        g2d.fill(shipRefl);
        
        g2d.setPaint(Color.blue);
        GeneralPath rays = new GeneralPath();
        rays.moveTo(pointsRays[0][0], pointsRays[0][1]);

        for (int g2 = 1; g2 < pointsRays.length; g2++) {
            
            rays.lineTo(pointsRays[g2][0], pointsRays[g2][1]);
        }
        g2d.fill(rays);
         
        
        g2d.dispose();
    }
  

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        doDrawing(g);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
		
}

public class PortEx extends JFrame {
    
    public PortEx() {

        initUI();
    }    
    
    private void initUI() {
        
        add(new Port());
        
        setTitle("Port");
        setSize(1280, 720);
        setLocationRelativeTo(null);           
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                PortEx ex = new PortEx();
                ex.setVisible(true);
            }
        });
    }    
}