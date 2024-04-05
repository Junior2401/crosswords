package alissouali.amdalatjinadu.crossword.model;

public class Grid<T> {
    
    // Variables d'instance
    int height;
    int width;
    T[][] array;

    @SuppressWarnings("unchecked")
	public Grid(int height, int width) {
        this.height = height+1;
        this.width = width+1;
        this.array = (T[][]) new Object[this.height][this.width];
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

	public boolean correctCoords(int row, int column) {
		
		for(int i=0; i<this.getHeight()-1; i++) {
			for(int j=0; j<=this.getWidth()-1; j++) {
				if(i==row && j==column) {
					return true;
				}
			}
		}
		return false;
	}
	

    public T getCell(int row, int column) {
        assert correctCoords(row, column);
            return this.array[row][column];
    }

    public void setCell(int row, int column, T value) {
    	assert correctCoords(row, column);
            this.array[row][column] = value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                sb.append(i).append(",").append(j).append(this.array[i][j]).append("|");
            }
        }
        return sb.toString();
    }

/*    public static void main(String[] args) {
        Grid<Integer> myGrid = new Grid<>(5, 5);
        System.out.println(myGrid.toString());
    }*/
}
