package persistence;

import org.json.JSONObject;

// code taken from Writable Interface JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
