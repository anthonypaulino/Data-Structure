package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {

	    	/*this gave me such a headache but I absolutely enjoyed every single headache constructing this method, i'm pretty sure there is a more simplistic way of doing this method 
		but I constructed the way my I processed it, im sure I could've constructed the different linked list while linking it to a next node and a node downward, instead I broke it down into two*/

       //Setting up ZeroNodes
	   TNode setter = new TNode(0);
	   setter.setDown(new TNode(0));
	   setter.getDown().setDown(new TNode(0));
	   
	   //pointers
	   TNode train = setter;
	   TNode bus = setter.getDown();
	   TNode walk = setter.getDown().getDown();
	   
	   //Linked list of trainstations
	   TNode aPTR = train;
	   for ( int i = 0; i < trainStations.length; i++){
		   TNode prev = aPTR;
		   aPTR = new TNode(trainStations[i], null, null);
		   prev.setNext(aPTR);
	   }
	   
	   //Linked list of bus stops
	   TNode bPTR = bus;
	   for ( int i = 0; i < busStops.length; i++){
		   TNode prev = bPTR;
		   bPTR = new TNode(busStops[i], null, null);
		   prev.setNext(bPTR);
	   }

		//Linked list walking locations
		TNode cPTR = walk;
		for ( int i = 0; i < locations.length; i++){
			TNode prev = cPTR;
			cPTR = new TNode(locations[i], null, null);
			prev.setNext(cPTR);
		}

	   //Connecting trainstations with bus stops
	   aPTR = train.getNext();
	   bPTR = bus.getNext();
	   for ( int i = 0; i < trainStations.length; i++){
		   //allows train pointer to increment
		   TNode prevA = aPTR;
		   aPTR = prevA.getNext();
		   // loop to check for locations that match in bus and train linked list
		   for ( int j = 0; j < busStops.length; j++){  
			   //if location matches it links that train TNode with bus TNode and then resets bus pointer for next train TNode increment
			   if (trainStations[i] == busStops[j]){ 
				   prevA.setDown(bPTR); 
				   bPTR = bus.getNext();
				   break;               
			   }
			   //increments bus pointer
			   else {
				   bPTR = bPTR.getNext();
			   }
		   }
	   } 

	   //Connecting bus stops with walk locations
	   bPTR = bus.getNext();
	   cPTR = walk.getNext();
	   for ( int i = 0; i < busStops.length; i++){
		   //allows bus pointer to increment
		   TNode prevB = bPTR;
		   bPTR = prevB.getNext();
		   // loop to check for locations that match in bus and walk linked list
		   for ( int j = 0; j < locations.length; j++){  
			   //if location matches it links that bus TNode with walk TNode and then resets walk pointer for next bus TNode increment
			   if (busStops[i] == locations[j]){ 
				   prevB.setDown(cPTR); 
				   cPTR = walk.getNext();
				   break;               
			   }
			   //increments walk pointer
			   else {
				   cPTR = cPTR.getNext();
			   }
		   }
	   }
	   
	   trainZero = setter;
	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		
		TNode busPrev = trainZero.getDown();
        TNode busPTR = busPrev.getNext(); 
        TNode trainPrev = trainZero;
        
		    for(TNode trainPTR = trainPrev.getNext();  trainPTR!= null; trainPTR = trainPTR.getNext()){

			    if (trainPTR.getLocation() == station){
                    trainPrev.setNext(trainPTR.getNext());
                    //busPrev.setNext(busPTR.getNext());         
                    break;
			    }
			
			    else if ( busPTR.getNext() != null){
                    busPrev = busPTR;
                    busPTR = busPTR.getNext();

                    trainPrev = trainPTR;
			    }
                
            }

	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
		//zero nodes
		TNode busPrev = trainZero.getDown();
		//holds the previous bus stop
        TNode walkPrev = trainZero.getDown().getDown();
		
		TNode walkholder = new TNode();
		TNode busholder = new TNode();
		TNode newNode = new TNode(busStop, null,null);

		for(TNode walkPTR =  walkPrev;  walkPTR!= null; walkPTR = walkPTR.getNext()){

			if(walkPTR.getLocation() == busStop){
				walkholder = walkPTR;
			}
		}
	
		for(TNode busPTR =  busPrev;  busPTR!= null; busPTR = busPTR.getNext()){
			//best case, inputted for effiency
			if(busStop == 0){
				break;
			}
			//last walk node
			else if(busPTR.getLocation() < busStop && busPTR.getNext() == null){
				newNode.setNext(null);
				newNode.setDown(walkholder);
				busPTR.setNext(newNode);
				break;
			}
			//normal case
			else if(busPTR.getLocation() < busStop){
				busholder = busPTR;
			}
			
			else if(busPTR.getLocation() > busStop){
				newNode.setNext(busPTR);
				newNode.setDown(walkholder);
				busholder.setNext(newNode);
				break;
			}
		}
	}

	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		ArrayList<TNode> route = new ArrayList<TNode>();
		//list.add(myObject); // Adding it to the list.
	   
		TNode ptr = trainZero;
		TNode tPTR = new TNode();
		TNode bPTR= new TNode();
		TNode wPTR = new TNode();

		for (TNode trainPTR = ptr; trainPTR!=null; trainPTR = trainPTR.getNext()) {
			if (trainPTR.getLocation() == destination ){
				tPTR =  trainPTR;
				route.add(trainPTR);
				break;
			}

			else if(trainPTR.getLocation() < destination){
				tPTR = trainPTR;
				route.add(tPTR);
			}
			
			else if(trainPTR.getLocation() > destination){
				break;
			}
		}

		for (TNode busPTR = tPTR.getDown();  busPTR!=null;  busPTR =  busPTR.getNext()) {
			if ( busPTR.getLocation() == destination ){
				bPTR =  busPTR; 
				route.add( busPTR);
				break;
			}

			else if( busPTR.getLocation() < destination){
				bPTR =  busPTR;
				route.add(bPTR);
				
			}
			
			else if( busPTR.getLocation() > destination){

				break;
			}
		}

		for (TNode walkPTR = bPTR.getDown(); walkPTR!=null; walkPTR = walkPTR.getNext()) {
			if (walkPTR.getLocation() == destination ){
				wPTR =  walkPTR;
				route.add(walkPTR);
				break;
			}

			else if(walkPTR.getLocation() < destination){
				wPTR = walkPTR;
				route.add(wPTR);
			}
			
			else if(walkPTR.getLocation() > destination){
				break;
			}
		}
		
			
		




	    return route;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		
	    //Setting up ZeroNodes
	   TNode copy = new TNode(0);
	   copy.setDown(new TNode(0));
	   copy.getDown().setDown(new TNode(0));
	   
	   //pointers for copy nodes
	   TNode train = copy;
	   TNode bus = copy.getDown();
	   TNode walk = copy.getDown().getDown();
		
	   //creating linked list
		for(TNode trainPTR = trainZero.getNext(); trainPTR!=null; trainPTR = trainPTR.getNext()) {
			TNode temp = new TNode(trainPTR.getLocation(),null,null);
			train.setNext(temp);
			train = train.getNext();
		}

		for(TNode busPTR = trainZero.getDown().getNext(); busPTR!=null; busPTR = busPTR.getNext()) {
			TNode temp = new TNode(busPTR.getLocation(),null,null);
			bus.setNext(temp);
			bus = bus.getNext();
		}

		for(TNode walkPTR = trainZero.getDown().getDown().getNext(); walkPTR!=null; walkPTR = walkPTR.getNext()) {
			TNode temp = new TNode(walkPTR.getLocation(),null,null);
			walk.setNext(temp);
			walk = walk.getNext();
		}

		//repointing
		train = copy.getNext();
		bus = copy.getDown().getNext();
		walk = copy.getDown().getDown().getNext();

	
		TNode aPTR = copy.getNext();
		TNode bPTR = copy.getDown().getNext();
		
		for (TNode trainPTR = copy.getNext(); trainPTR!=null; trainPTR = trainPTR.getNext()) {
			TNode prevA = aPTR;
			aPTR = prevA.getNext();

			for (TNode busPTR = copy.getDown().getNext(); busPTR!=null; busPTR = busPTR.getNext()){
				if (trainPTR.getLocation() == busPTR.getLocation()){ 
					prevA.setDown(bPTR); 
					bPTR = copy.getDown().getNext();
					break;               
				}
				else {
					bPTR = bPTR.getNext();
				}
			}
		}


		bPTR = copy.getDown().getNext();
		TNode cPTR = copy.getDown().getDown().getNext();

		for (TNode busPTR = copy.getDown().getNext(); busPTR!=null; busPTR = busPTR.getNext()) {
			TNode prevB = bPTR;
			bPTR = prevB.getNext();

			for (TNode walkPTR = copy.getDown().getDown().getNext(); walkPTR!=null; walkPTR = walkPTR.getNext()){
				if (busPTR.getLocation() == walkPTR.getLocation()){ 
					prevB.setDown(cPTR); 
					cPTR = copy.getDown().getDown().getNext();
					break;               
				}
				else {
					cPTR = cPTR.getNext();
				}
			}
		}
		
	    return copy;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {
		TNode scooter = new TNode(0);
		TNode sPTR = scooter;
		TNode walk = new TNode(0);
		TNode wCopy = walk;

		for ( int i = 0; i < scooterStops.length; i++){
			TNode prev = sPTR;
			sPTR = new TNode(scooterStops[i], null, null);
			prev.setNext(sPTR);
		}


		for(TNode walkPTR = trainZero.getDown().getDown().getNext(); walkPTR!=null; walkPTR = walkPTR.getNext()) {
			TNode temp = new TNode(walkPTR.getLocation(),null,null);
			wCopy.setNext(temp);
			wCopy = wCopy.getNext();
		}
		
		TNode bPTR = trainZero.getDown();
		sPTR = scooter;

		for (TNode busPTR = trainZero.getDown(); busPTR!=null; busPTR = busPTR.getNext()) {
			TNode prevB = bPTR;
			bPTR = prevB.getNext();

			for (TNode scootPTR = scooter; scootPTR!=null; scootPTR = scootPTR.getNext()){
				if (busPTR.getLocation() == scootPTR.getLocation()){ 
					prevB.setDown(sPTR); 
					sPTR = scooter;
					break;               
				}
				else {
					sPTR = sPTR.getNext();
				}
			}
		}

		TNode wPTR = walk;
		sPTR = scooter;
		for (TNode scootPTR = scooter; scootPTR!=null; scootPTR = scootPTR.getNext()) {
			TNode prevS = sPTR;
			sPTR = prevS.getNext();

			for (TNode walkPTR = walk; walkPTR!=null; walkPTR = walkPTR.getNext()){
				if (scootPTR.getLocation() == walkPTR.getLocation()){ 
					prevS.setDown(wPTR); 
					wPTR = walk;
					break;               
				}
				else {
					wPTR = wPTR.getNext();
				}
			}
		}


	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
