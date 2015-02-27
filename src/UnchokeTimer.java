
import java.util.Timer;

/**
 * The UnchokeTimer class starts a timer to keep
 * track of the chosen unchoked peers every k seconds.
 * 
 * @author Abhishek
 *
 */
public class UnchokeTimer {
	
	Timer timer;

	public UnchokeTimer () {
		this.timer = new Timer();		
		timer.schedule(new Unchoke(), 0, PeerInfo.unchokeInterval);  
	} 
	
	public void stop() {
		timer.cancel();
		timer.purge();
	}
	
}
