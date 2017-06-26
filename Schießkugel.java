import ea.*; 

class Schießkugel extends Kugel {
     public float x;
     public float y;
     public float xbewegen;
public Schießkugel (float x,float y, String Grafik){
        super (x,y,Grafik);
        float gesy = 10.0F;
}
public void schiessen(){
          bewegen(xbewegen, -0.001f);
    }

}

