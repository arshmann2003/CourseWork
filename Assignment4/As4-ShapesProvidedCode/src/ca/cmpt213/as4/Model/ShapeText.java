package ca.cmpt213.as4.Model;
import ca.cmpt213.as4.UI.Canvas;

import java.util.ArrayList;
import java.util.List;

public class ShapeText {

    private final String fill;
    private final String fillText;
    private final int width;
    private final int left;
    private final int top;
    private final int height;

    private final Canvas canvas;
    private final List<String> words;

    public ShapeText(String fill, String fillText, int width, int height, int top, int left, Canvas canvas) {
        this.fill = fill;
        this.fillText = fillText.trim();
        this.width = width;
        this.height = height;
        this.top = top;
        this.left = left;
        this.canvas = canvas;
        words = new ArrayList<>();
    }

    public void fillText() {
        if(fill.equals("solid")) {
            fillSolidText();
        } else {
            fillWrapperText();
        }
    }

    private void fillWrapperText() {
        extractWords(fillText);
        if(words.isEmpty())
            return;
        addWordsToShape();
    }

    private void addWordsToShape() {
        int sentenceIndex = 0;
        for(int i=1; i<height-1; i++) {
            if(sentenceIndex >= words.size()) break;
            String sentence = words.get(sentenceIndex);
            int wordIndex = 0;
            for(int j=getStartingIndex(sentenceIndex); j<width-1; j++) {
                if(wordIndex >= sentence.length()) break;
                canvas.setCellText(left+j, top+i, sentence.charAt(wordIndex));
                wordIndex++;
            }
            sentenceIndex++;
        }
    }

    private int getStartingIndex(int i) {
        String word = words.get(i);
        int n = word.length();
        if(n >= width-2)
            return 1;
        else {
            int index = (width-n)/2;
            if((n%2!=0 && width%2==0) || (width%2!=0 && n%2==0))
                index++;
            return index;
        }
    }

    private void extractWords(String sentence) {
        String word = "";
        for(int i=0; i<sentence.length(); i++) {
           if(sentence.charAt(i) == ' ' || i==sentence.length()-1) {
               if(i==sentence.length()-1) word += sentence.charAt(i);
               words.add(word);
               word = "";
           } else {
               word+=sentence.charAt(i);
           }
        }
        combineWords();
        splitRunOnWords();
    }

    private void splitRunOnWords() {
        int n = words.size();
        for(int i=0; i<n; i++) {
            String word = words.get(i);
            if(word.length() > width-2) {
                String runOn = word.substring(width - 2);
                words.set(i, word.substring(0, width-2));
                if(i+1 < n) {
                    words.add(i+1, runOn);
                }
                else words.add(runOn);
                n++;
            }
        }
    }

    private void combineWords() {
        int n = words.size();
        String newWord = "";
        for(int i=0; i<n-1 ; i++) {
            String word1 = words.get(i);
            newWord += word1;
            String word2 = words.get(i+1);
            if(word2.length() + word1.length() + 1 <= width-2) {
                newWord += " " + word2;
                words.set(i, newWord);
                words.remove(i + 1);
                n--;
                i--;
            }
            newWord = "";
        }
    }

    private void fillSolidText() {
        for(int i=1; i<height-1; i++) {
            for(int j=1; j<width-1; j++) {
                    canvas.setCellText(left+j, top+i, fillText.charAt(0));
            }
        }
    }

}
