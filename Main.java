import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {  // main function;
        AirlineSystem airline = new AirlineSystem();
        
        while(true){
          switch(airline.menu()){  // option for the cases
            case 1:
              airline.readGraph();
              break;
            case 2:
              airline.printGraph();
              break;
              case 3:
              airline.printsptree();
              break;
              case 4:
              airline.shortestDistance();
              break;
              case 5:
              airline.minprice();
              break;
              case 6:
              airline.hops();
              break;
              case 7:
              airline.search();
              break;
              case 8:
              airline.add();
              break;
              case 9:
             airline.remove();
             break;
             case 10:
             airline.exit();
            default:
            System.out.println("Incorrect option.");
            break;
          }
        }
      }
    
}
