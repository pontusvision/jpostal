import com.pontusvision.jpostal.AddressParser;
import com.pontusvision.jpostal.ParsedComponent;

public class PVJpostal
{
  public static void main(String[] args)
  {
    if (args.length < 1)
    {
      System.out.println("Usage:\n\n   java -jar pontus-jpostal-1.0.0-SNAPSHOT.jar <address string> [<data dir>]");
      System.exit(0);
    }

    AddressParser parser = args.length == 2? AddressParser.getInstanceDataDir(args[1]):  AddressParser.getInstance();


    ParsedComponent[] res =  parser.parseAddress(args[0]);
    StringBuffer sb = new StringBuffer();

    for (int i = 0, ilen = res.length; i < ilen; i++)
    {
      String label = res[i].getLabel();
      String value = res[i].getValue();
      sb.append("\n").append(label).append(":").append (value);

    }

    System.out.println (sb.toString());



  }
}
