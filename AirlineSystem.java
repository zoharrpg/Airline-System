import java.util.*;
import java.io.*;


@SuppressWarnings("unchecked")
public class AirlineSystem {

    private String [] cityNames = null; // City names array
    private Digraph G = null;          //Graph
   
   
    private static Scanner scan = new Scanner(System.in);
    private static final int INFINITY = Integer.MAX_VALUE;
    private String fileName; // filename for input and output

    public int menu(){
        System.out.println("*********************************");
        System.out.println("Welcome to FifteenO'One Airlines!");
        System.out.println("1. Read data from a file.");
        System.out.println("2. Display all routes.");
        System.out.println("3. Display a minimum spanning tree.");
        System.out.println("4. Compute shortest path based on distance.");
        System.out.println("5. Compute shortest path based on price.");
        System.out.println("6. Compute Shortest path based on number of hops.");
        System.out.println("7. Print path below given price.");
        System.out.println("8. Add a path.");
        System.out.println("9. Remove a path.");
        System.out.println("10. Exit");

        System.out.println("*********************************");
        System.out.print("Please choose a menu option (1-4): ");
    
        try
        {
        int choice = Integer.parseInt(scan.nextLine()); // get choice
        return choice;
        }
        catch(Exception e)
        {
          return 11; // if input is not correct, catch the exception and return the default option
        }
        
      }

      public void readGraph() throws IOException {  // read Graph method
        System.out.println("Please enter graph filename:");
        fileName = scan.nextLine();
       
        Scanner fileScan = new Scanner(new FileInputStream(fileName)); 
        int v = Integer.parseInt(fileScan.nextLine());
        G = new Digraph(v);
    
        cityNames = new String[v];
        for(int i=0; i<v; i++){
          cityNames[i] = fileScan.nextLine();
        }
    
        while(fileScan.hasNext()){
          int from = fileScan.nextInt();
          int to = fileScan.nextInt();
          int weight = fileScan.nextInt();
          double price = fileScan.nextDouble();
          G.addEdge(new WeightedDirectedEdge(from-1, to-1, weight,price));
          G.addEdge(new WeightedDirectedEdge(to-1, from-1, weight, price));
         
        }
        fileScan.close();
        System.out.println("Data imported successfully.");
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      }

      public void printGraph() {           // print all path;
        if(G == null){
          System.out.println("Please import a graph first (option 1).");
          System.out.print("Please press ENTER to continue ...");
          scan.nextLine();
        } else {
          for (int i = 0; i < G.v; i++) {
            
            for (WeightedDirectedEdge e : G.adj(i)) {
              System.out.println(cityNames[i] + " to "+cityNames[e.to()] + ": Distance is " + e.weight() + " miles and Cost is " + e.price()+" dollars.");
            }
            System.out.println();
          }
          System.out.print("Please press ENTER to continue ...");
          scan.nextLine();
    
        }

      }

      public void printsptree()    // print minmum spinning tree;
      {
        if(G==null)
        {
          System.out.println("Please import a graph first (option 1).");
          System.out.print("Please press ENTER to continue ...");
          scan.nextLine();

        }
        else
        {
        
          
          
          PriorityQueue quene = new PriorityQueue<WeightedDirectedEdge>();  // priorityQueue for store the path

          UF uf = new UF(G.v);   // union-find data structure for checking cycle and connection

          ArrayList<WeightedDirectedEdge> mst = new ArrayList<WeightedDirectedEdge>();// for store the spanning tree

         


          

          for(int i = 0; i < G.v; i++)     //Kruskal's algorithm
          {
            for(WeightedDirectedEdge s:G.adj(i))
            {
              quene.add(s);    // add all path into priority queue;
            }
          }
          while(!quene.isEmpty()&&mst.size()<G.v-1)
          {
            WeightedDirectedEdge current = (WeightedDirectedEdge)quene.poll(); // get small first
           

            if(!uf.connected(current))     // check whether is cycle, 
             { 
              uf.union(current);
              mst.add(current);          // if is not cycel, add into mst

             
              
             }
             
            
            
            
          }
          

         for(WeightedDirectedEdge now:mst)//print min spanning tree
         {
         

          System.out.println(cityNames[now.from()]+", " + cityNames[now.to()] + " : "+ now.weight());
         
         }

          
        
        }


      }


