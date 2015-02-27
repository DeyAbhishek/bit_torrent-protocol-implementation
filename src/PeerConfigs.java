import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

public class PeerConfigs {
	private FileInputStream in = null;
	private Reader streamReader;
	private StreamTokenizer tokenizer;
	private int prefNeighbors;
	private int timeUnchoke;
	private int timeOptUnchoke;
	private String fileName;
	private int sizeOfFile;
	private int sizeOfPiece;
	private int sizeOfLastPiece;
	private int totalPieces;
	private int totalPeers;
	private ArrayList<Integer> peerList;
	private ArrayList<String> hostList;
	private ArrayList<Integer> downloadPortList;
	private ArrayList<Integer> uploadPortList;
	private ArrayList<Boolean> hasWholeFile;
	private ArrayList<Integer> havePortList;
	
	public PeerConfigs(String commonConfig, String peerConfig) throws FileNotFoundException
	{
		String path = System.getProperty("user.home") + "/project/"; 
		readCommonConfig(path + commonConfig);
		readPeerInfoConfig(path + peerConfig);
	}
	
	public int getPrefNeighbors(){
		 return prefNeighbors;
	}
	public int getTimeUnchoke(){
		return timeUnchoke;
	}
	public int getTimeOptUnchoke(){
		return timeOptUnchoke;
	}
	public String getFileName(){
		return fileName;
	}
	public int getSizeOfFile(){
		return sizeOfFile;
	}
	public int getSizeOfPiece(){
		return sizeOfPiece;
	}
	public int getSizeOfLastPiece(){
		return sizeOfLastPiece;
	}
	public int getTotalPieces(){
		return totalPieces;
	}
	public int getTotalPeers(){
		return totalPeers;
	}
	public ArrayList<Integer> getPeerList(){
		return peerList;
	}
	public ArrayList<String> getHostList(){
		return hostList;
	}
	public ArrayList<Integer> getDownPortList(){
		return downloadPortList;
	}
	public ArrayList<Integer> getUpPortList(){
		return uploadPortList;
	}
	public ArrayList<Integer> getHavePortList(){
		return havePortList;
	}
	public ArrayList<Boolean> getHasWholeFile(){
		return hasWholeFile;
	}
		
