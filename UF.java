public class UF  // Union-Find data structure
      {
        public int count;
        public int[] id;

        
        
        public UF(int n)
        {
          count=n;
          id = new int[n];
          for(int i=0;i<n;i++)
          {
            id[i]=i;
          }
        }
        public int find(int p)
        {
            return id[p];
        }
        
        public boolean connected(WeightedDirectedEdge edge)  // check wether connected
        {

          return find(edge.from())==find(edge.to());


          
          

        }

        public void union(WeightedDirectedEdge edge)  // union method
        {
            int pID=find(edge.from());
            int qID=find(edge.to());
            
            if(pID==qID)
            return;

            for(int i=0;i<id.length;i++)
            {
                if(id[i]==pID)
                id[i]=qID;

               
            }
        


        }
    

      }
