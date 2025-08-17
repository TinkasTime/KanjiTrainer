package canvas;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimpleCanvas {

    private JFrame frame;
    private JButton penButton;
    private JButton undoButton;
    private JButton clearButton;
    private JPanel buttonPanel;
    private CanvasPanel canvasPanel;

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
        canvasPanel.setStrokeFinishedCallback(this::updateUndoButton);
        frame.add(canvasPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void initializeButtonListener() {
        penButton.addActionListener(_ -> {
            canvasPanel.setPenMode(true);
            updatePenButton();
            updateUndoButton();
        });

        clearButton.addActionListener(_ -> {
            canvasPanel.clearDrawing();
            canvasPanel.setPenMode(false);
            updatePenButton();
            updateUndoButton();
        });

        undoButton.addActionListener(_ -> {
            canvasPanel.undoLastStroke();
            canvasPanel.setPenMode(false);
            updatePenButton();
            updateUndoButton();
        });
    }

    /**
     * Ändere Aktivierung des Pen Buttons.
     * Ändere den Modus in CanvasPanel.
     */
    private void updatePenButton() {
        penButton.setEnabled(!canvasPanel.getPenMode());
    }

    private void updateUndoButton() {
        undoButton.setEnabled(canvasPanel.hasStrokes());
    }
}