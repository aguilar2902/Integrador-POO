package interfaz;

import java.awt.*;
import javax.swing.*;

public class PanelRedondeado extends JPanel {
    private int radius;
    private Color backgroundColor;
    private Color borderColor;
    private int borderThickness;

    public PanelRedondeado(int radius, Color backgroundColor, Color borderColor, int borderThickness) {
        this.radius = radius;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        setOpaque(false); // importante para no pintar esquinas cuadradas
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo redondeado
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Borde redondeado
        g2.setStroke(new BasicStroke(borderThickness));
        g2.setColor(borderColor);
        g2.drawRoundRect(borderThickness / 2, borderThickness / 2,
                getWidth() - borderThickness, getHeight() - borderThickness, radius, radius);

        g2.dispose();
    }
}