public class AI {
	/**
	 * Random algorithm, assumes X goes first
	 * @param XPattern Positions taken by X: bit i*3+j = 1 : position row i col j taken by X, 0 otherwise
	 * @param OPattern Positions taken by O: bit i*3+j = 1 : position row i col j taken by X, 0 otherwise 
	 * @return Pos position of next piece: 0~8
	 *   6 | 7 | 8
	 *  -----------
	 *   3 | 4 | 5
	 *  -----------
	 *   0 | 1 | 2
	 */  
	public static int RandomAlgorithm (int XPattern, int OPattern) {
		int allPattern = XPattern | OPattern;
		if( allPattern >= 0b111111111 ) return -1; // board is full
		// get a random position

		int numEmpty = 9 - GetPieceNum(allPattern);

		int numPos = (int)(Math.random() * numEmpty + 1);
		int movingBit = 1;
		int pos = 0;
		while( numPos > 0 ) {
			if( ( movingBit & allPattern ) == 0 ) { numPos--; }
			movingBit = movingBit << 1;
			pos += 1;
		}
		return pos - 1;
		
	}

	private static int GetPieceNum(int pattern) {
		int num = 0;
		for(int i=0; i<9; i++) {
			if( (1 & pattern) == 1 ){ num ++; }
			pattern = pattern >> 1;
		}
		return num;
	}
}
