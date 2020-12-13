import de.erichseifert.vectorgraphics2d.Document;
import de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import de.erichseifert.vectorgraphics2d.intermediate.CommandSequence;
import de.erichseifert.vectorgraphics2d.pdf.PDFProcessor;
import de.erichseifert.vectorgraphics2d.util.PageSize;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int CIRCLE_DIAMETRE = 4;

    public static void main(String[] args) throws IOException {
        List<Ball> balls = new ArrayList<>();
        Reader in = new FileReader("src/main/resources/positionData.csv");
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
        for (CSVRecord record : records) {
            int id =    Integer.parseInt(record.get("ID"));
            int x =     Integer.parseInt(record.get("X"));
            int y =     Integer.parseInt(record.get("Y"));
            int z =     Integer.parseInt(record.get("Z"));
            int mid =   Integer.parseInt(record.get("MID"));
            Ball ball = new Ball(id, x, y, z, mid);
            balls.add(ball);
            System.out.println(ball);
        }
        generatePDF("output1.pdf", 650, 650, balls);
        generatePDF("output2.pdf", 400, 650, balls);
    }

    private static void generatePDF(String filename, int deltaX, int deltaY, List<Ball> balls) throws IOException {
        VectorGraphics2D vg2d = new VectorGraphics2D();

        vg2d.setStroke(new BasicStroke(2));
        vg2d.draw(new Line2D.Double(-650 + deltaX, 0 + deltaY, 650 + deltaX, 0 + deltaY));
        vg2d.draw(new Line2D.Double(0 + deltaX, -650 + deltaY, 0 + deltaX, 650 + deltaY));
        vg2d.setStroke(new BasicStroke(1));
        for(int i = -600; i < 650; i += 100){
            vg2d.draw(new Line2D.Double(i + deltaX, -650 + deltaY, i + deltaX, 650 + deltaY));
            vg2d.draw(new Line2D.Double(-650 + deltaX, i + deltaY, 650 + deltaX, i + deltaY));
        }
        vg2d.setFont(new Font("Arial", Font.PLAIN, 6));
        for(Ball ball : balls){
            double pdfX = ball.getX() + deltaX - CIRCLE_DIAMETRE/2;
            double pdfY = ball.getY() + deltaY - CIRCLE_DIAMETRE/2;
            vg2d.draw(new Ellipse2D.Double(pdfX, pdfY, CIRCLE_DIAMETRE, CIRCLE_DIAMETRE));
            vg2d.drawString("ID: " + ball.getId(), (int)pdfX, (int)pdfY - 37);
            vg2d.drawString("X: " + ball.getX(), (int)pdfX, (int)pdfY - 29);
            vg2d.drawString("Y: " + ball.getY(), (int)pdfX, (int)pdfY - 21);
            vg2d.drawString("Z: " + ball.getZ(), (int)pdfX, (int)pdfY - 13);
            vg2d.drawString("Mid: " + ball.getMid(), (int)pdfX, (int)pdfY - 5);
        }
        vg2d.draw(new Ellipse2D.Double(deltaX - 350, deltaY - 350, 700, 700));

        CommandSequence commands = ((VectorGraphics2D) vg2d).getCommands();
        PDFProcessor pdfProcessor = new PDFProcessor(true);
        Document doc = pdfProcessor.getDocument(commands, new PageSize(900., 1300.));
        doc.writeTo(new FileOutputStream(filename));
        System.out.println("printed " + filename);
    }
}
