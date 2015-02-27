import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/*
 * @author Abhishek
 * 
 */
public class LoggerPeer 
{
	private Timestamp logTime; 
	int peerId;
	private File file;
	private FileWriter writer;
	private BufferedWriter buffer;
	
	public LoggerPeer(int peerId) throws IOException
	{
		this.peerId = peerId;
		Date date = new Date();
		this.logTime = new Timestamp(date.getTime());
		
		file = new File("log_peer_"+peerId+".log");

		if(file.exists()) file.delete();
		file.createNewFile();
		
		try 
		{
			writer = new FileWriter(file.getAbsoluteFile());
			buffer = new BufferedWriter(writer);
		} 		
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
	} 
	
	public synchronized void writeToFile(String content)
	{
		try 
		{
			buffer.write(content);
			buffer.flush();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
	}
	
	public synchronized void close()
	{
		try 
		{
			buffer.close();
			writer.close();
		} 
		catch (IOException e) 
		{
		
			e.printStackTrace();
		} 
		
	} 
	
	public synchronized String getTime()
	{
		Date date = new Date();
		this.logTime.setTime(date.getTime());
		return logTime.toString();
	} 
	
	public synchronized void tcpConnectionEstablishedLog(int peer_2)
	{
	String str = getTime() + ": Peer " + peerId + " makes a connection to Peer "+ peer_2;
	writeToFile(str);
	}
	
	public synchronized void tcpConnedtedLog(int peer_2)
	{
		String str = getTime() + ": Peer " + peerId + " is connected from Peer "+ peer_2;
		writeToFile(str);
	}
	
	public synchronized void changeOfPreferredNeighbourLog(int[] peers)
	{
		String peersArr = "";
		for(int peer : peers){
		peersArr = peersArr + peer + ", ";
		}
		String str = getTime() + ": Peer " + peerId + " has the preferred neighbours "+ peersArr;
		writeToFile(str);
	}
	
	public synchronized void changeOfOptUnchokedNeighbourLog(int peer_2)
	{
		String str = getTime() + ": Peer " + peerId + " has the optimistically unchoked neighbour "+ peer_2;
		writeToFile(str);
	}
	
	public synchronized void unchokeLog(int peer_2)
	{
		String str = getTime() + ": Peer " + peerId + " is unchoked by the "+ peer_2;
		writeToFile(str);
	}
	
	public synchronized void chokeLog(int peer_2)
	{
		String str = getTime() + ": Peer " + peerId + " is choked by "+ peer_2;
		writeToFile(str);
	}
	
	public synchronized void haveLog(int peer_2, int pieceIndex)
	{
		String str = getTime() + ": Peer " + peerId + " received the 'have' message from "+ peer_2 +  " for the piece " + pieceIndex;
		writeToFile(str);
	}
	
	public synchronized void interestedLog(int peer_2)
	{
		String str = getTime() + ": Peer " + peerId + " received the 'interested' message from "+ peer_2;
		writeToFile(str);
	}
	
	public synchronized void notInterestedLog(int peer_2)
	{
		String str = getTime() + ": Peer " + peerId + " received the 'not interested' message from "+ peer_2;
		writeToFile(str);
	}
	
	public synchronized void downloadingLog(int peer_2, int pieceIndex)
	{
		String str = getTime() + ": Peer " + peerId + " has downloaded the piece " + pieceIndex + " from " + peer_2;
		writeToFile(str);
	}
	
	public synchronized void completeDownloadLog()
	{
		String str = getTime() + ": Peer " + peerId + " has downloaded the complete file";
		writeToFile(str);
	}
	}