      public void shortestDistance() { //Dijkstra's algorithm
        if(G == null){
          System.out.println("Please import a graph first (option 1).");
          System.out.print("Please press ENTER to continue ...");
          scan.nextLine();
        } else {
          for(int i=0; i<cityNames.length; i++){
            System.out.println(i+1 + ": " + cityNames[i]);
          }
          System.out.print("Please enter source city (1-" + cityNames.length + "): ");
          int source = Integer.parseInt(scan.nextLine());
          System.out.print("Please enter destination city (1-" + cityNames.length + "): ");
          int destination = Integer.parseInt(scan.nextLine());
          source--;
          destination--;
          G.dijkstras(source, destination);
          if(!G.marked[destination]){
            System.out.println("There is no route from " + cityNames[source]
                                + " to " + cityNames[destination]);
          } else {
            Stack<Integer> path = new Stack<>();
            for (int x = destination; x != source; x = G.edgeTo[x]){
                path.push(x);
            }
            System.out.print("The shortest route from " + cityNames[source] +
                               " to " + cityNames[destination] + " has " +
                               G.distTo[destination] + " miles: ");
  
            int prevVertex = source;
            System.out.print(cityNames[source] + " ");
            while(!path.empty()){
              int v = path.pop();
              System.out.print(G.distTo[v] - G.distTo[prevVertex] + " "
                               + cityNames[v] + " ");
              prevVertex = v;
            }
            System.out.println();
  
          }
          System.out.print("Please press ENTER to continue ...");
          scan.nextLine();
        }
    }

    public void minprice() { // find cheapest price 
      if(G == null){
        System.out.println("Please import a graph first (option 1).");
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      } else {
        for(int i=0; i<cityNames.length; i++){
          System.out.println(i+1 + ": " + cityNames[i]);
        }
        System.out.print("Please enter source city (1-" + cityNames.length + "): ");
        int source = Integer.parseInt(scan.nextLine());
        System.out.print("Please enter destination city (1-" + cityNames.length + "): ");
        int destination = Integer.parseInt(scan.nextLine());
        source--;
        destination--;
        G.dijkstrasP(source, destination);
        if(!G.marked[destination]){
          System.out.println("There is no route from " + cityNames[source]
                              + " to " + cityNames[destination]);
        } else {
          Stack<Integer> path = new Stack<>();
          for (int x = destination; x != source; x = G.edgeTo[x]){
              path.push(x);
          }
          System.out.print("The cheapest route from " + cityNames[source] +
                             " to " + cityNames[destination] + " has " +
                             G.priceTo[destination] + " dollar: ");

          int prevVertex = source;
          System.out.print(cityNames[source] + " ");
          while(!path.empty()){
            int v = path.pop();
            System.out.print(G.priceTo[v] - G.priceTo[prevVertex] + " "
                             + cityNames[v] + " ");
            prevVertex = v;
          }
          System.out.println();

        }
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      }
  }


  public void hops() { // find least hops
    if(G == null){
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    } else {
      for(int i=0; i<cityNames.length; i++){
        System.out.println(i+1 + ": " + cityNames[i]);
      }
      System.out.print("Please enter source city (1-" + cityNames.length + "): ");
      int source = Integer.parseInt(scan.nextLine());
      System.out.print("Please enter destination city (1-" + cityNames.length + "): ");
      int destination = Integer.parseInt(scan.nextLine());
      source--;
      destination--;
      G.bfs(source);
      if(!G.marked[destination]){
        System.out.println("There is no route from " + cityNames[source]
                            + " to " + cityNames[destination]);
      } else {
        
        Stack<Integer> stack = new Stack<Integer>();

        

        for(int i =destination;i!=source;i=G.edgeTo[i])
        {
          
          stack.push(i);

        }
          
      System.out.print("The shortest route from " + cityNames[source] +
      " to " + cityNames[destination] + " has " +
      G.distTo[destination] + " hop(s): ");
      System.out.print(cityNames[source] + " ");
      while(!stack.empty())
      {
        int city=stack.pop();
        System.out.print(cityNames[city] + " ");
      }

      System.out.println();
    }
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    }

  }

