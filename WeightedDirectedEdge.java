public class WeightedDirectedEdge implements Comparable<WeightedDirectedEdge>{
    private final int v;
    private final int w;
    private int weight;
    private double price;
    /**
    * Create a directed edge from v to w with given weight.
    */
    public WeightedDirectedEdge(int v, int w, int weight,double price) {
      this.v = v;
      this.w = w;
      this.weight = weight;
      this.price=price;
    }

    public int from(){
      return v;
    }

    public int to(){
      return w;
    }

    public int weight(){
      return weight;
    }
    public double price(){
        return price;
    }

    public int compareTo(WeightedDirectedEdge e)
    {
      int number=this.weight()-e.weight();
      
      return number;

    }

    public boolean equals(int from,int to)
    {
      if(this.from()== from&&this.to()==to)
      {
        return true;
      
      }else
      {
        return false;
      }

    }
    
  }