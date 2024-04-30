import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DataCompression {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user to input the message
        System.out.println("Enter the message to compress and decompress: ");
        String message = scanner.nextLine();

        // Step 1: Build frequency map for characters in the message
        Map<Character, Integer> frequencyMap = buildFrequencyMap(message);

        // Step 2: Build Huffman Tree using the frequency map
        HuffmanTree huffmanTree = buildHuffmanTree(frequencyMap);

        // Step 3: Generate Huffman Codes for each character
        Map<Character, String> huffmanCodes = generateHuffmanCodes(huffmanTree);

        // Step 4: Compress the original message using Huffman Codes
        String compressedMessage = compressMessage(message, huffmanCodes);
        System.out.println("Compressed Message: " + compressedMessage);

        // Step 5: Decompress the compressed message back to original
        String decompressedMessage = decompressMessage(compressedMessage, huffmanTree);
        System.out.println("Decompressed Message: " + decompressedMessage);

        scanner.close();
    }

    // Step 1: Build frequency map for characters in the message
    static Map<Character, Integer> buildFrequencyMap(String message) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : message.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    // Step 2: Build Huffman Tree from the frequency map
    static HuffmanTree buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanTree> priorityQueue = new PriorityQueue<>();

        // Create a leaf node for each character and add to priority queue
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.offer(new HuffmanTree(entry.getKey(), entry.getValue()));
        }

        // Combine nodes to create Huffman Tree
        while (priorityQueue.size() > 1) {
            HuffmanTree left = priorityQueue.poll();
            HuffmanTree right = priorityQueue.poll();
            HuffmanTree parent = new HuffmanTree('\0', left.frequency + right.frequency, left, right);
            priorityQueue.offer(parent);
        }

        // Return the root of the Huffman Tree
        return priorityQueue.poll();
    }

    // Step 3: Generate Huffman Codes recursively
    static Map<Character, String> generateHuffmanCodes(HuffmanTree root) {
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodesRecursive(root, "", huffmanCodes);
        return huffmanCodes;
    }

    // Helper method for Step 3
    static void generateCodesRecursive(HuffmanTree node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) return;

        // If leaf node, add the code to the map
        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.character, code);
        }

        // Traverse left with '0' and right with '1'
        generateCodesRecursive(node.left, code + "0", huffmanCodes);
        generateCodesRecursive(node.right, code + "1", huffmanCodes);
    }

    // Step 4: Compress the message using Huffman Codes
    static String compressMessage(String message, Map<Character, String> huffmanCodes) {
        StringBuilder compressed = new StringBuilder();
        for (char c : message.toCharArray()) {
            compressed.append(huffmanCodes.get(c));
        }
        return compressed.toString();
    }

    // Step 5: Decompress the message using Huffman Tree
    static String decompressMessage(String compressedMessage, HuffmanTree root) {
        StringBuilder decompressed = new StringBuilder();
        HuffmanTree current = root;

        // Traverse the Huffman Tree based on bits in the compressed message
        for (char bit : compressedMessage.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            }

            // If leaf node reached, append character and reset to root
            assert current != null;
            if (current.left == null && current.right == null) {
                decompressed.append(current.character);
                current = root;
            }
        }
        return decompressed.toString();
    }

    // Inner class for representing nodes in Huffman Tree
    static class HuffmanTree implements Comparable<HuffmanTree> {
        char character;
        int frequency;
        HuffmanTree left, right;

        public HuffmanTree(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        public HuffmanTree(char character, int frequency, HuffmanTree left, HuffmanTree right) {
            this.character = character;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(HuffmanTree other) {
            return this.frequency - other.frequency;
        }
    }
}
