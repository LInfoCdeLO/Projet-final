import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.*;

import javax.swing.ImageIcon;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

//    /!\ AUNCUNE des lignes de codes si dessous n'est inutile. Sans doute ne sont-elles pas toutes optimisees mais elles ne sont en aucun cas inutiles, si vous ne comprenez pas a quoi elles servent c'est que vous n'avez pas bien compris le programme
//        Bon courage ^^

public class PanelPont extends JPanel implements MouseListener, MouseMotionListener {

    private Vehicule monVehicule;

    //francois
    private Point p1;            //correspond au point initial sur le bord gauche
    private Point p2;            //correspond au point initial sur le bord droit
    private Point pPressed;        //correspond au point ou j'ai presse le bouton de la souris
    private Point pClicked;        //correspond au point ou j'ai clique avec la souris
    private Point pReleased;    //correspnd au point ou j'ai relache le bouton de la souris
    private Point pDragged;        //correspond au point de draggage de la souris
    private Point pMoved;        //correpond au point ou se trouve la souris
    private Point pDetected;    //point detecter par la souris
    private Point pSave;        //point garde en memoire
    private Point pCliquable;    //point sur lequel je peux cliquer
    private Point pCalibration;
    private Distance dSave;        //garde une sauvegarde d'une distance
    private Distance dMove;        //distance dynamique
    private Distance distanceDetectee;//segent detecter par la souris
    private Image background;    //Image de font
    private Image plateforme;
    private boolean Detecter;    //ai-je detecte un point?
    private boolean build;        //suis-je entrein de construire des poutres
    private boolean appuyer;    //est-ce que j'ai le bouton de ma souris enfonce
    private boolean cliquable;    //est-ce que ce point de l'ecran est cliquable (y a-t-il un point sur lequel je peux construire une poutre)
    private boolean plusUn;
    private boolean DetecterPoutre;
    private boolean O;
    private boolean start;
    private int n;                //compteur
    private int correcteurX;    //correcteur du a l'ecran selon X
    private int correcteurY;    //correcteur du a l'ectan selon Y
    private int numeroMateriaux;// la nature de ma poutre
    private LinkedList<Distance> listeBarre = new LinkedList<>();//liste des poutres de mon pont
    private boolean calibration;


    public PanelPont(int numeroMateriaux) {
        super();
        monVehicule = new Vehicule(1, 80, 20, 40, 50);

        //francois
        this.addMouseListener(this);//ajoute un ecouteur de sourie au Panel
        this.addMouseMotionListener(this);//ajoute un ecouteur de sourie au Panel

        //import mes images de font depuis le dossier Image a la source de mon projet
        this.background = Toolkit.getDefaultToolkit().getImage("Image/Font.jpg");
        this.plateforme = Toolkit.getDefaultToolkit().getImage("Image/platforme.jpg");

        //choisi la nature de mes poutres (bois, metal...)
        this.numeroMateriaux = numeroMateriaux;

        //initialisation du point cliquable le plus a droite de l'ecran
        this.p2 = new Point(1050, 308);
        //initialisation du point cliquable le plus a gauche de l'ecran
        this.p1 = new Point(250, 308);

        //correcteur graphique correspondant au decalage entre l'ecran et le programme
        this.correcteurX = 0;
        this.correcteurY = 0;
        this.build = false;
        this.start = false;
        this.pDetected = new Point(500, 300);
        calibration = false;
        pCalibration = new Point((int) ((p1.getX() + p2.getX()) / 2), (int) ((p1.getY() + p2.getY()) / 2));
        this.setVisible(true);
        repaint();
    }


