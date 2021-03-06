package com.example.priyanka.bakingrecipes;


import android.content.Context;
import android.os.AsyncTask;

import com.example.priyanka.bakingrecipes.models.IngredientsModel;
import com.example.priyanka.bakingrecipes.models.RecipeModel;
import com.example.priyanka.bakingrecipes.models.StepsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class RecipeUtils extends AsyncTask<String, Void, ArrayList<RecipeModel>> {

    public interface AsyncTaskCompleteListener {
        void onTaskComplete(ArrayList<RecipeModel> result);
    }

    public Context context;
    private AsyncTaskCompleteListener listener;

    public RecipeUtils(Context context, AsyncTaskCompleteListener listener) {
        this.context = context.getApplicationContext();
        this.listener = listener;
    }

    private ArrayList<RecipeModel> recipesList = new ArrayList<>();

        @Override
        protected ArrayList<RecipeModel> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();

                StringBuilder stringBuilder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String finalJson = stringBuilder.toString();
                JSONArray parentArray = new JSONArray(finalJson);

                for (int i = 0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    RecipeModel recipeModel = new RecipeModel();
                    recipeModel.setName(finalObject.getString("name"));
                    recipeModel.setServings(finalObject.getInt("servings"));

                    JSONArray ingredients = finalObject.getJSONArray("ingredients");
                    ArrayList<IngredientsModel> ingredientsList = new ArrayList<>();

                    for (int j = 0; j<ingredients.length(); j++) {
                        JSONObject ingredientObject = ingredients.getJSONObject(j);
                        IngredientsModel ingredientsModel = new IngredientsModel();
                        ingredientsModel.setQuantity((float) ingredientObject.getDouble("quantity"));
                        ingredientsModel.setMeasure(ingredientObject.getString("measure"));
                        ingredientsModel.setIngredient(ingredientObject.getString("ingredient"));

                        ingredientsList.add(ingredientsModel);
                        recipeModel.setIngredients(ingredientsList);
                    }

                    JSONArray steps = finalObject.getJSONArray("steps");
                    ArrayList<StepsModel> stepsList = new ArrayList<>();
                    ArrayList<StepsModel> videoUrl = new ArrayList<>();

                    for (int k = 0; k<steps.length(); k++) {
                        JSONObject stepsObject = steps.getJSONObject(k);
                        StepsModel stepsModel = new StepsModel();
                        StepsModel videoModel = new StepsModel();
                        stepsModel.setShortDescription(stepsObject.getString("shortDescription"));
                        stepsModel.setDescription(stepsObject.getString("description"));
                        stepsModel.setVideoURL(stepsObject.getString("videoURL"));

                        videoModel.setVideoURL(stepsObject.getString("videoURL"));
                        videoModel.setThumbnailURL(stepsObject.getString("thumbnailURL"));

                        stepsList.add(stepsModel);
                        videoUrl.add(videoModel);

                        recipeModel.setSteps(stepsList);
                        recipeModel.setVideoUrl(videoUrl);
                    }

                    recipesList.add(recipeModel);
                }

                return recipesList;
            }

            catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<RecipeModel> result) {
            super.onPostExecute(result);
            if (recipesList != null) {
                listener.onTaskComplete(result);
            }
        }
}
