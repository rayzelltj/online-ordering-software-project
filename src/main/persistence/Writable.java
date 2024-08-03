package persistence;

import org.json.JSONObject;

// Writable interface (modelled using JsonSerializationDemo)
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
