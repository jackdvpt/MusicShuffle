# MusicShuffle
====
MusicPlayer.java is a class which deals with implementing methods to build and control music files on a music player; it consists of a linked list with three data fields.

- Track name
- Number of times track played
- Extra field for Shuffling

Sort Track
----
This function sorts the current linked list alphabetically according to its track field. Other helper methods are used.
The sort is a merge sort with worst-case O(n log n) complexity and only O(1) extra space.

Shuffle
----
Here a list is randomly shuffled, re-ordering the list randomly based on the extra field and the same merge sort function, but instead of sorting by the name it sorts by this new random field

Smart Shuffle
----
Here the list is shuffled based on the number of times a track has been played, meaning that no track appears in the list after any other track with a higher played field.

Recommended
----
This creates a playlist based on browsing preferences, it has a history of listening activity and a playlist is created based on that history. It achieves this by computing which track are in the current playlist and the history, removing anything that isn't in the history and then sorts the tracks so the tracks are sorted by the play history
