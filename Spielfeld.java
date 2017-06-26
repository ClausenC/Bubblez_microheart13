import ea.*;

public class Spielfeld extends Game
{
    private int xx;
    private String Farbe;
    private Rechteck Pfeil;
    private Rechteck randL;
    private Rechteck randO;
    private Rechteck randR;
    private Rechteck randU;
    private Kugel[][] kugelreihe;
    private Schießkugel Schuss;
    private Knoten K;
    private int x1;
    private int y1;
    private int newx;
    private int newy;
    private int zaehler;
    private int score;
    private int schusszaehler;

    public Spielfeld(){
        super(402,700,"Bubblez",false,true);
        Bild Hintergrundbild = new Bild(0,0,"hintergrund_fabrik.png");
        this.hintergrundSetzen(Hintergrundbild);
        Pfeil = new Rechteck(3.0F,50.0F,6.0F,70.0F);
        wurzel.add(Pfeil);
        Pfeil.positionSetzen(199.0F,560.0F);
        xx = 0;
        Pfeil.farbeSetzen("Schwarz");
        randL = new Rechteck (0.0F,0.0F,1.0F,700.0F);
        randO = new Rechteck (0.0F,0.0F,402.0F,3.0F);
        randR = new Rechteck (401.0F,0.0F,1.0F,700.0F);
        randU = new Rechteck (0.0F,697.0F,402.0F,3.0F);
        wurzel.add(randL);
        wurzel.add(randO);
        wurzel.add(randR);
        wurzel.add(randU);
        K = new Knoten();
        kugelreihe = new Kugel[999][10];
        zaehler = 0;
        schusszaehler = 0;
        Schuss= new Schießkugel(180.0F,592.0F,zufallsfarbe());

        wurzel.add(Schuss);
        wurzel.add(K);
        for(int y = 10; y < 13 ; y = y+1){
            for(int x = 0; x < 10 ; x= x+1){
                kugelreihe[y][x] = new Kugel (getKoods(y,x)[0],getKoods(y,x)[1],zufallsfarbe());
                wurzel.add(kugelreihe[y][x]);
                K.add(kugelreihe[y][x]);
            }
        }
    }

    public float[] getKoods(int y, int x){
        return new float[] {x*40 , 10 + 40*(y-10)};
    }

    public String zufallsfarbe(){
        int x = (int) (Math.random()*4);
        String kugelfarbe = "kugel_Braun.png";
        if (x<1){
            kugelfarbe = "Kugel_Blau.png";
        }
        else if (x<2){
            kugelfarbe = "Kugel_Rot.png";
        }
        else if (x<3){
            kugelfarbe = "Kugel_Grün.png";
        }
        else if (x<4){
            kugelfarbe = "Kugel_Gelb.png";
        }
        return kugelfarbe;
    }

    public void einreihen(){
        x1 =(int) (Schuss.getX()+20)/40;
        y1 =(int) (((Schuss.getY()+20)-10)/40)+10;
        newx = x1*40+20;
        newy = 10+40*(y1-10)+20;
        if(x1>0 && x1<9){
            if(kugelreihe[y1-1][x1] !=null || kugelreihe[y1][x1-1] != null || kugelreihe[y1][x1+1] !=null ){
                Schuss.mittelpunktSetzen(newx,newy);
                kugelreihe [y1][x1] = Schuss;
                K.add(kugelreihe[y1][x1]);
            }
            else if( x1 <=(Schuss.getX()+20)/40){
                x1--;
                newx = x1*40+20;
                Schuss.mittelpunktSetzen(newx,newy);
                kugelreihe [y1][x1] = Schuss;
                K.add(kugelreihe[y1][x1]);
            }
            else{
                x1++;
                newx = x1*40+20;
                Schuss.mittelpunktSetzen(newx,newy);
                kugelreihe [y1][x1] = Schuss;
                K.add(kugelreihe[y1][x1]);
            }
        }
        else{
            Schuss.mittelpunktSetzen(newx,newy);
            kugelreihe [y1][x1] = Schuss;
            K.add(kugelreihe[y1][x1]);  
        }
    }

