package ca.cmpt213.as4.Model;

import ca.cmpt213.as4.UI.Canvas;

import java.awt.*;


public class DrawableShapeI implements ca.cmpt213.as4.UI.DrawableShape{

    public int top;
    public int left;
    public int width;
    public int height;
    public String background;
    public String backgroundColor;
    public String line;
    public String lineChar;
    public String fill;
    public String fillText;
    private boolean redacted;

    public DrawableShapeI(int top, int left, int height, int width, String background, String backgroundColor, String line, String fill, String fillText, String lineChar) {
        this.top = top;
        this.left = left;
        this.height = height;
        this.width = width;
        this.background = background;
        this.backgroundColor = backgroundColor;
        this.line = line;
        this.fill = fill;
        this.fillText = fillText;
        this.lineChar = lineChar;
        this.redacted = false;
    }

    @Override
    public void draw(Canvas canvas) {
        if(redacted) {
            drawRedacted(canvas);
            return;
        }
        drawBorder(canvas);
        setBackground(canvas);
        ShapeText shapeText = new ShapeText(fill, fillText, width, height, top, left, canvas);
        shapeText.fillText();
    }

    public void redact() {
        this.redacted = true;
    }

    private void drawRedacted(Canvas canvas) {
        setBorder(canvas, '+', '+');
        for(int i=1; i<height-1; i++){
            for(int j=1; j<width-1; j++){
                canvas.setCellText(left+j, top+i, 'X');
                canvas.setCellColor(left+j, top+i, Color.lightGray);
            }
        }
    }
    private void drawBorder(Canvas canvas) {
        if(line.equals("char"))
            drawCharBorder(lineChar.charAt(0), canvas);
        else if(line.equals("ascii line"))
            drawAsciiBorder(canvas);
        else if(line.equals("sequence"))
            drawSequenceBorder(canvas);
    }

    private void drawSequenceBorder(Canvas canvas) {
       int value = 1;
       for(int i=0; i<width; i++) {
           canvas.setCellText(left+i, top, (char) (value + '0'));
           value++;
           if(value > 5) value = 1;
       }
       for(int i=1; i<height; i++) {
           canvas.setCellText(left+width-1, top+i, (char) (value + '0'));
           value++;
           if(value > 5) value = 1;
       }
       if(height > 1) {
           for (int i = 1; i < width; i++) {
               canvas.setCellText(left + width - 1 - i, top + height - 1, (char) (value + '0'));
               value++;
               if (value > 5) value = 1;

           }
       }
       if(width > 1) {
           for (int i = 1; i < height - 1; i++) {
               canvas.setCellText(left, top + height - 1 - i, (char) (value + '0'));
               value++;
               if (value > 5) value = 1;
           }
       }
    }

    private void setBackground(Canvas canvas) {
        Color color = StringToColor(backgroundColor);
        switch (background) {
            case "solid" -> setSolidBackGround(canvas, color);
            case "checker" -> setCheckerBackGround(canvas, color);
            case "triangle" -> setTriangleBackGround(canvas, color);
        }

    }

    private void drawAsciiBorder(Canvas canvas) {
        if(width==1 || height==1) {
            setBorder(canvas, '■', '■');
            return;
        }
        setBorder(canvas, '║', '═');
        updateCorners(canvas);
    }

    private void setCheckerBackGround(Canvas canvas, Color color) {
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                if((j+i)%2==0) canvas.setCellColor(left+j, top+i, color);
                else canvas.setCellColor(left+j, top+i, Color.WHITE);
            }
        }
    }

    private void setTriangleBackGround(Canvas canvas, Color color) {
        for(int i=0; i<height; i++) {
            for(int j=i; j<width; j++){
                canvas.setCellColor(left+j, top+i, color);
            }
        }
    }

    private void updateCorners(Canvas canvas) {
        canvas.setCellText(left, top, '╔');
        canvas.setCellText(left+width-1, top, '╗');
        canvas.setCellText(left, top+height-1, '╚');
        canvas.setCellText(left+width-1, top+height-1, '╝');
    }

    private void setBorder(Canvas canvas, char vertical, char horizontal) {
        for(int i=0; i<height; i++) {
            canvas.setCellText(left, top+i, vertical);
            canvas.setCellText(left+width-1, top+i, vertical);
            if(redacted) {
                canvas.setCellColor(left, top+i, Color.lightGray);
                canvas.setCellColor(left+width-1, top+i, Color.lightGray);
            }
        }
        for(int i=0; i<width; i++) {
            canvas.setCellText(left+i, top, horizontal);
            canvas.setCellText(left+i, top+height-1, horizontal);
            if(redacted) {
                canvas.setCellColor(left+i, top, Color.lightGray);
                canvas.setCellColor(left+i, top+height-1, Color.lightGray);
            }
        }
    }

    private void setSolidBackGround(Canvas canvas, Color color) {
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                canvas.setCellColor(left+i, top+j, color);
            }
        }
    }

    private void drawCharBorder(char borderSymbol, Canvas canvas) {
        setBorder(canvas, borderSymbol, borderSymbol);
    }

    private Color StringToColor(String color) {
        if(color.equals("light gray")) return Color.lightGray;
        if(color.equals("gray")) return Color.gray;
        if(color.equals("dark gray")) return Color.darkGray;
        if(color.equals("black")) return Color.black;
        if(color.equals("red")) return Color.red;
        if(color.equals("pink")) return Color.pink;
        if(color.equals("orange")) return Color.orange;
        if(color.equals("yellow")) return Color.yellow;
        if(color.equals("green")) return Color.green;
        if(color.equals("magenta")) return Color.magenta;
        if(color.equals("cyan")) return Color.cyan;
        if(color.equals("blue")) return Color.blue;
        return Color.WHITE;
    }
}
