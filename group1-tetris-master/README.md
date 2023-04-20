# TCSS305 B Tetris Final Project (group1-tetris)
## Group Members
- Harman Singh
- Lucas Perry
- Windie Le
- Shuaib Ali
## Sprint 1 Contribution
- Harman Singh - Created file menu system and initially worked on figuring out how the gridBagLayout works for the JFrame. However, we ended up not using this layout.
- Lucas Perry - Created README.md and the text label on the gui panels. Managed github and merged several branches. Lots of bugfixing and refactoring of the view package.
- Windie Le - Created the color-coded gui layout with several classes that we ultimately used for Sprint 1.
- Shuaib Ali - Created the initial guiTest with a resizeable window but due to a bug, the group opted in for a different implementation. Worked on documentation.
### Sprint 1 Comments
- The checkstyle warnings originating from any files in the archive package should be ignored. This package contains the old and unused code that we wrote to practice making UI elements and is not used for the actual project.
- We left any warnings in the model package untouched as we are not use if we are allowed to change that code. If we are allowed to change this code, we will fix these for the next sprint submission (Sprint 2).
- PMD says to avoid declaring local variables as final but CheckStyle says to declare them final. Ignore those PMD warnings, we're prioritizing CheckStyle's warnings first.
- Our archive package contains code that we ended up not using in the Sprint 1 submission. It's an alternate implementation of the gui.
## Sprint 2 Contribution
- Harman Singh - Added createAndShowGui() and refactored lots of functionality including setting the main frame and changing the main method.
- Lucas Perry - Implemented PropertyChangeListener in a few different places. In Board.java determined where state changed and fired property changes.
- Windie Le - Modified GUI layout presentation.
- Shuaib Ali - Created and populated the grid on the main board panel. Also implemented the next piece preview. Added instance vars in our Boardable.java interface.
### Sprint 2 Meetings
- [Google Doc](https://docs.google.com/document/d/16NREt5LWMhcEL5HpXvNqx7SKrTr3ErAcrU4EaTbGQws/edit)
### Sprint 2 Comments
- There is warning wherever Observable is used saying that it is deprecated. We were not sure what to do with this warning so we left it alone.
- We did not have time to fully implement PropertyChangeListener. We will need to work this part out before the next submission.
## Sprint 3 Contribution

- Harman Singh - Implemented the music to play when starting a new game. Worked on implementing broader support for Property Change Support across the panels and the board to update the GUI properly. Added broader keyPress support for pausing, viewing controls, etc.
- Lucas Perry - Worked on implemented broader support for Property Change Support across the panels and the board to update the GUI properly.
- Windie Le - Bug fixes and implemented the primary layout of the GUI.

- Shuaib Ali - Implemented info panel with control keys and fixed some bugs.
### Sprint 3 Meetings
- [Google Doc](https://docs.google.com/document/d/1VT7gbQ-uhWxGfeTbzxn50XZv6Bnt9N06wZGslU6hYPs/edit?usp=sharing)
### Sprint 3 Comments

- To play: run from Application.java and then start a new game. Then, you must go under the File menu and click "New Game" to start the game. The music will start when you press Ok.
- Source for music code: https://www.youtube.com/watch?v=TErboGLHZGA
- There were many violations in model that we did not touch as it was from code we did not manipulate and are not supposed to manipulate.
- The timer doesn't speed up as the level increases. We did not have time to troubleshoot this problem.
- The points system does work partially but currently, only the 4 points per block freeze are counted. It does not scale to the level the user is on.
- Special features:
  - We implemented a color picker that lets you change the color of some elements of the UI. We did not have time to make the GUI more customizable.
  - We added a music player that plays the original Tetris-A theme. The credits for the music are in the About option, under the File tab. The music can be paused and un-paused under the Settings tab.
  - There is an executable jar file in the directory "out\artifacts\group1_tetris_jar".

