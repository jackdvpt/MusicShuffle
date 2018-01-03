
import java.util.*;

class musicNode {
    String track; // The name of the track
    int played = 0; // The number of times played
    int shuffleTag = 0; // For shuffling
    musicNode next;

    public musicNode() { // Here's how we construct an empty list.
        next = null;
    }

    public musicNode(String t) {
        track = t;
        next = null;
    }

    public musicNode(String t, musicNode ptr) {
        track = t;
        next = ptr;
    }

    public boolean LTTrack(musicNode x) { // Compares tracks according to
        // alphabetical order on strings
        if (this.track.compareTo(x.track) <= 0)
            return true;
        else
            return false;
    }

    public boolean LTPlayed(musicNode x) { // Compares according to the played
        // field.
        if (this.played <= x.played)
            return true;
        else
            return false;
    }

    public boolean LTTag(musicNode x) { // Compares according to the shuffle
        // tag.
        if (this.shuffleTag <= x.shuffleTag)
            return true;
        else
            return false;
    }
};

// This class represents a playlist;
// We assume that each track appears at most once in the playlist

public class MusicPlayer {
    protected musicNode head = null; // Pointer to the top of the list.
    int length = 0; // the number of nodes in the list.
    boolean debug = false;

    public MusicPlayer() {
    }

