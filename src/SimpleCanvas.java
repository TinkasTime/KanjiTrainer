import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import analyzer.KanjiAnalyzer;
import canvas.CanvasPanel;
import canvas.CanvasStroke;
import canvas.DrawingMode;

public class SimpleCanvas {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "KanjiTrainer - Canvas";

    private static DrawingMode currentMode = DrawingMode.NONE;

    private JFrame frame;
    private JButton undoButton;
    private CanvasPanel canvasPanel;

    private KanjiAnalyzer kanjiAnalyzer;

    public SimpleCanvas() {
        frame = new JFrame();
        frame.setTitle(FRAME_TITLE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton penButton = new JButton("Pen");
        JButton clearButton = new JButton("Clear");
        undoButton = new JButton("Undo");

        buttonPanel.add(penButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(undoButton);

        frame.add(buttonPanel, BorderLayout.NORTH);

        canvasPanel = new CanvasPanel();
        canvasPanel.setUndoButtonCallback(() -> updateUndoButtonState());
        canvasPanel.setStrokeFinishedCallback(() -> performKanjiRecognition());
        frame.add(canvasPanel, BorderLayout.CENTER);

        kanjiAnalyzer = new KanjiAnalyzer();

        penButton.addActionListener(e -> {
            penButton.setEnabled(false);
            currentMode = DrawingMode.PEN;
            canvasPanel.setDrawingMode(currentMode);
        });

        clearButton.addActionListener(e -> {
            canvasPanel.clearDrawing();
            penButton.setEnabled(true);
            currentMode = DrawingMode.NONE;
            canvasPanel.setDrawingMode(currentMode);
            updateUndoButtonState();
        });

        undoButton.addActionListener(e -> {
            canvasPanel.undoLastStroke();
            penButton.setEnabled(true);
            currentMode = DrawingMode.NONE;
            canvasPanel.setDrawingMode(currentMode);
            updateUndoButtonState();
        });

        penButton.setEnabled(true);
        updateUndoButtonState();
        canvasPanel.setDrawingMode(currentMode);

        frame.setVisible(true);
    }

    private void updateUndoButtonState() {
        if (undoButton != null && canvasPanel != null) {
            undoButton.setEnabled(canvasPanel.hasStrokes());
        }
    }

    private void performKanjiRecognition() {
        List<CanvasStroke> strokes = canvasPanel.getDrawingStrokes();
        String recognizedKanji = kanjiAnalyzer.analyzeKanji(strokes);

        System.out.println("\n--- Aktuelles Erkennungsergebnis ---");
        System.out.println(recognizedKanji);
        System.out.println("--------------------------------------\n");
    }
}