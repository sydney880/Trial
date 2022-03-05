import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
import java.net.*;

public class FileViewer extends MIDlet implements CommandListener
{
  private Display display;	    // Reference to Display object
  private TextBox tbViewer;  // View file contents in a textbox
  private Command cmView;    // Command to view file
  private Command cmExit;    // Command to exit
  private String url = "http://www.m-indya.com/index.html";  

  public FileViewer()
  { 
    display = Display.getDisplay(this);
    
    // Define commands
    cmView = new Command("View", Command.SCREEN, 2);
    cmExit = new Command("Exit", Command.EXIT, 1);

    tbViewer = new TextBox("Viewer", "", 250, TextField.ANY);
    tbViewer.addCommand(cmView);
    tbViewer.addCommand(cmExit);
    tbViewer.setCommandListener(this);    




//Number of threads = Number of available Cores * (1 + wait time /service time)
//long time = System.nanoTime();
  }

  public void startApp()
  {
    display.setCurrent(tbViewer);
  }    

  private void viewFile() throws IOException
  {
    HttpConnection http = null;
    InputStream iStrm = null;
    
    try
    {
      // Create the connection
      http = (HttpConnection) Connector.open(url);
      
      //----------------
      // Client Request
      //----------------
      // 1) Send request method
      http.setRequestMethod(HttpConnection.GET);
      
      // 2) Send header information (this header is optional)
      http.setRequestProperty("User-Agent", "Profile/MIDP-1.0 Configuration/CLDC-1.0");
      
      // 3) Send body/data - No data for this request
      
      //----------------
      // Server Response
      //----------------
      // 1) Get status Line
      System.out.println("Msg: " + http.getResponseMessage());                  
      System.out.println("Code: " + http.getResponseCode());                
      
      // 2) Get header information 
      if (http.getResponseCode() == HttpConnection.HTTP_OK)
      {
        // 3) Get data (show the file contents)
        iStrm = http.openInputStream();
        int length = (int) http.getLength();
        if (length > 0)
        {
          byte serverData[] = new byte[length];
          iStrm.read(serverData);
          tbViewer.setString(new String(serverData));
        }
      }
    }
    finally
    {
      // Clean up
      if (iStrm != null)
        iStrm.close();
      if (http != null)
        http.close();
    }
  }

  public void pauseApp()
  { }

  public void destroyApp(boolean unconditional)
  { }

  public void commandAction(Command c, Displayable s)
  {
    if (c == cmView)
    {
      try
      {
        viewFile();
      }
      catch (Exception e)
      {
        System.out.println(e.toString());
      }
    }
    else if (c == cmExit)
    {
      destroyApp(false);
      notifyDestroyed();
    }	
  }
}
