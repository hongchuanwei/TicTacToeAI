public class AI {
	private static int MIN_SCORE = -10;
	private static int MAX_SCORE = 10;
	
	/**
	 * Random algorithm, assumes X goes first
	 * @param XPattern Positions taken by X: bit i*3+j = 1 : position row i col j taken by X, 0 otherwise
	 * @param OPattern Positions taken by O: bit i*3+j = 1 : position row i col j taken by O, 0 otherwise 
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


	
	/**
	 * MiniMax algorithm, AI plays O
	 * @param XPattern Positions taken by X: bit i*3+j = 1 : position row i col j taken by X, 0 otherwise
	 * @param OPattern Positions taken by O: bit i*3+j = 1 : position row i col j taken by O, 0 otherwise
	 * @return position of next piece: 0~8
	 */
	public static int MiniMaxAlgorithm(int XPattern, int OPattern) {
		int allPattern = XPattern | OPattern;
		int movingBit = 1;
        int maxScore = Integer.MIN_VALUE;
		int pos = 1;
		int bestPos = -1;
		// get all moves scores for pos not set
		for(int i =0; i<9; i++) {
			if( (allPattern & movingBit) == 0 ) {
				int score = GetScore( XPattern, OPattern | movingBit, true);
				if(score > maxScore ) {
					maxScore = score;
					bestPos = pos;
				}
			}
			movingBit = movingBit << 1;
			pos ++;
		}
		return bestPos - 1;
	}
	/**
	 * Helper function that gets the score of a single move, AI is O
	 * @param XPattern Positions taken by X: bit i*3+j = 1 : position row i col j taken by X, 0 otherwise
	 * @param OPattern Positions taken by O: bit i*3+j = 1 : position row i col j taken by O, 0 otherwise
	 * @param Xturn if current move is made by X
	 * @return score by current move
	 */
	private static int GetScore(int XPattern, int OPattern, boolean Xturn) {
		// base cases
		if(Board.IsWinner(XPattern)) { return MIN_SCORE; }
		if(Board.IsWinner(OPattern)) { return MAX_SCORE; }
		if(Board.IsDraw( XPattern|OPattern )) { return 0; }
		
		int allPattern = XPattern | OPattern;
		int movingBit = 1;
        int[] scores = new int[9];
		// get all moves scores for pos not set
		for(int i =0; i<9; i++) {
			if( (allPattern & movingBit) == 0 ) {
				// update score
				if(Xturn) {
					scores[i] = GetScore( XPattern | movingBit, OPattern, false);
				} else {
					scores[i] = GetScore( XPattern, OPattern | movingBit, true);
				}
			}
			movingBit = movingBit << 1;
		}

		if(Xturn) {
			return minValue( scores );
		} else {
			return maxValue( scores );
		}
	}

	private static int GetPieceNum(int pattern) {
		int num = 0;
		for(int i=0; i<9; i++) {
			if( (1 & pattern) == 1 ){ num ++; }
			pattern = pattern >> 1;
		}
		return num;
	}

	/**
	 * @param integers
	 * @return the max value in the array of integers
	 */
	private static int maxValue(int[] integers) {
		int max = integers[0];
		for (int i = 0; i < integers.length; i++) {
			if (integers[i] > max) {
				max = integers[i];
			}
		}
		return max;
	}

	/**
	 * @param integers
	 * @return the min value in the array of integers
	 */
	private static int minValue(int[] integers) {
		int min = integers[0];
		for (int i = 0; i < integers.length; i++) {
			if (integers[i] < min) {
				min = integers[i];
			}
		}
		return min;
	}
}
