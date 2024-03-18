package ca.cmpt213.as4.Model;

import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapeModelI implements ca.cmpt213.as4.ShapeModel {
    private List<DrawableShapeI> shapes = new ArrayList<>();

    @Override
    public void populateFromJSON(File jsonFile) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(jsonFile.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(fileReader);
        JsonArray array = obj.get("shapes").getAsJsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type type = new TypeToken<ArrayList<DrawableShapeI>>() {}.getType();
        shapes = gson.<ArrayList<DrawableShapeI>>fromJson(array, type);
    }

    @Override
    public void redact() {
        for(DrawableShapeI drawableShapeI : shapes) {
            drawableShapeI.redact();
        }
    }

    @Override
    public Iterator<DrawableShapeI> iterator() {
        return shapes.iterator();
    }
}
