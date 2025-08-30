package canvas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import analyzer.KanjiAnalyzer;

public class SimpleCanvas {

    private JFrame frame;
    private JButton penButton;
    private JButton undoButton;
    private JButton clearButton;
    private JPanel buttonPanel;
    private CanvasPanel canvasPanel;
    private KanjiAnalyzer kanjiAnalyzer;
    private JTextArea resultArea;

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "KanjiTrainer";

    public SimpleCanvas() {
        initializeGUI();
        initializeButtonListener();
    }

    private void initializeGUI() {
        // Frame
        frame = new JFrame();
        frame.setTitle(FRAME_TITLE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Buttons
        penButton = new JButton("Pen");
        clearButton = new JButton("Clear");
        undoButton = new JButton("Undo");
        undoButton.setEnabled(false);

        // JPanel for Buttons
        buttonPanel = new JPanel();
        buttonPanel.add(penButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(undoButton);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.add(buttonPanel, BorderLayout.NORTH);
        
        // Canvas
        canvasPanel = new CanvasPanel();
        canvasPanel.setStrokeUpdateCallback(this::startStrokeAnalysis);
        frame.add(canvasPanel, BorderLayout.CENTER);

        // KanjiAnalyzer mit resultArea
        kanjiAnalyzer = new KanjiAnalyzer();
        resultArea = new JTextArea("Analyseresultat hier anzeigen.");
        resultArea.setEditable(false);
        frame.add(resultArea, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void initializeButtonListener() {
        penButton.addActionListener(_ -> {
            canvasPanel.setPenMode(true);
            updatePenButton();
        });

        clearButton.addActionListener(_ -> {
            canvasPanel.clearDrawing();
            canvasPanel.setPenMode(false);
            updatePenButton();
        });

        undoButton.addActionListener(_ -> {
            canvasPanel.undoLastStroke();
            canvasPanel.setPenMode(false);
            updatePenButton();
        });
    }

    /**
     * Ändere Aktivierung des Pen Buttons anhand
     * des Pen-Modus in CanvasPanel.
     */
    private void updatePenButton() {
        penButton.setEnabled(!canvasPanel.getPenMode());
    }

    private void updateUndoButton() {
        undoButton.setEnabled(canvasPanel.hasStrokes());
    }

    /**
     * Nachdem ein Strich abgeschlossen wurde, wird ein Callback
     * genutzt, um die Strichanalyse zu starten.
     */
    private void startStrokeAnalysis() {
        updateUndoButton();

        String analysisResult = kanjiAnalyzer.analyzeKanji(canvasPanel.getDrawingStrokes());
        resultArea.setText(analysisResult);
    }
}