    public void paint(Graphics g) {

        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        g.drawImage(this.background, 0, 0, this);
        g.drawImage(this.plateforme, this.correcteurX, 300 + this.correcteurY, this);
        g.drawImage(this.plateforme, 1050 + this.correcteurX, 300 + this.correcteurY, this);

        g.setColor(Color.RED);
        g.fillOval((int) p1.getX() + this.correcteurX - 8, (int) p1.getY() + this.correcteurY - 8, 16, 16);
        g.fillOval((int) p2.getX() + this.correcteurX - 8, (int) p2.getY() + this.correcteurY - 8, 16, 16);
        if (calibration) {
            g.fillOval((int) pCalibration.getX() - 8, (int) pCalibration.getY() - 8, 16, 16);
        }

        if (cliquable) {
            paintBarre(g, false, this.dMove, g2);
        }

        for (Distance dis : this.listeBarre) {
            paintBarre(g, true, dis, g2);
            //System.out.println("Coordonnees: x:" + dis.getP1().getX());
        }
        //Vehicule
        double l = monVehicule.getLargeur();
        double h = monVehicule.getHauteur();

        double x1 = monVehicule.getX1();
        double y1 = 740 - monVehicule.getY1();
        double x2 = monVehicule.getX2();
        double y2 = 740 - monVehicule.getY2();

        double alpha = Math.atan((y1 - y2) / (x1 - x2));

        double x3 = x2 - h * Math.sin(alpha);
        double y3 = y2 + h * Math.cos(alpha);
        double x4 = x1 - h * Math.sin(alpha);
        double y4 = y1 + h * Math.cos(alpha);

        int[] xPoints = {(int) (x1), (int) (x2), (int) (x3), (int) (x4)};
        int[] yPoints = {(int) (740 - y1), (int) (740 - y2), (int) (740 - y3), (int) (740 - y4)};
        int nbPoints = 4;

        g.setColor(Color.green);
        g.fillPolygon(xPoints, yPoints, nbPoints);

        g.setColor(Color.black);
        g.fillOval((int) (x1 - 10), (int) (740 - y1 - 10), 20, 20);
        g.fillOval((int) (x2 - 10), (int) (740 - y2 - 10), 20, 20);

        if (this.Detecter) {
            g.setColor(Color.BLUE);
            int x = (int) this.pDetected.getX();
            int y = (int) this.pDetected.getY();
            g.fillOval(x + this.correcteurX - 5, y + this.correcteurY - 5, 10, 10);
        } else {
            g.setColor(Color.RED);
            int x = (int) this.pDetected.getX();
            int y = (int) this.pDetected.getY();
            g.fillOval(x + this.correcteurX - 5, y + this.correcteurY - 5, 10, 10);
        }
        if (DetecterPoutre) {
            g2.setColor(Color.red);
            Stroke s = g2.getStroke();
            g2.setStroke(new BasicStroke(16, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine((int) this.distanceDetectee.getP1().getX() + this.correcteurX, (int) distanceDetectee.getP1().getY() + this.correcteurY, (int) distanceDetectee.getP2().getX() + this.correcteurX, (int) distanceDetectee.getP2().getY() + this.correcteurY);
            g2.setStroke(s);
        } else {
            g2.setColor(Color.black);
            Stroke s = g2.getStroke();
            g2.setStroke(new BasicStroke(16, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine((int) this.distanceDetectee.getP1().getX() + this.correcteurX, (int) distanceDetectee.getP1().getY() + this.correcteurY, (int) distanceDetectee.getP2().getX() + this.correcteurX, (int) distanceDetectee.getP2().getY() + this.correcteurY);
            g2.setStroke(s);
            g.setColor(Color.RED);
        }

    }


    public void calibration() {
        if (!start) {
            System.out.println("calibration true");
            calibration = true;
            correcteurY = 0;
            correcteurX = 0;
        }
    }

    //cette methode permet de savoir si la point PP appartient a une poutre du pont que je viens de construire
    public void detectionPoutre(Point PP) {

        if (!start) {

            this.DetecterPoutre = false;//initialisation de DecterPoutre a false: aucune poutre n'est encore detectee
            int n = 0;
            //parcoure la liste de distance de mon pont pour savoir si mon point appartient a une de ces distances
            for (Distance dis : this.listeBarre) {
                double a = ((dis.getP1().getY() - dis.getP2().getY()) / (dis.getP1().getX() - dis.getP2().getX()));//calcule le coefficient directeur de la poutre
                double b = dis.getP1().getY() - dis.getP1().getX() * a;//calcule l'ordonnee a l'origine de la poutre
                if (dis.getP1().getX() <= dis.getP2().getX()) {
                    for (int i = (int) dis.getP1().getX(); i <= (int) dis.getP2().getX(); i++) {
                        int x = i;
                        int y = (int) (a * i + b);
                        if (PP.getX() <= x + 15 && PP.getX() >= x - 15) {
                            if (PP.getY() <= y + 15 && PP.getY() >= y - 15) {
                                this.DetecterPoutre = true;
                                n++;
                                System.out.println("Distance Suprimee " + n + " : " + dis.getP1().getX() + " " + dis.getP1().getY() + " " + dis.getP2().getX() + " " + dis.getP2().getY());
                                this.distanceDetectee = dis;
                                if (PP.equals(pReleased)) {
                                    this.listeBarre.remove(dis);
                                }
                            }
                        }
                    }
                }
                if (dis.getP1().getX() >= dis.getP2().getX()) {
                    for (int i = (int) dis.getP2().getX(); i <= (int) dis.getP1().getX(); i++) {
                        int x = i;
                        int y = (int) (a * i + b);
                        if (PP.getX() <= x + 15 && PP.getX() >= x - 15) {
                            if (PP.getY() <= y + 15 && PP.getY() >= y - 15) {
                                this.DetecterPoutre = true;
                                n++;
                                System.out.println("Distance Suprimee " + n + " : " + dis.getP1().getX() + " " + dis.getP1().getY() + " " + dis.getP2().getX() + " " + dis.getP2().getY());
                                this.distanceDetectee = dis;
                                if (PP.equals(pReleased)) {
                                    this.listeBarre.remove(dis);
                                }
                            }
                        }
                    }
                }
            }

            if (n == 0) {//si le point PP ne se trouve sur aucune poutre s'assure que DetecterPoutre est false
                this.DetecterPoutre = false;
            }
        }
    }

    //cette methode permet de savoir si la point PP est un point particulier de mon interface (joint entre les poutres)
    public void detectionPoint(Point PP, int rayon) {//

        if (!start) {
            this.Detecter = false;
            this.n = 0;//initialise le compteur a 0
            for (Distance dis : this.listeBarre) {//parcourir la liste des distances
                if (PP.getX() <= dis.getP1().getX() + rayon && PP.getX() >= dis.getP1().getX() - rayon) {
                    if (PP.getY() <= dis.getP1().getY() + rayon && PP.getY() >= dis.getP1().getY() - rayon) {//regarde si le point passe en parametre se trouve a proximite du point P1 de la distance passee en parametre
                        this.Detecter = true;//un point est Detecte!!
                        this.pDetected = dis.getP1();//pDetected prend la valeur du point qui a ete detecte
                        if (appuyer && PP.equals(this.pReleased)) {
                            this.pReleased = dis.getP1();//permet de raccorder deux poutres entre elles: si le point avec lequel j'etire ma poutre au moment de sa construction est proche d'un autre point au moment ou je relache le bouton de la sourie (et donc finalise la construction de ma poutre) le point relacher devient le point detecter et la nouvelle sitance possede un P2 egale a ce point
                            break;
                        }
                        if (!build && PP.equals(this.pClicked)) {
                            this.pClicked = dis.getP1();
                            break;
                        }
                        n++;
                        System.out.println("Detecte " + n + " : " + this.pDetected.getX() + " " + this.pDetected.getY());
                        break;
                    }
                }
                if (PP.getX() <= dis.getP2().getX() + rayon && PP.getX() >= dis.getP2().getX() - rayon) {
                    if (PP.getY() <= dis.getP2().getY() + rayon && PP.getY() >= dis.getP2().getY() - rayon) {//regarde si le point passe en parametre se trouve a proximite du point P1 de la distance
                        this.Detecter = true;
                        this.pDetected = dis.getP2();
                        if (appuyer && PP.equals(this.pReleased)) {
                            this.pReleased = dis.getP2();
                            break;
                        }
                        if (!build && PP.equals(this.pClicked)) {
                            this.pClicked = dis.getP2();
                            break;
                        }
                        n++;
                        System.out.println("Detecte " + n + " : " + this.pDetected.getX() + " " + this.pDetected.getY());
                        break;
                    }
                }
            }

            if (PP.getX() <= p1.getX() + rayon && PP.getX() >= p1.getX() - rayon) {
                if (PP.getY() <= p1.getY() + rayon && PP.getY() >= p1.getY() - rayon) {
                    this.Detecter = true;
                    this.pDetected = p1;
                    n++;
                    System.out.println("Detecte " + n + " : " + this.pDetected.getX() + " " + this.pDetected.getY());
                    if (appuyer && PP.equals(this.pReleased)) {
                        this.pReleased = p1;
                    }

                }
            }

            if (PP.getX() <= p2.getX() + rayon && PP.getX() >= p2.getX() - rayon) {
                if (PP.getY() <= p2.getY() + rayon && PP.getY() >= p2.getY() - rayon) {
                    this.Detecter = true;
                    this.pDetected = p2;
                    n++;
                    System.out.println("Detecte " + n + " : " + this.pDetected.getX() + " " + this.pDetected.getY());
                    if (appuyer && PP.equals(this.pReleased)) {
                        this.pReleased = p2;
                    }
                }
            }

            if (n == 0) {
                this.Detecter = false;
                this.distanceDetectee = new Distance(new Point(0, 0), new Point(0, 0), 1);
            }
        }
    }

    public void pointCliquable(Point PP) {

        if (!start) {
            this.pCliquable = new Point(0, 0);

            for (Distance dis : this.listeBarre) {
                if (PP.getX() <= dis.getP1().getX() + 30 && PP.getX() >= dis.getP1().getX() - 30) {
                    if (PP.getY() <= dis.getP1().getY() + 30 && PP.getY() >= dis.getP1().getY() - 30) {
                        this.cliquable = true;
                        this.pCliquable = dis.getP1();
                        System.out.println("Point cliquable Detercte " + n + " : " + this.pCliquable.getX() + " " + this.pCliquable.getY());

                    }
                }
                if (PP.getX() <= dis.getP2().getX() + 10 && PP.getX() >= dis.getP2().getX() - 30) {
                    if (PP.getY() <= dis.getP2().getY() + 10 && PP.getY() >= dis.getP2().getY() - 10) {
                        this.cliquable = true;
                        this.pCliquable = dis.getP2();
                        n++;
                        System.out.println("Point cliquable Deterte " + n + " : " + this.pCliquable.getX() + " " + this.pCliquable.getY());

                    }
                }
            }

            if (PP.getX() <= p1.getX() + 10 && PP.getX() >= p1.getX() - 10) {
                if (PP.getY() <= p1.getY() + 10 && PP.getY() >= p1.getY() - 10) {
                    this.cliquable = true;
                    this.pCliquable = p1;
                    n++;
                    System.out.println("Point cliquable Detecte " + n + " : " + this.pCliquable.getX() + " " + this.pCliquable.getY());
                }
            }

            if (PP.getX() <= p2.getX() + 10 && PP.getX() >= p2.getX() - 10) {
                if (PP.getY() <= p2.getY() + 10 && PP.getY() >= p2.getY() - 10) {
                    this.cliquable = true;
                    this.pCliquable = p2;
                    n++;
                    System.out.println("Detecte " + n + " : " + this.pDetected.getX() + " " + this.pDetected.getY());
                }
            }
            if (n == 0) {
                this.Detecter = false;
            }
        }
    }

    public void paintBarre(Graphics g, boolean V, Distance dis, Graphics2D g2) {

        if (V) {
            g2.setColor(dis.getMatiere().getColor());
            Stroke s = g2.getStroke();
            g2.setStroke(new BasicStroke(16, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine((int) dis.getP1().getX() + this.correcteurX, (int) dis.getP1().getY() + this.correcteurY, (int) dis.getP2().getX() + this.correcteurX, (int) dis.getP2().getY() + this.correcteurY);
            g.setColor(Color.red);
            g2.setStroke(s);
            g.fillOval((int) dis.getP1().getX() + this.correcteurX - 5, (int) dis.getP1().getY() + this.correcteurY - 5, 10, 10);
            g.fillOval((int) dis.getP2().getX() + this.correcteurX - 5, (int) dis.getP2().getY() + this.correcteurY - 5, 10, 10);

        } else {
            g.setColor(Color.RED);
            g.drawLine((int) dis.getP1().getX() + this.correcteurX, (int) dis.getP1().getY() + this.correcteurY, (int) dis.getP2().getX() + this.correcteurX, (int) dis.getP2().getY() + this.correcteurY);
            g.setColor(Color.red);
            g.fillOval((int) dis.getP1().getX() + this.correcteurX - 5, (int) dis.getP1().getY() + this.correcteurY - 5, 10, 10);
            g.fillOval((int) dis.getP2().getX() + this.correcteurX - 5, (int) dis.getP2().getY() + this.correcteurY - 5, 10, 10);
            this.dSave = new Distance(this.pSave, this.pMoved, this.numeroMateriaux);
        }
    }


    public void mouseClicked(MouseEvent e) {

        if (!start) {
            if (calibration) {
                correcteurX = e.getX() - (int) pCalibration.getX();
                correcteurY = e.getY() - (int) pCalibration.getY();
                calibration = false;
                System.out.println("calib false");
            } else {
                this.pClicked = new Point(e.getX(), e.getY());
                if (!this.build) {//je suis entrain d'effacer un poutre
                    this.detectionPoutre(this.pClicked);//regarde si le point sur lequel je viens de cliquer appartient a une poutre
                    repaint();
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {

        if (!start) {
            this.pReleased = new Point(0, 0);//reinitialise le point pReleased
            this.pPressed = new Point(e.getX(), e.getY());
            this.appuyer = true;//je suis entrain d'appuyer sur le bouton
            this.cliquable = false;//reinitialise le fait que je me trouve PAS sur un point cliquable
            if (this.build) {
                this.pointCliquable(this.pPressed);//regarde si le point sur lerquel je viens de presser le bouton de la sourie est un point particulier
                if (cliquable) {//si le point est un point particulie faire en sorte que :
                    this.pSave = this.pPressed;//garde en memoire le point pPressed
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

        if (!start) {
            this.pReleased = new Point(e.getX(), e.getY());
            if (build && cliquable) {// si je suis entrain de construire
                Point point1 = new Point((int) this.pCliquable.getX(), (int) this.pCliquable.getY());
                Point point2 = new Point(0, 0);
                this.detectionPoint(this.pReleased, 20);
                point2 = new Point((int) this.pReleased.getX(), (int) this.pReleased.getY());
                if (point1.getX() != point2.getX()) {
                    Distance D = new Distance(point1, point2, this.numeroMateriaux);
                    this.listeBarre.add(D);
                }
            }
            this.distanceDetectee = new Distance(new Point(-50, -50), new Point(-50, -50), 1);
            this.appuyer = false;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {

        if (!start) {
            this.pMoved = new Point(e.getX(), e.getY());
            //permet de savoir si mon curseur se trouve sur un point remarquable (joint)
            detectionPoint(this.pMoved, 10);

            if (this.Detecter) {
                this.plusUn = true;//initialisation d'un boolean sur true afin d'avoir un paint du point detecte dynamique
                repaint();
            }
            if (!this.Detecter && this.plusUn) {//permet de ramner la couteur du point detecter a sa couleur d'origine (rouge) une fois que l'on est plus sur le point remarquable
                repaint();
                this.plusUn = false;
            }

            if (!this.build) {
                detectionPoutre(this.pMoved);
                if (this.DetecterPoutre) {
                    repaint();
                }
            }
            repaint();
        }

    }

    public void mouseDragged(MouseEvent e) {

        if (!start) {
            //permet de previsualiser les poutres lors de leur construction
            this.pDragged = new Point(e.getX(), e.getY());
            detectionPoint(this.pDragged, 20);
            if (this.cliquable) {
                Point point1 = this.pCliquable;
                Point point2 = this.pDragged;
                //construit une distance provisoir entre le point ou j'ai presse la souris et le point ou a ete dragge la sourie
                this.dMove = new Distance(point1, point2, this.numeroMateriaux);
                repaint();
            }
        }
    }

    public void setListeBarre() {
        this.listeBarre = new LinkedList<Distance>();
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public LinkedList<Distance> getListeBarre() {
        return this.listeBarre;
    }

    public void setNumeroMateriaux(int a) {
        this.numeroMateriaux = a;
    }

    public Vehicule getVehicule() {
        return monVehicule;
    }

    public void setVehicule(Vehicule a) {
        this.monVehicule = a;
    }
}
