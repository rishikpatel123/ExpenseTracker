import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.net.*;
public class client {
    public static void main(String[] args) throws IOException {
        Scanner sc=new Scanner((System.in));
        Socket s= new Socket("127.0.0.1",1234);
        Scanner sc1=new Scanner(s.getInputStream());
        System.out.println("enter message");
        String a= sc.nextLine();
        PrintStream p= new PrintStream(s.getOutputStream());
        p.println(a);
        String  b=sc1.nextLine();
        System.out.println(b);
    }
}