    public void setToNull() {
        head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public String firstTrack() {
        return head.track;
    }

    public int firstPlaycount() {
        return head.played;
    }

    public int firstTag() {
        return head.shuffleTag;
    }

    public musicNode head() {
        return head;
    }

    public void printAll() {
        for (musicNode tmp = head; tmp != null; tmp = tmp.next)
            System.out.print(tmp.track.toString());
        System.out.print('\n');
    }

    void playTrack(String name) { // Simulates playing a track: searches for the
        // name and increments its played field
        musicNode tmp = head;
        for (; tmp != null && (tmp.track.compareTo(name) != 0); tmp = tmp.next) {

        }
        if (tmp != null)
            tmp.played = tmp.played + 1;
    }

    void insertTrack(String name) { // Inserts a new track at the top of the
        // list.
        musicNode temp = new musicNode(name, head);
        head = temp;
        length++;
    }

    void sortTrack() {
        if (head != null && head.next != null) { // If there are more than 2
            // tracks
            musicNode left = head;
            int mid = length / 2;
            while (mid - 1 > 0) {
                left = left.next;
                mid--;
            }
            musicNode right = left.next;
            left.next = null;
            left = head();
            head = merge(mergeSort(left, 1), mergeSort(right, 1), 1);
        }
    }

    public musicNode mergeSort(musicNode a, int type) {
        musicNode leftStart = a;
        int midlePos = middle(a);
        if (a.next == null) {
            return a; // If the only item left is the first one
        }
        while (midlePos - 1 > 0) {
            leftStart = leftStart.next;
            midlePos--;
        }
        musicNode rightStart = leftStart.next;
        leftStart.next = null;
        leftStart = a;
        return merge(mergeSort(leftStart, type), mergeSort(rightStart, type), type);
    }

    public int middle(musicNode a) {
        int count = 1;
        while (a.next != null) { // count up until one before the middle
            count++;
            a = a.next;
        }
        return count / 2; // Returns the middle of the list
    }

    public musicNode merge(musicNode left, musicNode right, int type) {
        musicNode result = null;
        if (right == null) {
            return left; // Only one item remaining in left so return left
        }
        if (left == null) {
            return right; // Otherwise if there is nothing in left then return
            // what is left in right
        }
        switch (type) { // The type is what it is going to be sorting by
            case 1: // A normal sort, using the track name
                if (left.LTTrack(right)) {
				/* If left is more then add that and merge from the rest */
                    result = left;
                    result.next = merge(left.next, right, 1);
                } else { // Otherwise take from the right hand side
                    result = right;
                    result.next = merge(left, right.next, 1);
                }
                break;
            case 2: // The smart sort. Sorting based off the played field
                if (left.LTPlayed(right)) {
                    result = left;
                    result.next = merge(left.next, right, 2);
                } else {
                    result = right;
                    result.next = merge(left, right.next, 2);
                }
                break;
            case 3: // Used for recommendations.but ascending
                if (right.LTPlayed(left)) {
                    result = left;
                    result.next = merge(left.next, right, 3);
                } else {
                    result = right;
                    result.next = merge(left, right.next, 3);
                }
                break;
            case 4: // Shuffle. Sorting by the random tag
                if (left.LTTag(right)) {
                    result = left;
                    result.next = merge(left.next, right, 4);
                } else {
                    result = right;
                    result.next = merge(left, right.next, 4);
                }
                break;
        }
        return result; //

    }

    void sortPlayed() { // This is optional but might be useful for shuffling.
        // Sorts (ascending) the list according to the number of times played
    }

    int countItem(String item) {
        // Returns the number of times that item occurs in the current list
        int count = 0;
        for (musicNode tmp = head; tmp != null; tmp = tmp.next) {
            if (tmp.track == item) {
                count++;
            }
        }
        return count;
    }

    musicNode checkMembership(String _track) {
        // If the given _track is present in the current list (i.e. the node
        // whose "track" field
        // is equal to _track), returns the address of that node;
        // otherwise returns null.
        for (musicNode tmp = head; tmp != null; tmp = tmp.next) {
            if (tmp.track == _track) {
                return tmp;
            }
        }
        return null;
    }

    public void populateRandomTag() {
        int count = 1;
        musicNode temp = head;
        int random;
        Random randomnNo = new Random();
        while (count <= length) {
            random = randomnNo.nextInt(25);
            if (temp != null) {
                temp.shuffleTag = random;

                temp = temp.next;
            }
            count++;
        }
    }

    void shuffle() {
        // Randomly shuffles the list
        populateRandomTag(); // Add to eacch songs random tag
        head = mergeSort(head(), 4); // Sort by the new random tags
    }

    void smartShuffle() {
        // Shuffles the list, whilst respecting the special condition on played
        // Sorts the tracks based off the number of times played first
        head = mergeSort(head(), 2);
        int count = 1;
        // Now shuffles the nodes inside of the sorted played groups
        musicNode temp = head;
        musicNode start = head;
        int tempnum = head.played;
        while (count <= length) {
            if (temp.played != tempnum) {
                // Now at a node that does not have the same played value
                checkNodes(start, temp);
                tempnum = temp.played;
                start = temp;
            }
            if (temp.next != null) {
                temp = temp.next;
            }
            count++;
        }
        if (temp.next == null) {
            checkNodes(start, temp.next);
        }
    }

    public void checkNodes(musicNode start, musicNode end) {
        musicNode temp = start;
        musicNode previous = null;
        int count = 0;
        while (temp != end) {
            count++;
            if (count >= 2) {
				/*
				 * If there are more than 2 nodes then it swaps them bassed of
				 * the random tags
				 */
                if (previous.next.LTTag(previous)) {
                    swapNodes(previous, previous.next);
                }
                count = 0;
            }
            previous = temp;
            temp = temp.next;

        }
    }

    public void swapNodes(musicNode one, musicNode two) {
        String first = one.track;
        int rand = one.played;
        String second = two.track;
        int rand2 = two.played;
        two.track = first;
        two.played = rand;
        one.track = second;
        one.played = rand2;

    }

    public void destoryUnwanted(String[] historyList, musicNode head) {
        musicNode temp = head().next;
        musicNode prev = head();
        boolean destroy = true;
        // Check if head is not in history
        for (int i = 0; i < historyList.length; i++) {
            if (head.track == historyList[i]) {
                destroy = false;
            }
        }
        // If head isn't in history then remove it
        if (destroy) {
            head = head.next;
        }
        // Check every other node
        while (temp != null) {
            destroy = true;
            for (int i = 0; i < historyList.length; i++) {
                if (temp.track == historyList[i]) {
                    destroy = false;
                }
            }
            if (destroy) {
                prev.next = temp.next;
            }
            prev = temp;
            temp = temp.next;

        }
    }

    public void countHistory(String[] historyList, musicNode head) {
        musicNode temp = head();
        int count = 0;
        while (temp != null) {
            count = 0;
            for (int i = 0; i < historyList.length; i++) {
                if (temp.track == historyList[i]) {
                    count++;
                }
            }
            temp.played += count;
			/*
			 * Set the played field of the song to the number of times it
			 * appears in the history array
			 */
            temp = temp.next;
        }
    }

    void recommended(String[] historyList) {
        // Remove any nodes not in the list
        destoryUnwanted(historyList, head());
        // Update the played field to represent the list
        countHistory(historyList, head());
        // Sort by the played field ascending
        head = mergeSort(head(), 3);
    }

    void moveFirstNode(MusicPlayer fromList, MusicPlayer toList) {
        // Removes the top node of fromList and puts it onto (the top of)
        // toList.
        // If fromList is empty, it does nothing.
        if (fromList.head() != null) {
            toList.head = fromList.head;
            fromList.head = fromList.head().next;
        }
    }
}
