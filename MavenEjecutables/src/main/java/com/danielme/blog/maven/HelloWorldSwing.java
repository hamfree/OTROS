/* 
   Copyright (c) 1995, 2008, Oracle y/o sus filiales. Reservados todos los derechos.
   Redistribución y uso en formato fuente y binario, con o sin
   modificaciones, están permitidas siempre que las siguientes condiciones
   se cumplen:

  - Las redistribuciones del código fuente deben conservar los derechos de autor anteriores.
    aviso, esta lista de condiciones y el siguiente descargo de responsabilidad.

  - Las redistribuciones en forma binaria deben reproducir los derechos de autor anteriores.
    aviso, esta lista de condiciones y el siguiente descargo de responsabilidad en el
    documentación y/u otros materiales proporcionados con la distribución.

  - Ni el nombre de Oracle ni los nombres de sus
    Los contribuyentes pueden utilizarse para respaldar o promover productos derivados
    de este software sin un permiso previo específico por escrito.

    ESTE SOFTWARE ES PROPORCIONADO POR LOS TITULARES DE LOS DERECHOS DE AUTOR Y 
    COLABORADORES "TAL CUAL" Y CUALQUIER GARANTÍA EXPRESA O IMPLÍCITA, INCLUYENDO, 
    PERO NO LIMITADO A, LAS GARANTÍAS IMPLÍCITAS DE COMERCIABILIDAD E IDONEIDAD 
    PARA UN PROPÓSITO EN PARTICULAR SE RECHAZAN. EN NINGÚN CASO EL PROPIETARIO DE 
    LOS DERECHOS DE AUTOR O LOS COLABORADORES SERÁN RESPONSABLES DE CUALQUIER DAÑO 
    DIRECTO, INDIRECTO, INCIDENTAL, ESPECIAL, EJEMPLAR O CONSECUENTE (INCLUYENDO, 
    PERO NO LIMITADO A, ADQUISICIÓN DE BIENES O SERVICIOS SUSTITUTOS; PÉRDIDA DE 
    USO, DATOS O BENEFICIOS; O INTERRUPCIÓN COMERCIAL) SIN EMBARGO Y SOBRE CUALQUIER 
    TEORÍA DE RESPONSABILIDAD, YA SEA POR CONTRATO, RESPONSABILIDAD ESTRICTA O AGRAVIO 
    (INCLUYENDO NEGLIGENCIA O DE OTRA MANERA) QUE SURJA DE CUALQUIER FORMA DEL USO 
    DE ESTE SOFTWARE, INCLUSO SI SE ADVIERTE DE LA POSIBILIDAD DE DICHO DAÑO.
 */
/**
 * Este ejemplo, como todos los ejemplos de Swing, existe en un paquete:
 * en este caso, el paquete "start".
 * Si está utilizando un IDE, como NetBeans, esto debería funcionar sin 
 * problemas. Si está compilando y ejecutando los ejemplos desde la línea 
 * de comandos, esto puede resultar confuso si no está acostumbrado a usar 
 * paquetes con nombre. En la mayoría de los casos, la solución rápida y 
 * sucia es eliminar o comentar la línea "paquete" de todos los archivos 
 * fuente y el código debería funcionar como se esperaba. Para obtener una 
 * explicación de cómo usar los ejemplos de Swing tal como están desde la 
 * línea de comandos, consulte
 * http://docs.oracle.com/javase/javatutorials/tutorial/uiswing/start/compile.html#package
 */
package com.danielme.blog.maven;

import java.awt.Dimension;


/*
 * HelloWorldSwing.java no requiere de otros ficheros. 
 */
import javax.swing.*;

import org.apache.log4j.Logger;

public class HelloWorldSwing {

  private static final Logger LOG = Logger.getLogger(HelloWorldSwing.class);

  /**
   * Creamos el IGU y lo mostramos. Por seguridad del hilo, este método debe ser invocado desde el hilo de gestión de eventos.
   */
  private static void createAndShowGUI() {
    //Creamos y configuramos la ventana
    JFrame frame = new JFrame("HolaMundoSwing");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Agrega la etiqueta omnipresente "Hola mundo".
    JLabel label = new JLabel("¡Hola Mundo!");
    frame.getContentPane().add(label);
    frame.setPreferredSize(new Dimension(300, 250));
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    //Planificamos un trabajo para el hilo de gestión de eventos
    //creamos y mostramos el IGU de esta aplicación
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        createAndShowGUI();
      }
    });
  }
}
