package edu.wm.cs.cs301.game2048;

import java.util.Arrays;
import java.util.Random;

public class State implements GameState {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(array);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (!Arrays.equals(array, other.array))
			return false;
		return true;
	}

	int[] array = {0, 0, 0, 0,
			       0, 0, 0, 0,
			       0, 0, 0, 0,
			       0, 0, 0, 0};
	
	public State() {
		// TODO Auto-generated constructor stub
		
	}
	
	public State(State currentState) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getValue(int x, int y) { // (row, column)
		// TODO Auto-generated method stub
		
		return array[(4 * y) + x];
	}

	@Override
	public void setValue(int x, int y, int value) {
		// TODO Auto-generated method stub
		
		//check if value is 0 or power of 2?
		array[(4 * y) + x] = value;
	}

	@Override
	public void setEmptyBoard() {
		// TODO Auto-generated method stub
		int empty_array[] = {0, 0, 0, 0,
			       		   0, 0, 0, 0,
			       		   0, 0, 0, 0,
			       		   0, 0, 0, 0};
		
		array = empty_array;
	}

	@Override
	public boolean addTile() {
		// TODO Auto-generated method stub
		Random random = new Random();
		int random_value = (int) Math.pow(2, random.nextInt(2) + 1);
		int random_tile = random.nextInt(16);
		
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0) {
				break;
			}
			if (i == array.length - 1) {
				return false;
			}
		}
		
		while (array[random_tile] != 0) {
			random_tile = random.nextInt(16);
		}
		
		array[random_tile] = random_value;
		
		return true;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0) {
				break;
			}
			if (i == array.length - 1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canMerge() {
		// TODO Auto-generated method stub		
		// for row merges
		for (int y = 0; y < 4; y++) {
			int prev_val = getValue(0, y); // (column, row)
			
			for (int x = 1; x < 4; x++) {
				if (getValue(x, y) == prev_val && getValue(x, y) != 0) {
					return true;
				} else {
					prev_val = getValue(x,y);
				}
			}
		}
		
		// for column merges
		for (int x = 0; x < 4; x++) {
			int prev_val = getValue(x, 0);
			
			for (int y = 1; y < 4; y++) {
				if (getValue(x, y) == prev_val && getValue(x, y) != 0) {
					return true;
				} else {
					prev_val = getValue(x,y);
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean reachedThreshold() {
		// TODO Auto-generated method stub
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (getValue(x, y) >= 2048) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int left() {
		// TODO Auto-generated method stub
		int points = 0;

		// push to the left and then check if merge
		for (int max_shifts = 0; max_shifts < 4; max_shifts++) {
			for (int y = 0; y < 4; y++) {
				int prev_val = getValue(0, y);
				
				for (int x = 1; x < 4; x++) {
					if (getValue(x, y) != 0 && prev_val == 0) {
						setValue(x-1, y, getValue(x, y));
						setValue(x, y, 0);	
					}
					prev_val = getValue(x, y);
				}
			}
		}
		if (canMerge()) {
			for (int y = 0; y < 4; y++) {
				int prev_val = getValue(0, y);
				
				for (int x = 1; x < 4; x++) {
					if (getValue(x, y) == prev_val) {
						setValue(x-1, y, prev_val * 2);
						setValue(x, y, 0);
						points = points + (prev_val * 2);
					} 
					prev_val = getValue(x, y);
				}
			}
		}
		
		for (int y = 0; y < 4; y++) {
			int prev_val = getValue(0, y);
			
			for (int x = 1; x < 4; x++) {
				if (getValue(x, y) != 0 && prev_val == 0) {
					setValue(x-1, y, getValue(x, y));
					setValue(x, y, 0);	
				}
				prev_val = getValue(x, y);
			}
		}
//		System.out.println(Arrays.toString(array));
		return points;
	}

	@Override
	public int right() {
		// TODO Auto-generated method stub
		int points = 0;

		// push to the right and then check if merge
		for (int max_shifts = 0; max_shifts < 4; max_shifts++) {
			for (int y = 0; y < 4; y++) {
				int prev_val = getValue(3, y);
				
				for (int x = 2; x >= 0; x--) {
					if (getValue(x, y) != 0 && prev_val == 0) {
						setValue(x+1, y, getValue(x, y));
						setValue(x, y, 0);	
					}
					prev_val = getValue(x, y);
				}
			}
		}
		if (canMerge()) {
			for (int y = 0; y < 4; y++) {
				int prev_val = getValue(3, y);
				
				for (int x = 2; x >= 0; x--) {
					if (getValue(x, y) == prev_val) {
						setValue(x+1, y, prev_val * 2);
						setValue(x, y, 0);
						points = points + (prev_val * 2);
					} 
					prev_val = getValue(x, y);
				}
			}
		}
		for (int y = 0; y < 4; y++) {
			int prev_val = getValue(3, y);
			
			for (int x = 2; x >= 0; x--) {
				if (getValue(x, y) != 0 && prev_val == 0) {
					setValue(x+1, y, getValue(x, y));
					setValue(x, y, 0);	
				}
				prev_val = getValue(x, y);
			}
		}
//		System.out.println(Arrays.toString(array));
		return points;

	}

	@Override
	public int down() {
		// TODO Auto-generated method stub
		int points = 0;

		// push to the left and then check if merge
		for (int max_shifts = 0; max_shifts < 4; max_shifts++) {
			for (int x = 0; x < 4; x++) {
				int prev_val = getValue(x, 3);
				
				for (int y = 2; y >= 0; y--) {
					if (getValue(x, y) != 0 && prev_val == 0) {
						setValue(x, y+1, getValue(x, y));
						setValue(x, y, 0);	
					}
					prev_val = getValue(x, y);
				}
			}
			
			
		}
		
		if (canMerge()) {
			for (int x = 0; x < 4; x++) {
				int prev_val = getValue(x, 3);
				
				for (int y = 2; y >= 0; y--) {
					if (getValue(x, y) == prev_val) {
						setValue(x, y+1, prev_val * 2);
						setValue(x, y, 0);
						points = points + (prev_val * 2);
					} 
					prev_val = getValue(x, y);
				}
			}
		}
		
		for (int x = 0; x < 4; x++) {
			int prev_val = getValue(x, 3);
			
			for (int y = 2; y >= 0; y--) {
				if (getValue(x, y) != 0 && prev_val == 0) {
					setValue(x, y+1, getValue(x, y));
					setValue(x, y, 0);	
				}
				prev_val = getValue(x, y);
			}
		}
		
		
//		System.out.println(Arrays.toString(array));
		return points;
	}

	@Override
	public int up() {
		// TODO Auto-generated method stub
		int points = 0;
				
		for (int max_shifts = 0; max_shifts < 4; max_shifts++) {
			for (int x = 0; x < 4; x++) {
				int prev_val = getValue(x, 0);
				
				for (int y = 1; y < 4; y++) {
					if (getValue(x, y) != 0 && prev_val == 0) {
						setValue(x, y-1, getValue(x, y));
						setValue(x, y, 0);	
					}
					prev_val = getValue(x, y);
				}
			}
			
			
		}
		
		if (canMerge()) {
			for (int x = 0; x < 4; x++) {
				int prev_val = getValue(x, 0);
				
				for (int y = 1; y < 4; y++) {
					if (getValue(x, y) == prev_val) {
						setValue(x, y-1, prev_val * 2);
						setValue(x, y, 0);
						points = points + (prev_val * 2);
					} 
					prev_val = getValue(x, y);
				}
			}
		}
		
		for (int x = 0; x < 4; x++) {
			int prev_val = getValue(x, 0);
			
			for (int y = 1; y < 4; y++) {
				if (getValue(x, y) != 0 && prev_val == 0) {
					setValue(x, y-1, getValue(x, y));
					setValue(x, y, 0);	
				}
				prev_val = getValue(x, y);
			}
		}
		return points;
	}

}
