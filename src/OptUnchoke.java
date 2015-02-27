import java.io.IOException;
import java.util.*;

/**
 * The OptUnchoke class is used in the OptUnchokeTimer.  It 
 * updates the optimistically unchoked peer.
 *
 *@author Abhishek
 *
 */
public class OptUnchoke extends TimerTask {

	private int peerId;

	@Override
    public void run() 
	{
		if (!PeerInfo.chokedInterested.isEmpty()) {
			Random randomGenerator = new Random();
			int index = randomGenerator.nextInt(PeerInfo.chokedInterested.size());
			PeerInfo.optimisticUnchokedPeer = PeerInfo.chokedInterested.get(index);
			
			Message m = new Message ();
			m.setType(Message.unchoke);

			//Now Send Unchoke Message to OptimisticUnchokedPeer. (This part is still left to be added.)
				
			try{
				LoggerPeer log = new LoggerPeer(peerId);

				log.changeOfOptUnchokedNeighbourLog(PeerInfo.optimisticUnchokedPeer);
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		else {
			PeerInfo.optimisticUnchokedPeer = -1;
		}
		
	}
}
