/*
 * This class keeps track of the Pieces that are downloaded from the peers.
 */
public class BitField {
	private final int totPieces;
	private boolean[] bitPieceIndex;
	private int countFinishedPieces;
	private boolean finished;
	
	public BitField(int totPieces){
		//Initialize here
		this.totPieces = totPieces;
		bitPieceIndex = new boolean[totPieces];
		countFinishedPieces = 0;
		finished = false;
		for(int i =0; i < totPieces; i++){
			bitPieceIndex[i] = false;
		}
	}
	/*
	 *  This function returns byte array by calculating the number of bytes required to represent 
	 *  the number of pieces and then shift bits according to the value set in the array of boolean flags
	 */
	public synchronized byte[] changeBitToByteField(){
		int noOfBytes;
		int bitIndex;
		int byteIndex;
		if(totPieces % 8 == 0)
			noOfBytes = totPieces / 8;
		else
			noOfBytes = (totPieces / 8) + 1;
		byte[] result = new byte[noOfBytes];
		for(int i =0; i < totPieces; i++){
			result[i] = (byte)0;
		}
		for(int i =0; i < totPieces; i++){
			bitIndex = i % 8;
			byteIndex = i / 8;
			if(bitPieceIndex[i] == true)
				result[byteIndex] = (byte)((1 << bitIndex) | (result[byteIndex]));
			else
				result[byteIndex] = (byte) (~(1 << bitIndex) & (result[byteIndex]));
		}
		return result;
	}
	
	//This function sets the corresponding bit field from the byte array received
	public synchronized void setBitFromByte(byte[] byteArray){
		int bitIndex;
		int byteIndex;
		countFinishedPieces = 0;
		for(int i = 0; i < totPieces; i++){
			bitIndex = i % 8;
			byteIndex = i / 8;
			//An AND operation can tell you if a bit is set. If it is not set then the result will be 0
			if(((1 << bitIndex) & (byteArray[byteIndex])) != 0) {
				bitPieceIndex[i] = true;
				countFinishedPieces++;
			}
			else {
				bitPieceIndex[i] = false;
			}
		}
		if(countFinishedPieces == totPieces)
			finished = true;
	}
	
	//This function is used to change a bit field to binary string of 0's and 1's
	public synchronized String changeBitToString(){
		String result = null;
		for(int i = 0; i < totPieces; i++){
			if(bitPieceIndex[i] == true)
				result += "1";
			else
				result += "0";
		}
		return result;
	}
	
	public synchronized boolean getFinished(){
		return finished;
	}
	
	public synchronized void setBitToTrue(int index){
		if(bitPieceIndex[index] == false){
			bitPieceIndex[index] = true;
			countFinishedPieces++;
			if(countFinishedPieces == totPieces)
				finished = true;
		}
	}
	
	public synchronized void setBitToFalse(int index){
		if(bitPieceIndex[index] == true){
			bitPieceIndex[index] = false;
			countFinishedPieces--;
			finished = false;
		}
	}
	
	public synchronized void setAllBitsTrue(){
		for(int i =0; i < totPieces; i++){
			bitPieceIndex[i] = true;
		}
		countFinishedPieces = totPieces;
		finished = true;
	}
 
	//Check if the piece is not present then return interested
	public synchronized boolean checkPiecesInterested(BitField bf){
		for(int i = 0; i < totPieces; i++){
			if((bitPieceIndex[i] == false) && (bf.bitPieceIndex[i] == true)) 
				return true;
		}
		return false;
	}
	//Check if the interested piece is downloaded then set the flag to true in bitfield array
	public synchronized int setInterestedPiece(BitField bf){
		for(int i = 0; i < totPieces; i++){
			if((bitPieceIndex[i] == false) && (bf.bitPieceIndex[i] == true)){
				bitPieceIndex[i] = true;
				countFinishedPieces++;
				if(countFinishedPieces == totPieces)
					finished = true;
				return i;
			}
		}
		return -1;
	}
	
}