    public void prüfen(){
        zaehler = 0;
        if(y1>0){
            if(kugelreihe[y1-1][x1] != null){
                if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1-1][x1].kugelfarbe){
                    kugelreihe[y1][x1].merker++;
                    kugelreihe[y1-1][x1].merker++;
                    y1--;
                    if(y1>0){
                        if(kugelreihe[y1-1][x1] != null){
                            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1-1][x1].kugelfarbe){
                                kugelreihe[y1][x1].merker++;
                                kugelreihe[y1-1][x1].merker++;

                            }
                        }
                    }
                    if(kugelreihe[y1+1][x1]!= null){
                        if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1+1][x1].kugelfarbe){
                            kugelreihe[y1][x1].merker++;
                            kugelreihe[y1+1][x1].merker++;
                        }
                    }
                    if(x1>0){
                        if(kugelreihe[y1][x1-1]!= null){
                            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1-1].kugelfarbe){
                                kugelreihe[y1][x1].merker++;
                                kugelreihe[y1][x1-1].merker++;
                            }
                        }
                    }
                    if(x1<9){
                        if(kugelreihe[y1][x1+1]!= null){
                            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1+1].kugelfarbe){
                                kugelreihe[y1][x1].merker++;
                                kugelreihe[y1][x1+1].merker++;
                            }
                        }
                    }
                    y1++;
                }
            }
        }

        if(kugelreihe[y1+1][x1]!= null){
            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1+1][x1].kugelfarbe){
                kugelreihe[y1][x1].merker++;
                kugelreihe[y1+1][x1].merker++;
                y1++;
                if(y1>0){
                    if(kugelreihe[y1-1][x1] != null){
                        if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1-1][x1].kugelfarbe){
                            kugelreihe[y1][x1].merker++;
                            kugelreihe[y1-1][x1].merker++;
                        }
                    }
                }
                if(kugelreihe[y1+1][x1]!= null){
                    if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1+1][x1].kugelfarbe){
                        kugelreihe[y1][x1].merker++;
                        kugelreihe[y1+1][x1].merker++;
                    }
                }
                if(x1>0){
                    if(kugelreihe[y1][x1-1]!= null){
                        if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1-1].kugelfarbe){
                            kugelreihe[y1][x1].merker++;
                            kugelreihe[y1][x1-1].merker++;
                        }
                    }
                }
                if(x1<9){
                    if(kugelreihe[y1][x1+1]!= null){
                        if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1+1].kugelfarbe){
                            kugelreihe[y1][x1].merker++;
                            kugelreihe[y1][x1+1].merker++;
                        }
                    }
                }
                y1--;    
            }
        }

        if(x1>0){
            if(kugelreihe[y1][x1-1]!= null){
                if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1-1].kugelfarbe){
                    kugelreihe[y1][x1].merker++;
                    kugelreihe[y1][x1-1].merker++;
                    x1--;
                    if(y1>0){
                        if(kugelreihe[y1-1][x1] != null){
                            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1-1][x1].kugelfarbe){
                                kugelreihe[y1][x1].merker++;
                                kugelreihe[y1-1][x1].merker++;
                            }
                        }
                    }
                    if(kugelreihe[y1+1][x1]!= null){
                        if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1+1][x1].kugelfarbe){
                            kugelreihe[y1][x1].merker++;
                            kugelreihe[y1+1][x1].merker++;
                        }
                    }
                    if(x1>0){
                        if(kugelreihe[y1][x1-1]!= null){
                            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1-1].kugelfarbe){
                                kugelreihe[y1][x1].merker++;
                                kugelreihe[y1][x1-1].merker++;
                            }
                        }
                    }
                    if(x1<9){
                        if(kugelreihe[y1][x1+1]!= null){
                            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1+1].kugelfarbe){
                                kugelreihe[y1][x1].merker++;
                                kugelreihe[y1][x1+1].merker++;
                            }
                        }
                    }
                    x1++;
                }
            }
        }

        if(x1<9){
            if(kugelreihe[y1][x1+1]!= null){
                if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1+1].kugelfarbe){
                    kugelreihe[y1][x1].merker++;
                    kugelreihe[y1][x1+1].merker++;
                    x1++;
                    if(y1>0){
                        if(kugelreihe[y1-1][x1] != null){
                            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1-1][x1].kugelfarbe){
                                kugelreihe[y1][x1].merker++;
                                kugelreihe[y1-1][x1].merker++;
                            }
                        }
                    }
                    if(kugelreihe[y1+1][x1]!= null){
                        if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1+1][x1].kugelfarbe){
                            kugelreihe[y1][x1].merker++;
                            kugelreihe[y1+1][x1].merker++;
                        }
                    }
                    if(x1>0){
                        if(kugelreihe[y1][x1-1]!= null){
                            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1-1].kugelfarbe){
                                kugelreihe[y1][x1].merker++;
                                kugelreihe[y1][x1-1].merker++;
                            }
                        }
                    }
                    if(x1<9){
                        if(kugelreihe[y1][x1+1]!= null){
                            if(kugelreihe[y1][x1].kugelfarbe == kugelreihe[y1][x1+1].kugelfarbe){
                                kugelreihe[y1][x1].merker++;
                                kugelreihe[y1][x1+1].merker++;
                            }
                        }
                    }
                    x1--;
                }  
            }
        }
        for(int y = 0; y < 999 ; y = y+1){
            for(int x = 0; x < 10 ; x= x+1){
                if(kugelreihe[y][x] != null){
                    if (kugelreihe[y][x].merker > 0){
                        zaehler++;
                    }
                }
            }
        }
        if (zaehler >= 3){
            for(int y = 0; y < 999 ; y = y+1){
                for(int x = 0; x < 10 ; x= x+1){
                    if(kugelreihe[y][x] != null){
                        if (kugelreihe[y][x].merker > 0){
                            kugelreihe[y][x].mittelpunktSetzen(5000,5000);
                            kugelreihe[y][x] = null;
                            score = score + 10;
                        }
                    }
                }
            }
        }
        for(int x = 0;x<16;x++){
            System.out.println("");
        }
        System.out.println("Score: " + score);
        for(int y = 0; y < 999 ; y = y+1){
            for(int x = 0; x < 10 ; x= x+1){
                if(kugelreihe[y][x] != null){
                    kugelreihe[y][x].merker = 0;
                }
            }
        }
    }

    public void neueReihe(){
        if (schusszaehler < 5){
            schusszaehler++;
        }
        else{
            for(int x = 0; x < 10 ; x= x+1){
                    kugelreihe[10][x] = new Kugel (getKoods(10,x)[0],getKoods(10,x)[1],zufallsfarbe());
                    wurzel.add(kugelreihe[10][x]);
                    K.add(kugelreihe[10][x]);
                }
            for(int y = 50; y >0 ; y = y-1){
                for(int x = 0; x < 10 ; x= x+1){
                    kugelreihe[y][x] = kugelreihe[y-1][x];
                    kugelreihe[y][x].mittelpunktSetzen(((int)getKoods(y,x)[0])+20,(((int)getKoods(y,x)[1])+20));
                    }
                }
            schusszaehler = 0;
        }
    }

    @Override
    public void tasteReagieren(int taste){
        if(taste == 27){
            if(xx<20){
                Pfeil.drehenRelativ(-3.5);
                Pfeil.verschieben(1,0);
                Schuss.drehenRelativ(-3.5);
                xx=xx+1;
                Schuss.xbewegen = Schuss.xbewegen + 0.00007F;
            } 
        }
        else if( taste == 29){
            if(xx>-20){
                Pfeil.drehenRelativ(3.5);
                Pfeil.verschieben(-1,0);
                Schuss.drehenRelativ(3.5);
                xx=xx-1;
                Schuss.xbewegen = Schuss.xbewegen - 0.00007F;
            } 
        }
        else if(taste == 30){   

            while(Schuss.schneidet(randO)== false && (Schuss.schneidet(K) == false )){
                if(Schuss.schneidet(randL) == false && (Schuss.schneidet(randR) == false)){
                    Schuss.schiessen();
                } else{
                    Schuss.xbewegen = -(Schuss.xbewegen);
                    Schuss.schiessen();
                }
            }

            K.add(Schuss);
            Pfeil.drehenRelativ((float)xx*3.5);
            Pfeil.verschieben(xx*-1,0);
            xx = 0;

            einreihen();
            prüfen();
            neueReihe();
            Schuss = new Schießkugel(180.0F,592.0F,zufallsfarbe());
            wurzel.add(Schuss);

        }
    }
}

