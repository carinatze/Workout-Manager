package persistence;

import org.json.JSONObject;

// citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// represents a writable interface class
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