	public void readCommonConfig(String commonConfigPath) throws FileNotFoundException
	{
		try{
			int token;
			
			in = new FileInputStream(commonConfigPath);	
			streamReader = new BufferedReader(new InputStreamReader(in));
			tokenizer = new StreamTokenizer(streamReader);
			tokenizer.eolIsSignificant(true);
			
			boolean eof = false;
		
			// parse tokens from the file until the end of the file is hit
	        do {
	            token = tokenizer.nextToken();
	            switch (token) {
	               case StreamTokenizer.TT_EOF:
	                  eof = true;
	                  break;
	               case StreamTokenizer.TT_EOL:
	                  break;
	               case StreamTokenizer.TT_WORD:
	            	   if(tokenizer.sval.compareToIgnoreCase("NumberOfPreferredNeighbors") == 0)
	            	   {
	            		   token = tokenizer.nextToken();
	            		   if(token == StreamTokenizer.TT_NUMBER)
	            			   prefNeighbors = (int) tokenizer.nval;
	            	   }
	            	   else if(tokenizer.sval.compareToIgnoreCase("UnchokingInterval") == 0)
	            	   {
	            		   token = tokenizer.nextToken();
	            		   if(token == StreamTokenizer.TT_NUMBER)
	            			   timeUnchoke = (int) tokenizer.nval;
	            	   }
	            	   else if(tokenizer.sval.compareToIgnoreCase("OptimisticUnchokingInterval") == 0)
	            	   {
	            		   token = tokenizer.nextToken();
	            		   if(token == StreamTokenizer.TT_NUMBER)
	            			   timeOptUnchoke = (int) tokenizer.nval;
	            	   }
	            	   else if(tokenizer.sval.compareToIgnoreCase("FileName") == 0)
	            	   {
	            		   token = tokenizer.nextToken();
	            		   if(token == StreamTokenizer.TT_WORD)
	            			   fileName = tokenizer.sval;
	            	   }
	            	   else if(tokenizer.sval.compareToIgnoreCase("FileSize") == 0)
	            	   {
	            		   token = tokenizer.nextToken();
	            		   if(token == StreamTokenizer.TT_NUMBER)
	            			   sizeOfFile = (int) tokenizer.nval;
	            	   }
	            	   else if(tokenizer.sval.compareToIgnoreCase("PieceSize") == 0)
	            	   {
	            		   token = tokenizer.nextToken();
	            		   if(token == StreamTokenizer.TT_NUMBER)
	            			   sizeOfPiece = (int) tokenizer.nval;
	            	   }
	            	   else
	            	   {
	            		   System.out.println("Error.  Invalid string found in the common configuration file.");
	            		   System.exit(-1);
	            	   }
	            	   break;
	               default:
	               {
	            	   System.out.println("Error.  Invalid parameter name.");   
	            	   System.exit(-1);
	               }
	            }
	         } while (!eof);

			if(in != null)
				in.close();
			
			totalPieces = sizeOfFile/sizeOfPiece;
			sizeOfLastPiece = sizeOfFile % sizeOfPiece;
			
			//printCommonSettings();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	public void readPeerInfoConfig(String peerConfigPath) throws FileNotFoundException
	{
		try{
			int currByte = 0;
			String currString = "";
			int stringNum = 1;
			totalPeers = 0;
			
			peerList = new ArrayList<Integer>(totalPeers);
			hostList = new ArrayList<String>(totalPeers);
			downloadPortList = new ArrayList<Integer>(totalPeers);
			uploadPortList = new ArrayList<Integer>(totalPeers);
			hasWholeFile = new ArrayList<Boolean>(totalPeers);
			havePortList = new ArrayList<Integer>(totalPeers);
			
			in = new FileInputStream(peerConfigPath);
			streamReader = new BufferedReader(new InputStreamReader(in));
			tokenizer = new StreamTokenizer(streamReader);
			
			int token;
			int tokenNum = 0;
			int lineNum = 0;
			int modResult;
			
			boolean eof = false;
			
			// parse tokens from the file until the end of the file is hit
	        do {
	            token = tokenizer.nextToken();
	            ++tokenNum;
	            lineNum = tokenNum/4;
	            
	            modResult = tokenNum % 4;
	            
	            switch (token) {
	               case StreamTokenizer.TT_EOF:
	                  eof = true;
	                  break;
	               case StreamTokenizer.TT_EOL:
	            	   --tokenNum;
	                  break;
	               case StreamTokenizer.TT_WORD:
	            	   if(modResult == 2) // token is the peer's host name
	            	   {
	            		   lineNum = (tokenNum-1)/4;
	            		   hostList.add(lineNum, tokenizer.sval);	
	            	   }
	            	   else
	            	   {
	            		   System.out.println("Error:  The host name in the wrong location.");
	            		   System.exit(-1);
	            	   }   
	                  break;
	               case StreamTokenizer.TT_NUMBER:
	            	   if(modResult == 1) // token is the peer's peerID
	            	   {
	            		   lineNum = (tokenNum-1)/4;	            		   
	            		   peerList.add(lineNum, (int) tokenizer.nval);	
	            	   }
	            	   else if(modResult == 3) // token is the peer's port #
	            	   {
	            		   if(tokenizer.nval < 0 || tokenizer.nval > 65535)
	            		   {
	            			   System.out.println("Error:  Invalid port specified.");
	            			   System.exit(-1);
	            		   }
	            		   
	            		   downloadPortList.add(lineNum, (int) tokenizer.nval);
	            		   uploadPortList.add(lineNum, (int) tokenizer.nval);
	            		   havePortList.add(lineNum, (int) tokenizer.nval);
	            	   }
	            	   else if(modResult == 0) // token indicates whether the peer has the whole file
	            	   {
	            		   ++totalPeers;
	            		   if(tokenizer.nval == 0)
	            			   hasWholeFile.add(false);
	            		   else if(tokenizer.nval == 1)
	            			   hasWholeFile.add(true);
	            		   else
	            		   {
	            			   System.out.println("Error:  You must specify either a 0 or 1 to denote if the peer has the entire file.");
	            			   System.exit(-1);
	            		   }
	            	   }
	            	   else
	            	   {
	            		   System.out.println("Error:  Number argument in wrong location in file.");    		   
	            		   System.exit(-1);
	            	   }
	                  break;
	               default:
	            	   break;
	            }
	         } while (!eof);
			
			if(in != null)
				in.close();
			
			if(totalPeers == 0)
			{
     		   System.out.println("Error:  No peer information in the configuration file.");    		   
     		   System.exit(-1);	
			}
			else
				printPeerInfo(totalPeers);
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	// print info read from Common.cfg
	public void printCommonSettings()
	{
		System.out.printf("# Preferred neighbors: %d\nUnchoking Interval: %d\n"
				+ "Optimistic Unchoking Interval: %d\n"
				+ "File Name: %s\nFile size: %d\nPiece size: %d\n", prefNeighbors,
				timeUnchoke, timeOptUnchoke, fileName, sizeOfFile, sizeOfPiece);
	}
	
	// print info read from PeerInfo.cfg
	public void printPeerInfo(int totalPeers)
	{
		int i = 0;
		
		System.out.printf("\nTotal # peers: %d\n", totalPeers);
		System.out.println("\nPeer List:");
		
		int wholeFile = 0;
		
		while(i < totalPeers)
		{
			if(hasWholeFile.get(i) == true)
				wholeFile = 1;
			else
				wholeFile = 0;
			
			System.out.printf("\nPeer #: %d  Host Name: %s  UploadPort: %d  DownloadPort: %d  "
					+ "hasWholeFile: %d  HavePortList: %d", peerList.get(i), hostList.get(i),
			downloadPortList.get(i), uploadPortList.get(i), wholeFile, havePortList.get(i));
			++i;
		}
	}
	
	public static void main(String [] args) throws FileNotFoundException
	{	
		PeerConfigs config = new PeerConfigs("Common.cfg", "PeerInfo2.cfg");
	}
}
