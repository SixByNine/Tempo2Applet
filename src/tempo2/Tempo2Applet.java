package tempo2;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import ptolemy.plot.Plot;
import ptolemy.plot.PlotBox;
import ptolemy.plot.plotml.PlotMLApplet;

public class Tempo2Applet extends PlotMLApplet {

    ControlPanel controlPanel;
    FitStatusPanel fitStatusPanel;
    ArrayList<Double> wraps = new ArrayList<Double>();
    ArrayList<Double> hiddenWraps = new ArrayList<Double>();
    ArrayList<String> lastResiduals = null;
    private int lastPtype = 0;
    private double period = 1.0;
    private double start = 0;
    private double finish = 0;
    private String psr = null;

    @Override
    public void init() {
        super.init();
        final int width = this.getWidth();
        final int height = this.getHeight();


        fitStatusPanel = new FitStatusPanel();
        controlPanel = new ControlPanel(Tempo2Applet.this);

        psr = getParameter("psr");
        String timurlspec = getParameter("timfile");
        StringBuffer tim = new StringBuffer();
        StringBuffer par = new StringBuffer();

        if (timurlspec != null) {
            try {
                showStatus("Reading data");

                URL dataurl = new URL(getDocumentBase(), timurlspec);
                BufferedReader input = new BufferedReader(new InputStreamReader(dataurl.openStream()));

                String line = input.readLine();
                while (line != null) {
                    tim.append(line + "\n");
                    line = input.readLine();
                }
                this.controlPanel.setInTim(tim.toString());
            } catch (MalformedURLException e) {
                System.err.println(e.toString());
            } catch (FileNotFoundException e) {
                System.err.println("PlotApplet: file not found: " + e);
            } catch (IOException e) {
                System.err.println("PlotApplet: error reading input file: " + e);
            }
        }

        String parurlspec = getParameter("parfile");
        if (parurlspec != null) {
            try {
                showStatus("Reading data");

                URL dataurl = new URL(getDocumentBase(), parurlspec);
                BufferedReader input = new BufferedReader(new InputStreamReader(dataurl.openStream()));

                String line = input.readLine();
                while (line != null) {
                    par.append(line + "\n");
                    line = input.readLine();
                }
                this.controlPanel.setInPar(par.toString());
            } catch (MalformedURLException e) {
                System.err.println(e.toString() + " " + getDocumentBase() + timurlspec);
            } catch (FileNotFoundException e) {
                System.err.println("PlotApplet: file not found: " + e);
            } catch (IOException e) {
                System.err.println("PlotApplet: error reading input file: " + e);
            }
        }

        try {

            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {

                    _setPlotSize(width - 300, height);

                    getContentPane().add(controlPanel, BorderLayout.EAST);

                    getContentPane().add(fitStatusPanel, BorderLayout.SOUTH);

                    fitStatusPanel.setVisible(true);
                    controlPanel.setVisible(true);
                    getContentPane().validate();
                    repaint();
                }
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(Tempo2Applet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Tempo2Applet.class.getName()).log(Level.SEVERE, null, ex);
        }
        lastPtype=1;
        reFit(par.toString(), tim.toString(), true);
    }

    String reFit(String par, String tim) {
        return reFit(par, tim, false);
    }

