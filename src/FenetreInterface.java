import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

/**
 *
 */
public class FenetreInterface extends JFrame implements ActionListener, ChangeListener {
    private PanelPont conteneur1;
    private JPanel conteneur2;
    private JPanel conteneur3;
    private JLabel texte1;
    private JLabel texte2;
    private JLabel texte3;
    private JLabel texte4;
    private JPanel conteneurTotal;
    private JButton start;
    private JButton recommencer;
    private JButton creer;
    private JButton effacer;
    private JButton calibrer;
    private JRadioButton boutonBeton;
    private JRadioButton boutonBois;
    private JRadioButton boutonMetal;
    private ButtonGroup materiaux;
    private JSlider selectVehicule;
    private JSlider selectPoids;
    private JSlider selectVitesse;
    private boolean pressStart;
    Timer timer;

    /**
     *
     */
    public FenetreInterface() {
        super("On construit des ponts");
        setResizable(false);
        setSize(1800, 1000);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new Timer(10, this);

        //Les JLabel
        texte1 = new JLabel("Choisissez votre materiaux", JLabel.CENTER);
        Font policeTexte1 = new Font(" Comic Sans MS ", Font.BOLD, 24);
        texte1.setFont(policeTexte1);
        texte1.setBounds(250, 10, 400, 50);
        texte1.setForeground(Color.black);

        texte2 = new JLabel("Choisissez votre vehicule", JLabel.CENTER);
        Font policeTexte2 = new Font(" Comic Sans MS ", Font.BOLD, 24);
        texte2.setFont(policeTexte2);
        texte2.setBounds(10, 50, 450, 50);
        texte2.setForeground(Color.black);

        texte3 = new JLabel("Choisissez le multiplicateur de poids", JLabel.CENTER);
        texte3.setFont(policeTexte2);
        texte3.setBounds(10, 205, 450, 50);
        texte3.setForeground(Color.black);

        texte4 = new JLabel("Choisissez votre vitesse en km/h", JLabel.CENTER);
        texte4.setFont(policeTexte2);
        texte4.setBounds(10, 360, 450, 50);
        texte4.setForeground(Color.black);
        //FIN JLabel

        //Les JButton
        start = new JButton("START");
        Font policeStart = new Font(" Comic Sans MS ", Font.BOLD, 40);
        start.setFont(policeStart);
        start.setBounds(10, 780, 450, 110);
        start.setBackground(Color.green);
        start.setForeground(Color.white);

        recommencer = new JButton("RECOMMENCER");
        recommencer.setFont(policeStart);
        recommencer.setBounds(10, 660, 450, 110);
        recommencer.setBackground(Color.red);
        recommencer.setForeground(Color.white);

        creer = new JButton("CREER");
        Font policeCreerEffacer = new Font(" Comic Sans MS ", Font.BOLD, 30);
        creer.setFont(policeCreerEffacer);
        creer.setBounds(1020, 20, 250, 70);
        creer.setBackground(Color.green);
        creer.setForeground(Color.black);

        effacer = new JButton("EFFACER");
        effacer.setFont(policeCreerEffacer);
        effacer.setBounds(1020, 110, 250, 70);
        effacer.setBackground(Color.red);
        effacer.setForeground(Color.black);

        calibrer = new JButton("CALIBRER");
        calibrer.setFont(policeCreerEffacer);
        calibrer.setBounds(210, 580, 250, 70);
        calibrer.setBackground(Color.black);
        calibrer.setForeground(Color.white);

        start.addActionListener(this);
        recommencer.addActionListener(this);
        creer.addActionListener(this);
        effacer.addActionListener(this);
        calibrer.addActionListener(this);
        //FIN JButton

        //Les JRadioButton
        boutonBeton = new JRadioButton("Beton");
        boutonBeton.setBounds(100, 70, 120, 80);
        Font policeJRadioButton = new Font(" Comic Sans MS ", Font.BOLD, 24);
        boutonBeton.setFont(policeJRadioButton);
        boutonBeton.setBackground(Color.white);
        boutonBeton.setForeground(Color.black);

        boutonBois = new JRadioButton("Bois");
        boutonBois.setBounds(400, 70, 120, 80);
        boutonBois.setFont(policeJRadioButton);
        boutonBois.setBackground(Color.white);
        boutonBois.setForeground(Color.black);

        boutonMetal = new JRadioButton("Metal");
        boutonMetal.setBounds(700, 70, 120, 80);
        boutonMetal.setFont(policeJRadioButton);
        boutonMetal.setBackground(Color.white);
        boutonMetal.setForeground(Color.black);


        materiaux = new ButtonGroup();

        materiaux.add(boutonBeton);
        materiaux.add(boutonBois);
        materiaux.add(boutonMetal);

        boutonBeton.addActionListener(this);
        boutonBois.addActionListener(this);
        boutonMetal.addActionListener(this);
        //FIN JRAdioButton

        //Les JSlider

        selectVehicule = new JSlider(JSlider.HORIZONTAL, 1, 3, 1);
        selectVehicule.setBounds(20, 105, 430, 50);
        selectVehicule.setMajorTickSpacing(1);
        selectVehicule.setPaintTicks(true);
        selectVehicule.setBackground(Color.white);

        Hashtable labelTable1 = new Hashtable();
        labelTable1.put(new Integer(1), new JLabel("Velo"));
        labelTable1.put(new Integer(2), new JLabel("Voiture"));
        labelTable1.put(new Integer(3), new JLabel("Camion"));
        selectVehicule.setLabelTable(labelTable1);
        selectVehicule.setPaintLabels(true);


        selectPoids = new JSlider(JSlider.HORIZONTAL, 1, 10, 2);//Attention pour le multiplieur de poids, le diviser par 2!!
        selectPoids.setBounds(20, 260, 430, 50);
        selectPoids.setMajorTickSpacing(1);
        selectPoids.setMinorTickSpacing(1);
        selectPoids.setPaintTicks(true);
        selectPoids.setBackground(Color.white);

        Hashtable labelTable2 = new Hashtable();
        labelTable2.put(new Integer(2), new JLabel("X 1"));
        labelTable2.put(new Integer(4), new JLabel("X 2"));
        labelTable2.put(new Integer(6), new JLabel("X 3"));
        labelTable2.put(new Integer(8), new JLabel("X 4"));
        labelTable2.put(new Integer(10), new JLabel("X 5"));
        selectPoids.setLabelTable(labelTable2);
        selectPoids.setPaintLabels(true);


        selectVitesse = new JSlider(JSlider.HORIZONTAL, 6, 26, 10);
        selectVitesse.setBounds(20, 415, 430, 50);
        selectVitesse.setMajorTickSpacing(4);
        selectVitesse.setMinorTickSpacing(1);
        selectVitesse.setPaintTicks(true);
        selectVitesse.setBackground(Color.white);

        Hashtable labelTable3 = new Hashtable();
        labelTable3.put(new Integer(6), new JLabel("30"));
        labelTable3.put(new Integer(10), new JLabel("50"));
        labelTable3.put(new Integer(14), new JLabel("70"));
        labelTable3.put(new Integer(18), new JLabel("90"));
        labelTable3.put(new Integer(22), new JLabel("110"));
        labelTable3.put(new Integer(26), new JLabel("130"));
        selectVitesse.setLabelTable(labelTable3);
        selectVitesse.setPaintLabels(true);


        selectPoids.addChangeListener(this);
        selectVehicule.addChangeListener(this);
        selectVitesse.addChangeListener(this);
        //FIN JSlider

        //Les Panel
        conteneur1 = new PanelPont(1);
        conteneur1.setLayout(null);
        conteneur1.setBounds(10, 10, 1300, 740);


        conteneur2 = new JPanel();
        conteneur2.setLayout(null);
        conteneur2.add(start);
        conteneur2.add(recommencer);
        conteneur2.add(texte2);
        conteneur2.add(texte3);
        conteneur2.add(texte4);
        conteneur2.add(selectPoids);
        conteneur2.add(selectVehicule);
        conteneur2.add(selectVitesse);
        conteneur2.add(calibrer);

        conteneur2.setBounds(1320, 10, 470, 950);
        conteneur2.setBackground(Color.gray);


        conteneur3 = new JPanel();
        conteneur3.setLayout(null);
        conteneur3.add(texte1);
        conteneur3.add(creer);
        conteneur3.add(effacer);
        conteneur3.add(boutonBois);
        conteneur3.add(boutonMetal);
        conteneur3.add(boutonBeton);

        conteneur3.setBounds(10, 760, 1300, 200);
        conteneur3.setBackground(Color.gray);


        conteneurTotal = new JPanel();
        conteneurTotal.setLayout(null);
        conteneurTotal.add(conteneur1);
        conteneurTotal.add(conteneur2);
        conteneurTotal.add(conteneur3);
        conteneurTotal.setBackground(Color.white);

        setContentPane(conteneurTotal);
        //FIN JPanel

        setVisible(true);

    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == boutonBois) {
            if (!pressStart) {
                for (Distance d : conteneur1.getListeBarre()) {
                    d.setMatierePoutre(1);
                }

                conteneur1.setNumeroMateriaux(1);
                System.out.println("Bois");
                repaint();
            }
        } else if (e.getSource() == boutonMetal) {
            if (!pressStart) {
                for (Distance d : conteneur1.getListeBarre()) {
                    d.setMatierePoutre(2);
                }

                conteneur1.setNumeroMateriaux(2);
                System.out.println("Metal");
                repaint();
            }
        } else if (e.getSource() == boutonBeton) {
            if (!pressStart) {
                for (Distance d : conteneur1.getListeBarre()) {
                    d.setMatierePoutre(3);
                }

                conteneur1.setNumeroMateriaux(3);
                System.out.println("Beton");
                repaint();
            }
        } else if (e.getSource() == start) {
            if (!pressStart) {
                ConstructionPont chemin = new ConstructionPont(conteneur1.getListeBarre());
                if (chemin.getCreationListe()) {
                    this.pressStart = true;
                    conteneur1.setStart(true);
                    conteneur1.getVehicule().setPoutres(chemin.getPont());
                    timer.start();
                } else {
                    JOptionPane.showMessageDialog(this, "Aucun chemin viable n'a été trouvé !!");
                }
            }
            System.out.println("START");
        } else if (e.getSource() == recommencer) {
            restart(1);
            System.out.println("Recommencer");
        } else if (e.getSource() == creer) {
            conteneur1.setBuild(true);
            System.out.println("creer");
        } else if (e.getSource() == effacer) {
            conteneur1.setBuild(false);
            System.out.println("effacer");
        } else if (e.getSource() == timer) {
            jouer();
        } else if (e.getSource() == calibrer) {
            conteneur1.calibration();
        }
    }

    /**
     *
     * @param e
     */
    public void stateChanged(ChangeEvent e) {
        if (!pressStart) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {

                if (source == selectVehicule) {

                    int val = (int) (source.getValue());

                    if (val == 1) {
                        conteneur1.setVehicule(new Vehicule(1, 80 * (double) (selectPoids.getValue() / 2.0), 20, 40, conteneur1.getVehicule().getVitesse()));
                    } else if (val == 2) {
                        conteneur1.setVehicule(new Vehicule(2, 500 * (double) (selectPoids.getValue() / 2.0), 70, 50, conteneur1.getVehicule().getVitesse()));
                    } else if (val == 3) {
                        conteneur1.setVehicule(new Vehicule(3, 2000 * (double) (selectPoids.getValue() / 2.0), 120, 60, conteneur1.getVehicule().getVitesse()));
                    }

                    conteneur1.repaint();
                    System.out.println("vehicule= " + val);
                } else if (source == selectPoids) {
                    double val = source.getValue() / 2.0;

                    if (conteneur1.getVehicule().getType() == 1) {
                        conteneur1.setVehicule(new Vehicule(1, 80 * val, 20, 40, conteneur1.getVehicule().getVitesse()));
                    } else if (conteneur1.getVehicule().getType() == 2) {
                        conteneur1.setVehicule(new Vehicule(2, 500 * val, 70, 50, conteneur1.getVehicule().getVitesse()));
                    } else if (conteneur1.getVehicule().getType() == 3) {
                        conteneur1.setVehicule(new Vehicule(3, 2000 * val, 120, 60, conteneur1.getVehicule().getVitesse()));
                    }

                    System.out.println("poids= X " + val);
                } else if (source == selectVitesse) {
                    double val = source.getValue() * 5;

                    conteneur1.setVehicule(new Vehicule(conteneur1.getVehicule().getType(), conteneur1.getVehicule().getPoids(), conteneur1.getVehicule().getLargeur(), conteneur1.getVehicule().getHauteur(), val));

                    System.out.println("Vitesse= " + val + " km/h");
                }
            }
        }
    }

    /**
     *
     */
    public void jouer() {

        if (conteneur1.getVehicule().moteur()) {
            restart(0);
            JOptionPane.showMessageDialog(this, "Votre véhicule s'est retourné, veuillez recommencer.");
        }

        if (conteneur1.getVehicule().getX2() >= 1230) {
            JOptionPane.showMessageDialog(this, "Félicitation!! Vous avez traversé sain et sauf! (ou presque...)");
            restart(0);
        }
        //test resistance


        repaint();

    }

    /**
     *
     * @param a
     */
    public void restart(int a) {

        timer.stop();
        timer = new Timer(10, this);
        this.pressStart = false;

        selectVehicule.setValue(1);
        selectPoids.setValue(2);
        selectVitesse.setValue(10);
        boutonBois.setSelected(true);

        conteneur1.setVehicule(new Vehicule(1, 80, 20, 40, 50));
        conteneur1.setBuild(false);
        conteneur1.setStart(false);
        conteneur1.setNumeroMateriaux(1);

        if (a == 1) {
            conteneur1.setListeBarre();
        }

        repaint();
        conteneur1.repaint();
    }

    /**
     *
     */
    public void haHa() {
        this.setState(ICONIFIED);
        this.setState(NORMAL);
    }

}
