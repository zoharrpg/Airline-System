public class UF
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
        
        public boolean connected(WeightedDirectedEdge edge)
        {

          return find(edge.from())==find(edge.to());


          
          

        }

        public void union(WeightedDirectedEdge edge)
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
    