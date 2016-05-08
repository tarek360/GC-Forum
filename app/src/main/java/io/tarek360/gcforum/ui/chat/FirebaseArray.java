package io.tarek360.gcforum.ui.chat;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import java.util.ArrayList;
/**
 * Created by tarek on 4/13/16.
 */

/**
 * This class implements an array-like collection on top of a Firebase location.
 */
class FirebaseArray implements ChildEventListener {
  private Query mQuery;
  private OnChangedListener mListener;
  private ArrayList<DataSnapshot> mSnapshots;

  public FirebaseArray(Query ref) {
    mQuery = ref;
    mSnapshots = new ArrayList<DataSnapshot>();
    mQuery.addChildEventListener(this);
  }

  public void cleanup() {
    mQuery.removeEventListener(this);
  }

  public int getCount() {
    return mSnapshots.size();
  }

  public DataSnapshot getItem(int index) {
    return mSnapshots.get(index);
  }

  private int getIndexForKey(String key) {
    int index = 0;
    for (DataSnapshot snapshot : mSnapshots) {
      if (snapshot.getKey().equals(key)) {
        return index;
      } else {
        index++;
      }
    }
    throw new IllegalArgumentException("Key not found");
  }

  // Start of ChildEventListener methods
  public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
    int index = 0;
    if (previousChildKey != null) {
      index = getIndexForKey(previousChildKey) + 1;
    }
    mSnapshots.add(index, snapshot);
    notifyChangedListeners(OnChangedListener.EventType.Added, index);
  }

  public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
    int index = getIndexForKey(snapshot.getKey());
    mSnapshots.set(index, snapshot);
    notifyChangedListeners(OnChangedListener.EventType.Changed, index);
  }

  public void onChildRemoved(DataSnapshot snapshot) {
    int index = getIndexForKey(snapshot.getKey());
    mSnapshots.remove(index);
    notifyChangedListeners(OnChangedListener.EventType.Removed, index);
  }

  public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
    int oldIndex = getIndexForKey(snapshot.getKey());
    mSnapshots.remove(oldIndex);
    int newIndex = previousChildKey == null ? 0 : (getIndexForKey(previousChildKey) + 1);
    mSnapshots.add(newIndex, snapshot);
    notifyChangedListeners(OnChangedListener.EventType.Moved, newIndex, oldIndex);
  }

  public void onCancelled(FirebaseError firebaseError) {
    // TODO: what do we do with this?
  }

  public void setOnChangedListener(OnChangedListener listener) {
    mListener = listener;
  }
  // End of ChildEventListener methods

  protected void notifyChangedListeners(OnChangedListener.EventType type, int index) {
    notifyChangedListeners(type, index, -1);
  }

  protected void notifyChangedListeners(OnChangedListener.EventType type, int index, int oldIndex) {
    if (mListener != null) {
      mListener.onChanged(type, index, oldIndex);
    }
  }

  public interface OnChangedListener {
    void onChanged(EventType type, int index, int oldIndex);

    enum EventType {Added, Changed, Removed, Moved}
  }
}