  public void search() // Find the airlines that lower than the specific amount of money
  {
    if(G==null)
    {
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();

    }
    else
    {
      System.out.println("Please the enter the amount of money :");
      double money = scan.nextDouble();
      
      
      for(int i=0;i<G.v;i++)
      {
        G.solve(i,money);  // use dfs to find;

      }

      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();

     
    }

   

  }


  public void add()// add new path
  {
    System.out.println("Please enter one of vertices of the cities you want to add");
    int from = scan.nextInt();

    System.out.println("Please enter another vertices of the cities you want to add");

    int to = scan.nextInt();

    System.out.println("Please enter weight");

    int weight = scan.nextInt();

    System.out.println("Please enter price");

    double price = scan.nextDouble();
    G.addEdge(new WeightedDirectedEdge(from-1, to-1, weight,price));
    G.addEdge(new WeightedDirectedEdge(to-1, from-1, weight, price));

    System.out.print("Please press ENTER to continue ...");
      scan.nextLine();


  }

  public void remove()// move the path between the 2 cities
  {
    System.out.println("Please enter one of vertices of the cities you want to remove");
    int from = scan.nextInt();

    System.out.println("Please enter another vertices of the cities you want to remove");

    int to = scan.nextInt();

    

    
    
    if(!G.removeEdge(from-1, to-1))
    {
      System.out.println("Remove Not Success");// if these is no path, print remove not success
      
    }
    if(!G.removeEdge(to-1, from-1))
    {
      System.out.println("Remove Not Success:");
    }

    System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    

  }

  public void exit() throws IOException   
  {
    File file =new File(fileName);
    FileWriter file2=new FileWriter(file);

    BufferedWriter writer= new BufferedWriter(file2);// write file;
   
    writer.write(G.v + "");
    writer.newLine();
    for(int i=0;i<G.v;i++)
    {
      writer.write(cityNames[i]);
      writer.newLine();
    }

    for(int i=0;i<G.v;i++)
    {
      for(WeightedDirectedEdge edge:G.adj[i])
      {
        if(edge.from()<edge.to())
        {
          writer.write((edge.from()+1)+" "+ (edge.to()+1)+" "+edge.weight()+" "+edge.price());
          writer.newLine();
        }
      }
    }

    writer.close();

    System.exit(0);// exit the system

    



  }


  public class Digraph {  // graph class
    public final int v;
    public  int e;
    public LinkedList<WeightedDirectedEdge>[] adj;
    public boolean[] marked;  // marked[v] = is there an s-v path
    public  int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    public int[] distTo;      // distTo[v] = number of edges shortest s-v path
    public double[] priceTo;// priceTo[v] = price of shortest s-v path
    


    /**
    * Create an empty digraph with v vertices.
    */
    public Digraph(int v) {
      if (v < 0) throw new RuntimeException("Number of vertices must be nonnegative");
      this.v = v;
      this.e = 0;
      
      @SuppressWarnings("unchecked")
      LinkedList<WeightedDirectedEdge>[] temp =
      (LinkedList<WeightedDirectedEdge>[]) new LinkedList[v];
      adj = temp;
      for (int i = 0; i < v; i++)
        {
          adj[i] = new LinkedList<WeightedDirectedEdge>();// adjacency list
          
        }

        
    }

    /**
    * Add the edge e to this digraph.
    */
    public void addEdge(WeightedDirectedEdge edge) {  // add path between the cities;
      
      
      
      int from = edge.from();
      for(WeightedDirectedEdge e:adj[from])
      {
        if(e.equals(edge.from(), edge.to()))  // if the path between cities already exist, update it
        {
          e.edit(edge);
          return;
          
        }
      }
      adj[from].add(edge);
      e++;
      
     
    }

