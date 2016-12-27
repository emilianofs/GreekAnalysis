package dataModels;


public class Graph {
    private boolean adjacencyMatrix[][];
    private int vertexCount;

    public Graph(int vertexCount) {
          this.vertexCount = vertexCount;
          adjacencyMatrix = new boolean[vertexCount][vertexCount];
          for(int i=0; i< vertexCount; i++){
      		for(int j=0; j< vertexCount;j++){
      			adjacencyMatrix[i][j] = false;
      		}
      		System.out.println("");
      	}
    }

    public void addEdge(int i, int j) {
          if (i >= 0 && i < vertexCount && j > 0 && j < vertexCount) {
                adjacencyMatrix[i][j] = true;
                adjacencyMatrix[j][i] = true;
          }
    }

    public void removeEdge(int i, int j) {
          if (i >= 0 && i < vertexCount && j > 0 && j < vertexCount) {
                adjacencyMatrix[i][j] = false;
                adjacencyMatrix[j][i] = false;
          }
    }

    public boolean isEdge(int i, int j) {
          if (i >= 0 && i < vertexCount && j > 0 && j < vertexCount)
                return adjacencyMatrix[i][j];
          else
                return false;
    }
    
    public void print(){
    	System.out.print(",");
    	for(int i=0; i< vertexCount; i++){
    		System.out.print(i);
    		if(i<vertexCount){
				System.out.print(",");
			}
    	}
    	System.out.println("");
    	
    	for(int i=0; i< vertexCount; i++){
    		System.out.print(i+",");
    		for(int j=0; j< vertexCount;j++){
    			System.out.print(adjacencyMatrix[i][j]);
    			if(j<vertexCount){
    				System.out.print(",");
    			}
    		}
    		System.out.println("");
    	}
    }
}