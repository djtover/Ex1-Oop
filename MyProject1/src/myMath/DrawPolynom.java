package myMath;
import java.awt.Color;
import javax.swing.JFrame;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.data.DataSeries;
import java.awt.geom.Ellipse2D;
import java.awt.BorderLayout;

public class DrawPolynom extends JFrame {
	private Polynom_able p;
	private double x0;
	private double x1;
	private double eps;



	public DrawPolynom(Polynom_able p1, double left, double right,double Eps) {
		this.p= p1;
		this.x0=left;
		this.x1=right;
		this.eps=Eps;
		DataTable data = new DataTable(Double.class, Double.class);

		for (double x = x0; x <= x1; x+=0.01) {
			double y = p.f(x);
			data.add(x, y);   
		}
		XYPlot plot = new XYPlot(data);
		getContentPane().add(new InteractivePanel(plot));




		DataTable dataEx = new DataTable(Double.class, Double.class);
		double prev,current,next;
		for (double x = x0; x <= x1; x+=eps) {
			prev=p.f(x-eps);
			current=p.f(x);
			next=p.f(x+eps);
			if((current>prev && current>next) || (current<prev && current<next)) {
				dataEx.add(x, current);   
			}
		}

		DataSeries series1 = new DataSeries("Series 1", dataEx, 0, 1);
		//        XYPlot plotEx = new XYPlot(series1);
		plot.add(series1);
		PointRenderer points1 = new DefaultPointRenderer2D();
		points1.setShape(new Ellipse2D.Double(-5, -5, 10,10));
		points1.setColor(new Color(1.0f, 0.0f, 0.0f, 1.0f));

		plot.setPointRenderers(series1, points1);

		getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(getContentPane().getMinimumSize());
		setSize(1000, 700);

		System.out.println(underArea(p1,left,right,Eps));


	}
	public double underArea(Polynom_able p,double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		double sum=0; 
		double max=0;
		for(double i=x0;i<x1;i=i+eps) {
			if(p.f(i)<0 && p.f(i+eps)<0) {
				max=Double.max(p.f(i), p.f(i+eps))*-1;
				sum=sum+(max*eps);
			}
		}
		return sum;

	}


}
