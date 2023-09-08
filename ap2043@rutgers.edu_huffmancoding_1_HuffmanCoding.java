package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        sortedCharFreqList = new ArrayList<CharFreq>();
        int [] occurrences = new int [128]; 
        int count = 0;

        while (StdIn.hasNextChar()){
        int c = StdIn.readChar();
        occurrences[c] = occurrences[c] + 1;
        count++;
        }

        for ( int i = 0; i < occurrences.length; i++){
            if (occurrences[i] > 0){
                CharFreq c = new CharFreq((char)i, occurrences[i]/(double)count );
                sortedCharFreqList.add(c);
            }
        }


        if ( sortedCharFreqList.size() == 1){
            int intDC = sortedCharFreqList.get(0).getCharacter();
            int newDC = (intDC + 1) % 128;
            CharFreq c = new CharFreq ((char)newDC,0);
            sortedCharFreqList.add(c);
        }

        Collections.sort(sortedCharFreqList);
       
        

    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {

        //creating queues
        Queue<TreeNode> source = new Queue<TreeNode>();
        Queue<TreeNode> target = new Queue<TreeNode>();
        Queue<TreeNode> holder = new Queue<TreeNode>();
      
        //filling up source queue
        for(int i =0; i<sortedCharFreqList.size(); i++){
            source.enqueue(new TreeNode(sortedCharFreqList.get(i),null,null));
        }
  
            while( source.isEmpty() == false || target.size() != 1){
                TreeNode aux = new TreeNode();
                TreeNode temp1 = new TreeNode();
                TreeNode temp2 = new TreeNode();
                CharFreq c = new CharFreq ();
                //filling holder queue
                while(holder.size()!=2 && holder.size()<2){
                    //target is empty take it a node from source
                    if( target.isEmpty()){
                        holder.enqueue(source.dequeue());
                    }
                    // target is not empty and source is not empty so compare node of with smaller probOcc
                    //if target and source probOcc is the same, use source node first
                    else if(source.isEmpty() == false){
                        if(source.peek().getData().getProbOcc() <= target.peek().getData().getProbOcc()){
                            holder.enqueue(source.dequeue());
                        }
                        else{
                            holder.enqueue(target.dequeue());
                        }
                    }
                    // if target is not empty and source is empty take a node from target
                    else if(source.isEmpty()){
                        holder.enqueue(target.dequeue());
                        }
                }
                
                temp1 = holder.dequeue();
                temp2 = holder.dequeue();
                aux.setLeft( temp1);
                aux.setRight(temp2);
                c.setProbOcc(temp1.getData().getProbOcc() + temp2.getData().getProbOcc());
                aux.setData(c);
                target.enqueue(aux);
                    
            }

            huffmanRoot = target.dequeue();  
        }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    private void recursiveEncode(String[] encodings, TreeNode ptr, String bit){

        if (ptr.getData().getCharacter() != null){
        encodings[ptr.getData().getCharacter()] = bit;
        return;
        }
        recursiveEncode(encodings, ptr.getLeft(), bit + 0);
        recursiveEncode(encodings, ptr.getRight(), bit + 1);
    }

    public void makeEncodings() {

        encodings = new String[128];
        TreeNode ptr = huffmanRoot;
        String bit = "";

        for(int i = 0; i < sortedCharFreqList.size(); i++){
            recursiveEncode(encodings, ptr, bit);
        }
    
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        String encode = "";

        while (StdIn.hasNextChar()){
            int c = StdIn.readChar();
            encode += encodings[c];
        } 
        writeBitString(encodedFile, encode);  
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */

    

    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);
        String decode = readBitString(encodedFile);
        TreeNode ptr = huffmanRoot;

        for ( int i = 0; i < decode.length(); i++){
            
            if(ptr.getData().getCharacter() == null){
                if (decode.substring(i,i+1).equals("0")){
                    ptr = ptr.getLeft();
                    if(ptr.getData().getCharacter() != null){
                        StdOut.print(ptr.getData().getCharacter());
                        ptr = huffmanRoot;
                    }
                    
                }
                else{
                    ptr = ptr.getRight();
                    if(ptr.getData().getCharacter() != null){
                        StdOut.print(ptr.getData().getCharacter());
                        ptr = huffmanRoot;
                    }
                   
                }
            }
            else {
                StdOut.print(ptr.getData().getCharacter());
                ptr = huffmanRoot;
            }
        }
        // StdOut.println() and StdOut.print()  StdOut.print(char x) 

	/* Your code goes here */
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
