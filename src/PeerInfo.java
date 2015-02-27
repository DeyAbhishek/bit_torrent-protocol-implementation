import java.util.ArrayList;

/*
*Added this dummy class as of now since I needed this class in the OptChoke class.  --Abhishek
*/

public class PeerInfo {

	protected static int unchokeInterval;
	protected static int optimisticUnchokeInterval;	
	public static ArrayList <Integer>  chokedInterested;  //List of neighbours who would be interested. (Used in OptChoke class)
	public static int optimisticUnchokedPeer;
	public static LoggerPeer log;
	public static OptUnchokeTimer optUnchokeTimer;
}
