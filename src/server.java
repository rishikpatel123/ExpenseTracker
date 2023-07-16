
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.net.*;
    public class server {
        public static void main(String[] args)throws IOException {
            ServerSocket s1=new ServerSocket(1234);
            Socket s2 = s1.accept();
            Scanner sc= new Scanner(s2.getInputStream());
            String a=sc.nextLine();
            String b= a+" is OK";

            PrintStream p = new PrintStream(s2.getOutputStream());
            p.println(b);

        }
        }
