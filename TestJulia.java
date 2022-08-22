package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestJulia extends JPanel implements AdjustmentListener, MouseListener {

    JFrame frame;
    JPanel leftPanel, rightPanel, masterPanel, leftLabels, leftScroll, rightLabels, rightScroll, topPanel, verPanel;
    JScrollBar ABar, BBar, ZoomBar, HueBar, SatBar, BriBar, EyeBar, IterBar, VerBar, MulBar, RadBar;
    JLabel ALabel, BLabel, ZoomLabel, HueLabel, SatLabel, BriLabel, EyeLabel, IterLabel, VerLabel, MulLabel, RadLabel;
    JButton resetButton, saveButton;
    JScrollPane juliaPane;
    JFileChooser fileChooser;
    BufferedImage image;
    double A, B, zoom;
    boolean skip;
    float hue, sat, brightness, eye, max_iter, multiplier, radius;
    int version;
    int width = 1000;
    int height = 600;

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if(e.getSource() == ABar){
            A = (double) ABar.getValue() / 1000.0;
            ALabel.setText("A: " + A + "\t\t");
        }
        if(e.getSource() == BBar){
            B = (double) BBar.getValue() / 1000.0;
            BLabel.setText("B: " + B + "\t\t");
        }
        if(e.getSource() == ZoomBar){
            zoom = (double)ZoomBar.getValue() / 10.0;
            ZoomLabel.setText("Zoom: " + zoom + "\t\t");
        }
        if(e.getSource() == HueBar){
            hue = (float) (HueBar.getValue() / 1000.0);
            HueLabel.setText("Hue: " + hue + "\t\t");
        }
        if(e.getSource() == SatBar){
            sat = (float) (SatBar.getValue() / 1000.0);
            SatLabel.setText("Saturation: " + sat + "\t\t");
        }
        if(e.getSource() == BriBar){
            brightness = (float) (BriBar.getValue() / 1000.0);
            BriLabel.setText("Brightness: " + brightness + "\t\t");
        }
        if(e.getSource() == EyeBar){
            eye = (float) (EyeBar.getValue() / 1000.0);
            EyeLabel.setText("Eye Hue: " + eye + "\t\t");
        }
        if(e.getSource() == IterBar){
            max_iter = (float) IterBar.getValue();
            IterLabel.setText("Iterations: " + max_iter + "\t\t");
        }
        if(e.getSource() == VerBar){
            version = VerBar.getValue();
            VerLabel.setText("Version: " + version + "\t\t");
        }
        if(e.getSource() == MulBar){
            multiplier = MulBar.getValue();
            MulLabel.setText("Multiplier: " + multiplier + "\t\t");
        }
        if(e.getSource() == RadBar){
            radius = (float) (RadBar.getValue() / 10.0);
            RadLabel.setText("Radius: " + radius + "\t\t");
        }

        repaint();
    }

    public TestJulia(){
        frame = new JFrame("Julia Set Program");
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(width*3, height*3));

        ABar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        BBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        ZoomBar = new JScrollBar(JScrollBar.HORIZONTAL, 10, 0, 0, 1000);
        HueBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 0, 1000);
        SatBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 0, 1000);
        BriBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 0, 1000);
        EyeBar = new JScrollBar(JScrollBar.HORIZONTAL, 500, 0, 0, 1000);
        IterBar = new JScrollBar(JScrollBar.HORIZONTAL, 300, 0, 0, 500);
        MulBar = new JScrollBar(JScrollBar.HORIZONTAL, 2, 0, 0, 10);
        VerBar = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 1, 5);
        RadBar = new JScrollBar(JScrollBar.HORIZONTAL, 60, 0, 1, 60);
        resetButton = new JButton("Reset to Default");
        saveButton = new JButton("Save Image");

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetValues();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { saveImage(); }
        });

        A = (double)ABar.getValue() / 1000.0;
        B = (double)BBar.getValue() / 1000.0;
        zoom = (double)ZoomBar.getValue() / 10.0;
        hue = (float) (HueBar.getValue() / 1000.0);
        sat = (float) (SatBar.getValue() / 1000.0);
        brightness = (float) (BriBar.getValue() / 1000.0);
        eye = (float) (EyeBar.getValue() / 1000.0);
        max_iter = (float) IterBar.getValue();
        multiplier = (float) MulBar.getValue();
        version = VerBar.getValue();
        radius = (float) (RadBar.getValue() / 10.0);
        skip = false;

        String currDir=System.getProperty("user.dir");
        fileChooser=new JFileChooser(currDir);

        ABar.addAdjustmentListener(this);
        BBar.addAdjustmentListener(this);
        ZoomBar.addAdjustmentListener(this);
        HueBar.addAdjustmentListener(this);
        SatBar.addAdjustmentListener(this);
        BriBar.addAdjustmentListener(this);
        EyeBar.addAdjustmentListener(this);
        IterBar.addAdjustmentListener(this);
        VerBar.addAdjustmentListener(this);
        MulBar.addAdjustmentListener(this);
        RadBar.addAdjustmentListener(this);

        ABar.addMouseListener(this);
        BBar.addMouseListener(this);
        ZoomBar.addMouseListener(this);
        HueBar.addMouseListener(this);
        SatBar.addMouseListener(this);
        BriBar.addMouseListener(this);
        EyeBar.addMouseListener(this);
        IterBar.addMouseListener(this);
        VerBar.addMouseListener(this);
        MulBar.addMouseListener(this);
        RadBar.addMouseListener(this);

        ALabel = new JLabel("A: " + A + "\t\t");
        BLabel = new JLabel("B: " + B + "\t\t");
        ZoomLabel = new JLabel("Zoom: " + zoom + "\t\t");
        HueLabel = new JLabel("Hue: " + hue + "\t\t");
        SatLabel = new JLabel("Saturation: " + sat + "\t\t");
        BriLabel = new JLabel("Brightness: " + brightness + "\t\t");
        EyeLabel = new JLabel("Eye Hue: " + eye + "\t\t");
        IterLabel = new JLabel("Iterations: " + max_iter + "\t\t");
        VerLabel = new JLabel("Version: " + version + "\t\t");
        MulLabel = new JLabel("Multiplier: " + multiplier + "\t\t");
        RadLabel = new JLabel("Radius: " + radius + "\t\t");

        juliaPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        juliaPane.setPreferredSize(new Dimension(width/5,height/5));

        masterPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        topPanel = new JPanel();
        verPanel = new JPanel();

        leftLabels = new JPanel();
        leftScroll = new JPanel();
        rightLabels = new JPanel();
        rightScroll = new JPanel();

        GridLayout grid = new GridLayout(5, 1);

        masterPanel.setLayout(new GridLayout(1, 2));
        masterPanel.add(leftPanel);
        masterPanel.add(rightPanel);

        leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());

        leftPanel.add(leftLabels, BorderLayout.WEST);
        leftPanel.add(leftScroll, BorderLayout.CENTER);
        rightPanel.add(rightLabels, BorderLayout.WEST);
        rightPanel.add(rightScroll, BorderLayout.CENTER);

        leftLabels.setLayout(grid);
        leftScroll.setLayout(grid);
        rightLabels.setLayout(grid);
        rightScroll.setLayout(grid);

        leftLabels.add(ALabel);
        leftLabels.add(BLabel);
        leftLabels.add(ZoomLabel);
        leftLabels.add(IterLabel);
        leftLabels.add(RadLabel);

        rightLabels.add(HueLabel);
        rightLabels.add(SatLabel);
        rightLabels.add(BriLabel);
        rightLabels.add(EyeLabel);
        rightLabels.add(MulLabel);

        leftScroll.add(ABar);
        leftScroll.add(BBar);
        leftScroll.add(ZoomBar);
        leftScroll.add(IterBar);
        leftScroll.add(RadBar);

        rightScroll.add(HueBar);
        rightScroll.add(SatBar);
        rightScroll.add(BriBar);
        rightScroll.add(EyeBar);
        rightScroll.add(MulBar);

        verPanel.setLayout(new BorderLayout());
        verPanel.add(VerLabel, BorderLayout.WEST);
        verPanel.add(VerBar, BorderLayout.CENTER);

        topPanel.setLayout(new BorderLayout());
        topPanel.add(resetButton, BorderLayout.WEST);
        topPanel.add(saveButton, BorderLayout.EAST);
        topPanel.add(verPanel, BorderLayout.CENTER);

        frame.add(masterPanel, BorderLayout.SOUTH);
        frame.add(juliaPane, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.PAGE_START);

        frame.setVisible(true);
    }

    public void resetValues(){
        ABar.setValue(0);
        BBar.setValue(0);
        ZoomBar.setValue(10);
        HueBar.setValue(1000);
        SatBar.setValue(1000);
        BriBar.setValue(1000);
        EyeBar.setValue(500);
        IterBar.setValue(300);
        VerBar.setValue(1);
        MulBar.setValue(2);
        RadBar.setValue(10);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < width; x += (!skip) ? 1 : 4){
            for(int y = 0; y < height; y++){
                float iter = max_iter;
                double zx = 1.5 * ((x - 0.5 * width) / (0.5 * zoom * width));
                double zy = (y - 0.5 * height) / (0.5 * zoom * height);

                switch(version) {
                    case 1:
                        while (zx * zx + zy * zy < radius && iter > 0) {
                            double diff = zx * zx - zy * zy + A;
                            zy = (multiplier * zx * zy) + B;
                            zx = diff;
                            iter--;
                        }
                        break;

                    case 2:
                        while (zx + zy * zy < radius && iter > 0) {
                            double diff = zx - zy * zy + A;
                            zy = (multiplier * zx * zy) + B;
                            zx = diff;
                            iter--;
                        }
                        break;

                    case 3:
                        while (zx * zx + zy < radius && iter > 0) {
                            double diff = zx * zx - zy + A;
                            zy = (multiplier * zx * zy) + B;
                            zx = diff;
                            iter--;
                        }
                        break;

                    case 4:
                        while (zx * zx * zx + zy * zy * zy < radius && iter > 0) {
                            double diff = zx * zx * zx - zy * zy * zy + A;
                            zy = (multiplier * zx * zy) + B;
                            zx = diff;
                            iter--;
                        }
                        break;

                    case 5:
                        while (zx * zx * zx + zy < radius && iter > 0) {
                            double diff = zx * zx * zx - zy + A;
                            zy = (multiplier * zx * zy) + B;
                            zx = diff;
                            iter--;
                        }
                        break;
                }

                int c;
                if (iter > 0) {
                    c = Color.HSBtoRGB(hue * (iter / max_iter) % 1, sat, brightness);
                } else {
                    c = Color.HSBtoRGB(eye, 1, brightness);
                }
                image.setRGB(x, y, c);
            }
        }
        g.drawImage(image, 0, 0, null);
    }

    public static void main(String[] args){
        TestJulia app = new TestJulia();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        skip = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        skip = false;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void saveImage() {
        if (image != null) {
            FileFilter filter = new FileNameExtensionFilter("*.png", "png");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String st = file.getAbsolutePath();
                    if (st.indexOf(".png") >= 0)
                        st = st.substring(0, st.length() - 4);
                    ImageIO.write(image, "png", new File(st + ".png"));
                } catch (IOException e) {
                }

            }
        }
    }
}


