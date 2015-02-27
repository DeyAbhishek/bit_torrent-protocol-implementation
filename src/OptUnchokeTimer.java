
import java.util.Timer;

/**
 * The OptUnchokeTimer class will be used to start a timer to keep
 * track of the optimistically chosen unchoked peer every optimisticUnchokeInterval.
 * 
 * @author Abhishek
 *
 */
public class OptUnchokeTimer {
	
	Timer timer;

	public OptUnchokeTimer () {
		this.timer = new Timer();		
		timer.schedule(new OptUnchoke(), 0, PeerInfo.optimisticUnchokeInterval);  
	} 
	
	public void stop() {
		timer.cancel();
		timer.purge();
	}
}
