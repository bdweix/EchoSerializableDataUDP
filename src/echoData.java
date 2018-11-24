import java.io.Serializable;


public class echoData implements Serializable
{
  /**
   *
   */
  //private static final long serialVersionUID = 1L;
  private String name, data;

  public echoData(String n, String d)
  {
    this.name = n;
    this.data = d;
  }
  public String getName()
  {
    return name;
  }

  public void setName(String n)
  {
    this.name = n;
  }

  public String getData()
  {
    return data;
  }

  public void setData(String d)
  {
    this.data = d;
  }
  public void printIt(){
    System.out.println("Name: " + name + " data: " + data);
  }
  public String toString(){
    return ("Name: " + name + " data: " + data);
  }
}