    String reFit(String par, String tim, boolean nofit) {
        StringBuffer newpar = new StringBuffer();
        ArrayList<String> resid = new ArrayList<String>();
        StringBuffer tempo2out = new StringBuffer();
        StringBuffer jmp = new StringBuffer();
        hiddenWraps.clear();
        for (double d : wraps) {

            jmp.append(d + "jj");
        }

        try {
            URL url;
            URLConnection urlConn;
            PrintStream printout;
            BufferedReader input;
            // URL of CGI-Bin script.
            url = new URL(getCodeBase().toString() + "refit.php");
            // URL connection channel.
            urlConn = url.openConnection();
            // Let the run-time system (RTS) know that we want input.
            urlConn.setDoInput(true);
            // Let the RTS know that we want to do output.
            urlConn.setDoOutput(true);
            // No caching, we want the real thing.
            urlConn.setUseCaches(false);
            // Specify the content type.
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // Send POST output.
            printout = new PrintStream(urlConn.getOutputStream());
            String content = "par=" + URLEncoder.encode(par, "UTF-8") +
                    "&tim=" + URLEncoder.encode(tim, "UTF-8") +
                    "&jmp=" + URLEncoder.encode(jmp.toString(), "UTF-8");
            if (start > 0) {
                content += "&srt=" + URLEncoder.encode(String.valueOf(start), "UTF-8");
            }
            if (finish > 0) {
                content += "&fsh=" + URLEncoder.encode(String.valueOf(finish), "UTF-8");
            }
            if (psr != null) {
                content += "&psr=" + URLEncoder.encode(psr, "UTF-8");
            }
            if (nofit) {
                content += "&nofit=1";
            }


            printout.println(content);
            printout.flush();
            printout.close();
            // Get response data.
            input = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            String line = input.readLine();
            line = input.readLine();
            while (line != null) {
                line = line.trim();
                if (line.equals("##END##")) {
                    break;
                }
                if (line.startsWith("PHASE")) {
                    String[] elems = line.split("\\s+");
                    if (elems.length > 2) {
                        double mjd = Double.parseDouble(elems[1]) * Double.parseDouble(elems[2]);
                        hiddenWraps.add(mjd);
                    }
                } else if (line.startsWith("START") || line.startsWith("FINISH")) {
                } else {
                    newpar.append(line + "\n");
                }
                line = input.readLine();
            }
            line = input.readLine();
            line = input.readLine();
            while (line != null) {
                if (line.equals("##END##")) {
                    break;
                }
                resid.add(line);
                line = input.readLine();
            }
            line = input.readLine();
            line = input.readLine();
            while (line != null) {
                if (line.equals("##END##")) {
                    break;
                }
                tempo2out.append(line + "\n");
                line = input.readLine();
            }
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(Tempo2Applet.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.controlPanel.setTempo2Pane(tempo2out.toString());
        lastResiduals = resid;
        if(!nofit)lastPtype = 0;
        clearPhaseWraps();
        return newpar.toString();
    }

    @Override
    public PlotBox newPlot() {
        return new Tempo2Plot(this);
    }

    private void replotResid() {
        plotResid(lastResiduals, lastPtype);
    }

    public void showPostFit() {
        lastPtype = 0;
        replotResid();
    }

    public void showPreFit() {
        lastPtype = 1;
        replotResid();
    }

    private void plotResid(final ArrayList<String> residuals, final int ptype) {
        lastResiduals = residuals;
        lastPtype = ptype;

        if (residuals != null && residuals.size() > 0) {
            final double[] mjds = new double[residuals.size()];
            final double[] prefit = new double[residuals.size()];
            final double[] postfit = new double[residuals.size()];
            final double[] reserr = new double[residuals.size()];
            final double[] data;
            if (ptype == 0) {
                data = postfit;
            } else {
                data = prefit;
            }
            int i = 0;
            for (String line : residuals) {
                String[] elems = line.split("\t");
                if (elems[0].equals("HDR")) {
                    if (elems[1].equals("P0")) {
                        period = Double.parseDouble(elems[2]);
                    }
                    continue;
                }

                mjds[i] = Double.parseDouble(elems[0]);
                prefit[i] = Double.parseDouble(elems[1]);
                postfit[i] = Double.parseDouble(elems[3]);
                reserr[i] = Double.parseDouble(elems[5]) / 1e6;

                for (double d : wraps) {
                    double mjd = d;
                    if (mjd < 0) {
                        mjd = -mjd;
                    }
                    if (mjds[i] > mjd) {
                        if (d < 0) {
                            prefit[i] -= period;
                            postfit[i] -= period;
                        } else {
                            prefit[i] += period;
                            postfit[i] += period;
                        }
                    }
                }
                for (double d : hiddenWraps) {
                    System.err.println(d);
                    double mjd = d;
                    if (mjd < 0) {
                        mjd = -mjd;
                    }
                    if (mjds[i] > mjd) {
                        if (d < 0) {
                            prefit[i] += period;
                            postfit[i] += period;
                        } else {
                            prefit[i] -= period;
                            postfit[i] -= period;
                        }
                    }
                }

                i++;
            }
            final int totalResid = i;

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    try {
                        plot().setVisible(false);
                        plot().clear(true);
                        plot().setGrid(true);

                        plot().setTitle("Ploty Plot plot");

                        plot().setXLabel("MJD");

                        ((Plot) plot()).setMarksStyle("points");
                        double s = start;
                        double f = finish;
                        if (f == 0) {
                            f = Double.MAX_VALUE;
                        }
                        String p = "";
                        if (psr != null) {
                            p = psr;
                        }
                        if (ptype == 0) {
                            plot().setTitle("Post-Fit Residuals " + p);
                            plot().setYLabel("Post-fit Residual (s)");
                        } else {
                            plot().setTitle("Pre-Fit Residuals " + p);
                            plot().setYLabel("Pre-fit Residual (s)");
                        }
                        for (int i = 0; i < totalResid; i++) {
                            if (mjds[i] > s && mjds[i] < f) {
                                ((Plot) plot()).addPointWithErrorBars(ptype, mjds[i], data[i], data[i] - reserr[i] / 2, data[i] + reserr[i] / 2, false);
                            }
                        }
                        for (double d : wraps) {
                            double mjd = d;
                            int p2type = 2;

                            if (mjd < 0) {
                                mjd = -mjd;
                                p2type = 3;
                            }
                            ((Plot) plot()).addPoint(p2type, mjd, 0, false);
                        }

                        plot().revalidate();
//                        ((Plot) plot()).setXRange(mjds[0], mjds[mjds.length - 1]);
                        plot().setVisible(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    void addPositiveWrap(double mjd) {
        this.wraps.add(mjd);
//        this.controlPanel.setInPar(this.controlPanel.getInPar() + "\nPHASE 1 " + mjd);
        replotResid();
    }

    void addNegativeWrap(double mjd) {
        this.wraps.add(-mjd);
//        this.controlPanel.setInPar(this.controlPanel.getInPar() + "\nPHASE -1 " + mjd);
        replotResid();
    }

    void clearPhaseWraps() {
        this.wraps.clear();
//        String s = this.controlPanel.getInPar();
//        StringBuffer newpar = new StringBuffer();
//        String[] lines = s.split("$");
//        for(String l : lines){
//            if(!l.startsWith("PHASE")){
//                newpar.append(l+"\n");
//            }
//        }
//        this.controlPanel.setInPar(newpar.toString());

        replotResid();
    }

    ArrayList<Double> getPhaseWraps() {
        return this.wraps;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public double getPeriod() {
        return period;
    }

    public double getFinish() {
        return finish;
    }

    public void setFinish(double finish) {
        this.finish = finish;
        replotResid();

    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
        replotResid();

    }

    public void clearZoom() {
        this.start = 0;
        this.finish = 0;
        replotResid();

    }
}