    public boolean removeEdge(int from,int to)
    {
      
    for(WeightedDirectedEdge w:adj[from])      // search the path and remove it
    {
      if(w.equals(from,to))
      {
         return adj[from].remove(w);
      }
    }

    return false;
    
      
      
  }
  
  
  /**
    * Return the edges leaving vertex v as an Iterable.
    * To iterate over the edges leaving vertex v, use foreach notation:
    * <tt>for (WeightedDirectedEdge e : graph.adj(v))</tt>.
    */
    public Iterable<WeightedDirectedEdge> adj(int v) {
      
      return adj[v];
    }

    public void bfs(int source) {    // bfs
      marked = new boolean[this.v];
      distTo = new int[this.e];
      edgeTo = new int[this.v];

      Queue<Integer> q = new LinkedList<Integer>();
      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      q.add(source);

      while (!q.isEmpty()) {
        int v = q.remove();
        for (WeightedDirectedEdge w : adj(v)) {
          if (!marked[w.to()]) {
            edgeTo[w.to()] = v;
            distTo[w.to()] = distTo[v] + 1;
            marked[w.to()] = true;
            q.add(w.to());
          }
        }
      }
    }

    public void dijkstras(int source, int destination) //Find min path
    { 
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];


      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) {
        for (WeightedDirectedEdge w : adj(current)) {
          if (distTo[current]+w.weight() < distTo[w.to()]) {
          
        edgeTo[w.to()]=current;
        
        distTo[w.to()]=distTo[current]+w.weight();
          
          }
        }
        
        int min = INFINITY;
        current = -1;

        for(int i=0; i<distTo.length; i++)
        {
          if(marked[i])
            continue;
          if(distTo[i] < min){
            min = distTo[i];
            current = i;
          }
        }

        if(current==-1)
        break;

        marked[current]=true;
        nMarked++;

    
      }
    }

    public void dijkstrasP(int source, int destination) //Find min price path
    { 
      marked = new boolean[this.v];
      priceTo = new double[this.v];
      edgeTo = new int[this.v];


      for (int i = 0; i < v; i++){
        priceTo[i] = INFINITY;
        marked[i] = false;
      }
      priceTo[source] = 0;
      marked[source] = true;
      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) {
        for (WeightedDirectedEdge w : adj(current)) {
          if (priceTo[current]+w.price() < priceTo[w.to()]) {
          
        edgeTo[w.to()]=current;
        
        priceTo[w.to()]=priceTo[current]+w.price();
          
          }
        }
        //Find the vertex with minimim path distance
        //This can be done more effiently using a priority queue!
        double min = INFINITY;
        current = -1;

        for(int i=0; i<priceTo.length; i++)
        {
          if(marked[i])
            continue;
          if(priceTo[i] < min){
            min = priceTo[i];
            current = i;
          }
        }

        if(current==-1)
        break;

        marked[current]=true;
        nMarked++;

    
      }
    }

    public void solve(int s,double money)   // dfs helpher path
    {
      marked=new boolean[this.v];
     priceTo = new double[this.v];
     
      edgeTo = new int[this.v];

      dfs(s,priceTo,money,s);

    }
    
    private void dfs(int current,double[] priceTo,double money,int s)  // actuall dfs method;
    {
      marked[current]=true; // mark the graph;
      
      
      for(WeightedDirectedEdge w:adj(current))
      {
        if(!marked[w.to()]&&(priceTo[current]+w.price())<=money)
        {
          priceTo[w.to()]=priceTo[current]+w.price();
          System.out.println("The cost from " + cityNames[s] +
          " to " + cityNames[w.to()] + " has " +
          priceTo[w.to()]+ " dollar: ");
          
          dfs(w.to(),priceTo,money,s);

          
          
        }

        

      }

      if(current!=s)
      marked[current]=false;

    }





   
  
  
  
  }

    
}
