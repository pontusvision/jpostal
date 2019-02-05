import com.pontusvision.jpostal.AddressParser;
import com.pontusvision.jpostal.ParsedComponent;

import java.util.Scanner;

public class PVJpostal
{
  public static void main(String[] args)
  {
    if (args.length < 1)
    {
      System.out.println("Usage:\n\n   java -jar pontus-jpostal-1.0.0-SNAPSHOT.jar <address string> [<data dir>]\n\n"
          + "Note: if <address string> is a dash (-), the addresses will be read from stdin until an EOF");
      System.exit(0);
    }

    AddressParser parser = args.length == 2? AddressParser.getInstanceDataDir(args[1]):  AddressParser.getInstance();
    StringBuffer sb = new StringBuffer();

    if ("-".equals(args[0])){

      System.out.println("Please enter an address:");

      Scanner input = new Scanner(System.in);
      while (input.hasNextLine()){

        sb.setLength(0);
        String address = input.nextLine();


        System.out.println(address);

        parseData(parser,address, sb);

        System.out.println(sb.toString());
        System.out.println("\n\n\nPlease enter an address:");

      }

    }
    else
    {
      sb.setLength(0);

      parseData(parser,args[0], sb);

      System.out.println(sb.toString());

    }

  }
  public static void parseData(AddressParser parser, String address, StringBuffer sb){
    ParsedComponent[] res = parser.parseAddress(address);

    for (int i = 0, ilen = res.length; i < ilen; i++)
    {
      String label = res[i].getLabel();
      String value = res[i].getValue();
      sb.append("\n").append(label).append(":").append(value);

    }

  }
}
